package com.pmn.baseapplication.ui.list

class EntityListContract {
    sealed class Event  {
        data object Loading : Event()
        data class EntitySelection(val entityId: Long) : Event()
    }

    sealed class Navigation {
        data class ToDetails(val entityId: Long): Navigation()
    }
}