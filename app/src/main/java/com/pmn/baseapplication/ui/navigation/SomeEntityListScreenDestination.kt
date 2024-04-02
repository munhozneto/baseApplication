package com.pmn.baseapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pmn.baseapplication.ui.list.EntityListContract
import com.pmn.baseapplication.ui.list.EntityListViewModel
import com.pmn.baseapplication.ui.list.composables.EntityListScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@Composable
fun SomeEntityListScreenDestination(
    navController: NavController,
    viewModel: EntityListViewModel = koinViewModel()
) {
    LaunchedEffect(true) {
        viewModel.navigation
            .onEach { destination ->
                if (destination is EntityListContract.Navigation.ToDetails) {
                    navController.navigateToDetails(destination.entityId)
                }
            }
            .collect()
    }

    LaunchedEffect(true) {
        viewModel.handleEvent(EntityListContract.Event.Loading)
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    EntityListScreen(
        state = state.value,
        onEventSent = { event ->
            viewModel.handleEvent(event)
        }
    )
}