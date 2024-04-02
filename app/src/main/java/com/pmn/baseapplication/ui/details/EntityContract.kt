package com.pmn.baseapplication.ui.details

class EntityContract {
    sealed class Event {
        data class Loading(
            val entityId: Long
        ) : Event()

        data object Back : Event()
    }

    sealed class Navigation {
        data object Back : Navigation()
    }
}