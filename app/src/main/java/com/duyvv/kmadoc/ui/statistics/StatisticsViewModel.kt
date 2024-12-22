package com.duyvv.kmadoc.ui.statistics

import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.data.dto.request.StatisticsRequest
import com.duyvv.kmadoc.data.model.StatisticModel
import com.duyvv.kmadoc.data.usecase.GetStatisticsUseCase
import com.duyvv.kmadoc.util.onEachError
import com.duyvv.kmadoc.util.toStatisticModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getStatisticsUseCase: GetStatisticsUseCase
) : BaseViewModel() {

    private val _listStatistics = MutableStateFlow<List<StatisticModel>>(emptyList())
    val listStatistics = _listStatistics.asStateFlow()

    private val _startDate = MutableStateFlow("")
    val startDate = _startDate.asStateFlow()
    private val _endDate = MutableStateFlow("")
    val endDate = _endDate.asStateFlow()

    fun updateStartDate(startDate: String) {
        _startDate.value = startDate
    }

    fun updateEndDate(endDate: String) {
        _endDate.value = endDate
    }

    init {
        getListStatistics()
    }

    fun getListStatistics() {
        showLoading(true)
        getStatisticsUseCase.execute(
            StatisticsRequest(
                fromDate = startDate.value,
                toDate = endDate.value
            )
        ).onEachError {
            showLoading(false)
            showError(it)
        }.onEach { result ->
            showLoading(false)
            _listStatistics.value = result.data?.map { it.toStatisticModel() } ?: emptyList()
        }.launchIn(viewModelScopeExceptionHandler)
    }
}