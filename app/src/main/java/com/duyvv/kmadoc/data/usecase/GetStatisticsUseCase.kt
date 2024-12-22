package com.duyvv.kmadoc.data.usecase

import com.duyvv.kmadoc.base.UseCase
import com.duyvv.kmadoc.data.dto.request.StatisticsRequest
import com.duyvv.kmadoc.data.dto.response.StatisticsResponse
import com.duyvv.kmadoc.data.repository.FormManagementRepository
import com.duyvv.kmadoc.util.flowFromSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetStatisticsUseCase @Inject constructor(
    private val repo: FormManagementRepository
) : UseCase<StatisticsRequest, Result<StatisticsResponse>> {

    override fun execute(value: StatisticsRequest?): Flow<Result<StatisticsResponse>> =
        flowFromSuspend {
            repo.getStatisticsForm(value!!)
        }.flowOn(Dispatchers.IO)
}