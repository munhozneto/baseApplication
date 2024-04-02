package com.pmn.domain.usecase

import com.pmn.domain.model.BaseRemoteState
import com.pmn.domain.model.BaseRemoteSyncState
import com.pmn.domain.model.ResponseHandler
import com.pmn.domain.model.SomeEntityDomain
import com.pmn.domain.repository.SomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GetSomeEntityUseCase(
    private val repository: SomeRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(
        entityId: Long,
        showLoading: Boolean = true,
        currentState: BaseRemoteState<SomeEntityDomain>
    ) = flow {
        if (showLoading) {
            emit(currentState.copy(syncState = BaseRemoteSyncState.Loading))
        }

        val response = repository.getSomeEntity(
            id = entityId
        )

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
        response: ResponseHandler<SomeEntityDomain>,
        currentState: BaseRemoteState<SomeEntityDomain>
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
        currentState: BaseRemoteState<SomeEntityDomain>
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