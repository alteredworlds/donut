package com.example.twcgilbert.donut.repo.di

import android.app.Application
import com.example.twcgilbert.donut.repo.DataRepository
import com.example.twcgilbert.donut.repo.DataRepositoryImpl
import com.example.twcgilbert.donut.repo.network.Constants
import com.example.twcgilbert.donut.repo.network.CreditReportService
import com.example.twcgilbert.donut.repo.network.NetworkMonitorInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * The Dagger module for the REPOSITORY layer
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideCache(context: Application): Cache {
        val cacheSize = 2 * 1024 * 1024 // 2 MB
        val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
        return Cache(httpCacheDirectory, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun providesNetworkInterceptor(): Interceptor {
        // model persistence provided via HTTP Cache: assume data valid for 1 minute
        return okhttp3.Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }
    }

    @Provides
    @Singleton
    fun providesNetworkMonitorInterceptor(context: Application): NetworkMonitorInterceptor =
            NetworkMonitorInterceptor(context)

    @Provides
    @Singleton
    fun provideDataRepository(
            cache: Cache,
            networkCacheInterceptor: Interceptor,
            networkMonitorInterceptor: NetworkMonitorInterceptor): DataRepository {

        // helps with debugging - we get to see HTTP body in logcat for debug builds
        val loggingInterceptor = HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message ->
                    Timber.tag("OkHttp").d(message)
                }).setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(networkCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(networkMonitorInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()

        return DataRepositoryImpl(retrofit.create<CreditReportService>(CreditReportService::class.java))
    }
}