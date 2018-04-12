package com.example.twcgilbert.donut.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.example.twcgilbert.donut.R
import java.util.*

/**
 * Created by twcgilbert on 11/03/2018.
 */

/**
 * Based on https://stackoverflow.com/questions/31219455/android-round-edges-on-ring-shaped-progressbar
 */
class CreditScoreDonut @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val startAngle = -90f
    private var sweepAngle = 0.0f
    private val maxSweepAngle = 360.0f
    private var animationDuration: Int
    private var maxProgress = 100

    private var progressBoundaryGap: Float
    private var boundaryRingStrokeWidth: Float
    private var boundaryRingColor: Int
    private var progressStrokeWidth: Float
    private var progressColor: Int
    private var secondaryTextColor: Int

    private val textPaintSmall: Paint
    private val textPaintLarge: Paint
    private var textCentreX = 0.0f
    private var textUpperY = 0.0f
    private var textCentreY = 0.0f
    private var textLowerY = 0.0f

    private val boundaryPaint: Paint
    private val boundaryOuterOval = RectF()

    private val progressIndicatorPaint: Paint
    private val progressIndicatorOval = RectF()
    private var diameter = 0

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.CreditScoreDonut)
        try {
            progressColor = array.getColor(R.styleable.CreditScoreDonut_progress_color, Color.BLACK)
            progressStrokeWidth = array.getDimension(R.styleable.CreditScoreDonut_progress_width, 20.0f)
            boundaryRingColor = array.getColor(R.styleable.CreditScoreDonut_boundary_color, Color.BLACK)
            boundaryRingStrokeWidth = array.getDimension(R.styleable.CreditScoreDonut_boundary_width, 2.0f)
            progressBoundaryGap = array.getDimension(R.styleable.CreditScoreDonut_progress_boundary_gap, 4.0f)
            animationDuration = array.getInteger(R.styleable.CreditScoreDonut_animation_duration_ms, 400)
            secondaryTextColor = array.getInteger(R.styleable.CreditScoreDonut_secondary_text_color, Color.BLACK)

            textPaintSmall = Paint(Paint.ANTI_ALIAS_FLAG)
            textPaintSmall.textAlign = Paint.Align.CENTER
            textPaintSmall.strokeWidth = 0f
            textPaintSmall.color = secondaryTextColor

            textPaintLarge = Paint(Paint.ANTI_ALIAS_FLAG)
            textPaintLarge.textAlign = Paint.Align.CENTER
            textPaintLarge.strokeWidth = 0f
            textPaintLarge.color = progressColor

            boundaryPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            boundaryPaint.color = boundaryRingColor
            boundaryPaint.strokeWidth = boundaryRingStrokeWidth
            boundaryPaint.isAntiAlias = true
            boundaryPaint.strokeCap = Paint.Cap.BUTT
            boundaryPaint.style = Paint.Style.STROKE

            progressIndicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            progressIndicatorPaint.color = progressColor
            progressIndicatorPaint.strokeWidth = progressStrokeWidth
            progressIndicatorPaint.isAntiAlias = true
            progressIndicatorPaint.strokeCap = Paint.Cap.ROUND
            progressIndicatorPaint.style = Paint.Style.STROKE

        } catch (e: Exception) {
            throw InvalidPropertiesFormatException(e)
        } finally {
            array.recycle()
        }
    }

    private val progressFromSweepAngle: Int
        get() = (maxProgress * sweepAngle / maxSweepAngle).toInt()

    fun setProgressAndMax(progress: Int, max: Int = 100) {
        maxProgress = max
        val newSweepAngle =
                when (maxProgress) {
                    0 -> 0.0f
                    else -> maxSweepAngle * progress / maxProgress
                }
        if (newSweepAngle != sweepAngle) {
            val animator = ValueAnimator.ofFloat(sweepAngle, newSweepAngle)
            animator.interpolator = DecelerateInterpolator()
            animator.duration = animationDuration.toLong()
            animator.addUpdateListener { valueAnimator ->
                sweepAngle = valueAnimator.animatedValue as Float
                invalidate()
            }
            animator.start()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // QUESTION: when can (right - left) differ from onDraw() {canvas.width}?
        //  which values can I cache here, and which need to be calculated in onDraw?
        diameter = Math.min(w, h)

        // TEST SIZES depend on diameter of enclosing circle
        textPaintSmall.textSize = diameter / 14f
        textPaintLarge.textSize = diameter / 4f

        // TEXT POSITIONS
        textCentreX = w.toFloat() / 2.0f        // horizontally centered text
        textUpperY = 0.3f * h - 0.5f * (textPaintSmall.descent() + textPaintSmall.ascent())
        textCentreY = 0.5f * h - 0.5f * (textPaintLarge.descent() + textPaintLarge.ascent())
        textLowerY = 0.7f * h - 0.5f * (textPaintSmall.descent() + textPaintSmall.ascent())

        // Boundary offset just inside enclosing circle
        val outerPad = boundaryRingStrokeWidth / 2.0f
        boundaryOuterOval.set(outerPad, outerPad, diameter - outerPad, diameter - outerPad)

        // Progress Indicator inset a bit further
        val pad = boundaryRingStrokeWidth + progressBoundaryGap + progressStrokeWidth / 2.0f
        progressIndicatorOval.set(pad, pad, diameter - pad, diameter - pad)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawOuterBoundary(canvas)
        if (0 != maxProgress) {
            drawProgressIndicator(canvas)
            drawText(canvas)
        }
    }

    private fun drawOuterBoundary(canvas: Canvas) =
            canvas.drawArc(boundaryOuterOval,
                    0.0f,
                    360.0f,
                    false,
                    boundaryPaint)

    private fun drawProgressIndicator(canvas: Canvas) =
            canvas.drawArc(progressIndicatorOval,
                    startAngle,
                    sweepAngle,
                    false,
                    progressIndicatorPaint)

    private fun drawText(canvas: Canvas) {
        // top line (smaller font)
        canvas.drawText(context.getString(R.string.credit_score_is),
                textCentreX,
                textUpperY,
                textPaintSmall)

        // Progress value (larger font)
        canvas.drawText(progressFromSweepAngle.toString(),
                textCentreX,
                textCentreY,
                textPaintLarge)

        // bottom line (smaller font)
        canvas.drawText(context.getString(R.string.credit_score_total, maxProgress),
                textCentreX,
                textLowerY,
                textPaintSmall)
    }
}
