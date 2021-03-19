package com.example.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in Request, Response>() {
    internal abstract suspend  fun getRawFlow(params: Request): Flow<Response>

    suspend fun getFlow(request: Request): Flow<Response> = getRawFlow(request).flowOn(Dispatchers.IO)
}