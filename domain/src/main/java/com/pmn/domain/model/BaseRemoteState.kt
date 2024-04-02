package com.pmn.domain.model

data class BaseRemoteState<out T>(
    val syncState: BaseRemoteSyncState<T> = BaseRemoteSyncState.Idle
)

sealed class BaseRemoteSyncState<out T> {
    data object Idle : BaseRemoteSyncState<Nothing>()
    data object Loading : BaseRemoteSyncState<Nothing>()
    data object NetworkError : BaseRemoteSyncState<Nothing>()
    data object TimeoutError : BaseRemoteSyncState<Nothing>()
    data object EmptyResponse : BaseRemoteSyncState<Nothing>()
    data class Failure(
        val throwable: Throwable? = null,
        val responseMessage: String? = null,
        val responseCode: Int? = null
    ) : BaseRemoteSyncState<Nothing>()

    data class Success<out T>(val data: T?) : BaseRemoteSyncState<T>()
}