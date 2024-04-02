package com.pmn.domain.usecase

import com.pmn.domain.model.BaseRemoteState
import com.pmn.domain.model.BaseRemoteSyncState
import com.pmn.domain.model.ResponseHandler
import com.pmn.domain.model.SomeEntityDomain
import com.pmn.domain.repository.SomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GetSomeEntityListUseCase(
    private val repository: SomeRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(
        showLoading: Boolean = true,
        currentState: BaseRemoteState<List<SomeEntityDomain>>
    ) = flow {
        if (showLoading) {
            emit(currentState.copy(syncState = BaseRemoteSyncState.Loading))
        }

        delay(1_000)

        val response = repository.getSomeEntityList()

        val newState = handleResponse(
            response = response,
            currentState = currentState
        )
        emit(newState)
    }
        .flowOn(defaultDispatcher)
        .catch { throwable ->
            val newState = handleThrowable(
                throwable = throwable,
                currentState = currentState
            )
            emit(newState)
        }

    private fun handleResponse(
        response: ResponseHandler<List<SomeEntityDomain>>,
        currentState: BaseRemoteState<List<SomeEntityDomain>>
    ) = when (response.code) {
        in 0..299 -> {
            response.data?.let { someData ->
                currentState.copy(
                    syncState = BaseRemoteSyncState.Success(data = someData)
                )
            } ?: run {
                currentState.copy(
                    syncState = BaseRemoteSyncState.EmptyResponse
                )
            }
        }

        else -> {
            currentState.copy(
                syncState = BaseRemoteSyncState.Failure(
                    responseMessage = response.message,
                    responseCode = response.code,
                )
            )
        }
    }

    private fun handleThrowable(
        throwable: Throwable,
        currentState: BaseRemoteState<List<SomeEntityDomain>>
    ) = when (throwable) {
        is HttpException -> {
            currentState.copy(
                syncState = BaseRemoteSyncState.Failure(
                    responseMessage = throwable.message(),
                    responseCode = throwable.code()
                )
            )
        }

        is ConnectException,
        is UnknownHostException -> {
            currentState.copy(syncState = BaseRemoteSyncState.NetworkError)
        }

        is SocketTimeoutException -> {
            currentState.copy(syncState = BaseRemoteSyncState.TimeoutError)
        }

        else -> {
            currentState.copy(
                syncState = BaseRemoteSyncState.Failure(
                    throwable = throwable
                )
            )
        }
    }
}