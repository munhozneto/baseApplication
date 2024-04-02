package com.pmn.baseapplication.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmn.domain.model.BaseRemoteState
import com.pmn.domain.model.SomeEntityDomain
import com.pmn.domain.usecase.GetSomeEntityListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntityListViewModel(
    private val getSomeEntityListUseCase: GetSomeEntityListUseCase
) : ViewModel() {

    private val _navigation: Channel<EntityListContract.Navigation> = Channel()
    val navigation = _navigation.receiveAsFlow()

    fun handleEvent(event: EntityListContract.Event) {
        when (event) {
            is EntityListContract.Event.Loading -> getSomeEntityList()
            is EntityListContract.Event.EntitySelection -> {
                viewModelScope.launch {
                    _navigation.send(EntityListContract.Navigation.ToDetails(event.entityId))
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(BaseRemoteState<List<SomeEntityDomain>>())
    val uiState = _uiState.asStateFlow()

    private fun getSomeEntityList() {
        viewModelScope.launch {
            getSomeEntityListUseCase.invoke(
                currentState = uiState.value
            )
                .onEach { newState ->
                    _uiState.update { newState }
                }
                .collect()
        }
    }
}