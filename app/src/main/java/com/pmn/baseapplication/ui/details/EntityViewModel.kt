package com.pmn.baseapplication.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmn.domain.model.BaseRemoteState
import com.pmn.domain.model.SomeEntityDomain
import com.pmn.domain.usecase.GetSomeEntityUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntityViewModel(
    private val getSomeEntityUseCase: GetSomeEntityUseCase
) : ViewModel() {

    private val _navigation: Channel<EntityContract.Navigation> = Channel()
    val navigation = _navigation.receiveAsFlow()

    fun handleEvent(event: EntityContract.Event) {
        when (event) {
            is EntityContract.Event.Loading -> getSomeEntityList(
                entityId = event.entityId
            )

            is EntityContract.Event.Back -> {
                viewModelScope.launch {
                    _navigation.send(EntityContract.Navigation.Back)
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(BaseRemoteState<SomeEntityDomain>())
    val uiState = _uiState.asStateFlow()

    private fun getSomeEntityList(entityId: Long) {
        viewModelScope.launch {
            getSomeEntityUseCase.invoke(
                entityId = entityId,
                currentState = uiState.value
            )
                .onEach { newState ->
                    _uiState.update { newState }
                }
                .collect()
        }
    }
}