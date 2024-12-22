package com.duyvv.kmadoc.ui.statistics

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.duyvv.kmadoc.base.BaseFragment
import com.duyvv.kmadoc.data.model.FormType
import com.duyvv.kmadoc.data.model.StatisticModel
import com.duyvv.kmadoc.databinding.FragmentStatisticsBinding
import com.duyvv.kmadoc.ui.dialog.FilterTimeDialog
import com.duyvv.kmadoc.util.formatFromDateToDateString
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding, StatisticsViewModel>(
    FragmentStatisticsBinding::inflate
) {

    override val viewModel: StatisticsViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun addObserver() {
        viewModel.listStatistics.onEach { models ->
            loadHorizontalBarChart(models)
        }.launchIn(lifecycleScope)

        viewModel.startDate.onEach { startDate ->
            val endDate = viewModel.endDate.value
            binding.tvTime.text = formatFromDateToDateString(startDate, endDate)
        }.launchIn(lifecycleScope)
        viewModel.endDate.onEach { endDate ->
            val startDate = viewModel.startDate.value
            binding.tvTime.text = formatFromDateToDateString(startDate, endDate)
        }.launchIn(lifecycleScope)
    }

    private fun loadHorizontalBarChart(models: List<StatisticModel>) {
        val list = FormType.entries.map {
            it.titleVn.removePrefix("Đơn xin").trim().replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase() else char.toString()
            }
        }
        val textSizeDefault = 12f
        binding.barChart.apply {
            axisRight.isEnabled = false // Tắt trục phải, không cần thiết
            axisLeft.apply {
                axisMinimum = 0f
                axisLineWidth = 2f
                axisLineColor = Color.BLACK
                textSize = textSizeDefault
                labelCount = 10
            }
            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(list)
                position = XAxis.XAxisPosition.BOTTOM_INSIDE // Hiển thị nhãn phía trong
                granularity = 1f
                isGranularityEnabled = true
                textSize = textSizeDefault
                labelRotationAngle = 90f
                xOffset = -2f
            }
            description.apply {
                text = "Biểu đồ loại đơn"
                textSize = textSizeDefault // Tăng kích thước chữ trong phần mô tả
            }
        }

        val entries = models.mapIndexed { index, statisticModel ->
            BarEntry(index.toFloat(), statisticModel.totalForm.toFloat())
        }

        val dataSet = BarDataSet(entries, "Loại đơn").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextSize = textSizeDefault
        }

        val barData = BarData(dataSet)
        binding.barChart.apply {
            data = barData
            description.isEnabled = false
            animateY(1000)
            invalidate()
            val maxVal = if (models.isEmpty()) 0f else models.maxOf { it.totalForm }
            axisLeft.axisMaximum = maxVal + 10f
        }
    }

    override fun addAction() {
        binding.llFilterTime.setOnClickListener {
            showDialog(FilterTimeDialog.newInstance(
                viewModel.startDate.value,
                viewModel.endDate.value,
                onClickCancelFilter = {
                    viewModel.updateStartDate("")
                    viewModel.updateEndDate("")
                    viewModel.getListStatistics()
                },
                onApply = { startDate, endDate ->
                    viewModel.updateStartDate(startDate)
                    viewModel.updateEndDate(endDate)
                    viewModel.getListStatistics()
                }
            ))
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}