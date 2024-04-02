package com.pmn.baseapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pmn.baseapplication.ui.details.EntityContract
import com.pmn.baseapplication.ui.details.EntityViewModel
import com.pmn.baseapplication.ui.details.composables.EntityScreen
import com.pmn.baseapplication.ui.list.EntityListContract
import com.pmn.baseapplication.ui.list.composables.EntityListScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun SomeEntityScreenDestination(
    entityId: Long,
    navController: NavController,
    viewModel: EntityViewModel = koinViewModel()
) {
    LaunchedEffect(true) {
        viewModel.navigation
            .onEach { destination ->
                if (destination is EntityContract.Navigation.Back) {
                    navController.popBackStack()
                }
            }
            .collect()
    }

    LaunchedEffect(true) {
        viewModel.handleEvent(EntityContract.Event.Loading(entityId))
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    EntityScreen(
        state = state.value,
        onEventSent = { event ->
            viewModel.handleEvent(event)
        }
    )
}