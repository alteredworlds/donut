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

/**
 * Created by twcgilbert on 11/03/2018.
 */

/**
 * Based on https://stackoverflow.com/questions/31219455/android-round-edges-on-ring-shaped-progressbar
 * This custom view code is as yet far from optimal.
 * It contains far too many magic numbers, for starters...
 * The current design is inflexible & RectF instantiation could be moved out of onDraw
 * Similarly Paint field setters needn't be called in onDraw
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

    private val paint: Paint

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val array = context.obtainStyledAttributes(attrs, R.styleable.CreditScoreDonut)
        try {
            progressColor = array.getColor(R.styleable.CreditScoreDonut_progress_color, Color.BLACK)
            progressStrokeWidth = array.getDimension(R.styleable.CreditScoreDonut_progress_width, 20.0f)
            boundaryRingColor = array.getColor(R.styleable.CreditScoreDonut_boundary_color, Color.BLACK)
            boundaryRingStrokeWidth = array.getDimension(R.styleable.CreditScoreDonut_boundary_width, 2.0f)
            progressBoundaryGap = array.getDimension(R.styleable.CreditScoreDonut_progress_boundary_gap, 4.0f)
            animationDuration = array.getInteger(R.styleable.CreditScoreDonut_animation_duration_ms, 400)
            secondaryTextColor = array.getInteger(R.styleable.CreditScoreDonut_secondary_text_color, Color.BLACK)
        } finally {
            array.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val diameter = Math.min(width, height)
        drawOuterRing(canvas, diameter)

        if (0 != maxProgress) {
            drawProgressIndicator(canvas, diameter)
            drawText(canvas, diameter)
        }
    }

    private fun drawOuterRing(canvas: Canvas, diameter: Int) {
        val outerPad = boundaryRingStrokeWidth / 2.0f
        val outerOval = RectF(outerPad, outerPad, diameter - outerPad, diameter - outerPad)
        paint.color = boundaryRingColor
        paint.strokeWidth = boundaryRingStrokeWidth
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.BUTT
        paint.style = Paint.Style.STROKE
        canvas.drawArc(outerOval, 0.0f, 360.0f, false, paint)
    }

    private fun drawProgressIndicator(canvas: Canvas, diameter: Int) {
        val pad = boundaryRingStrokeWidth + progressBoundaryGap + progressStrokeWidth / 2.0f
        val innerOval = RectF(pad, pad, diameter - pad, diameter - pad)

        paint.color = progressColor
        paint.strokeWidth = progressStrokeWidth
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        canvas.drawArc(innerOval, startAngle, sweepAngle, false, paint)
    }

    private fun drawText(canvas: Canvas, diameter: Int) {
        val largeFontSize = diameter / 4f
        val smallFontSize = diameter / 14f

        // horizontally centered text
        val xPos = canvas.width / 2.0f
        var yPos: Float

        paint.textAlign = Paint.Align.CENTER
        paint.strokeWidth = 0f

        // top line (smaller font)
        paint.textSize = smallFontSize
        paint.color = secondaryTextColor
        yPos = 0.3f * canvas.height - 0.5f * (paint.descent() + paint.ascent())
        canvas.drawText(context.getString(R.string.credit_score_is), xPos, yPos, paint)

        // Progress value (larger font)
        paint.textSize = largeFontSize
        paint.color = progressColor
        yPos = 0.5f * canvas.height - 0.5f * (paint.descent() + paint.ascent())
        canvas.drawText(getProgressFromSweepAngle().toString(), xPos, yPos, paint)

        // bottom line (smaller font)
        paint.textSize = smallFontSize
        paint.color = secondaryTextColor
        yPos = 0.7f * canvas.height - 0.5f * (paint.descent() + paint.ascent())
        canvas.drawText(context.getString(R.string.credit_score_total, maxProgress), xPos, yPos, paint)
    }

    private fun getSweepAngleFromProgress(progress: Int): Float {
        return if (0 == maxProgress)
            0.0f
        else
            maxSweepAngle * progress / maxProgress
    }

    private fun getProgressFromSweepAngle(): Int {
        return (maxProgress * sweepAngle / maxSweepAngle).toInt()
    }

    fun setProgressAndMax(value: Int, max: Int = 100) {
        maxProgress = max
        val newSweepAngle = getSweepAngleFromProgress(value)
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
}
