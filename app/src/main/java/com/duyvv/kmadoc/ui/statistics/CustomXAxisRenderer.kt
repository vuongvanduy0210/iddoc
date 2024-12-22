package com.duyvv.kmadoc.ui.statistics

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class CustomXAxisRenderer(
    viewPortHandler: ViewPortHandler,
    xAxis: XAxis,
    trans: Transformer,
    private val spacingMin: Float,
) : XAxisRenderer(viewPortHandler, xAxis, trans) {

    override fun computeAxisValues(min: Float, max: Float) {
        // Compute label sizes first
        computeSize()

        val labelCount = mAxis.labelCount
        val range = abs(max - min).toDouble()

        if (labelCount == 0 || range <= 0 || range.isInfinite()) {
            mAxis.mEntries = floatArrayOf()
            mAxis.mCenteredEntries = floatArrayOf()
            mAxis.mEntryCount = 0
            return
        }

        // Find out how much spacing (in x value space) between axis values
        var rawInterval = range / labelCount
        var interval = Utils.roundToNextSignificant(rawInterval).toDouble()

        // Do not allow the interval go below the label width with spacing
        val labelMaxWidth =
            (mXAxis.mLabelRotatedWidth + spacingMin) * range / mViewPortHandler.contentWidth()
        if (interval < labelMaxWidth)
            interval = labelMaxWidth

        // If granularity is enabled, then do not allow the interval to go below specified granularity.
        // This is used to avoid repeated values when rounding values for display.
        if (mAxis.isGranularityEnabled) interval =
            if (interval < mAxis.granularity) mAxis.granularity.toDouble()
            else interval

        // Normalize interval
        rawInterval = interval
        interval = Utils.roundToNextSignificant(rawInterval).toDouble()
        val intervalMagnitude =
            Utils.roundToNextSignificant(10.0.pow(log10(interval).toInt())).toDouble()
        val intervalSigDigit = (interval / intervalMagnitude).toInt()
        if (intervalSigDigit > 5) {
            // Use one order of magnitude higher, to avoid intervals like 0.9 or 90
            // if it's 0.0 after floor(), we use the old value
            interval = if (floor(10.0 * intervalMagnitude) == 0.0) interval
            else floor(10.0 * intervalMagnitude)
            // If rounded down, round up to avoid labels overlapping
        } else if (interval < rawInterval) {
            interval = (intervalSigDigit + 1) * intervalMagnitude
        }

        var n = if (mAxis.isCenterAxisLabelsEnabled) 1 else 0

        /*
         * Copy-paste here the rest of the code from XAxisRenderer.computeAxisValues method
        */
    }
}