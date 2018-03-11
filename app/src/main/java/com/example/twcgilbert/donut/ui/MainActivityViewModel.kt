package com.example.twcgilbert.donut.ui

import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import com.example.twcgilbert.donut.repo.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by twcgilbert on 06/03/2018.
 */
class MainActivityViewModel(
        private val view: MainActivityContract.View,
        private val repository: DataRepository) :
        MainActivityContract.ViewModel {

    private val disposables = CompositeDisposable()

    override val maxScore = ObservableInt()

    override val score = ObservableInt()

    override val inProgress = ObservableBoolean()

    override fun onResume() {
        inProgress.set(true)
        disposables.add(repository.getCreditReport()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.d("Success!")
                            inProgress.set(false)
                            score.set(it.creditReportInfo.score)
                            maxScore.set(it.creditReportInfo.maxScoreValue)
                        },
                        {
                            Timber.e(it, "Failure!")
                            inProgress.set(false)
                            view.showError(it.localizedMessage)
                        }
                ))
    }

    override fun onPause() {
        disposables.clear()
    }
}