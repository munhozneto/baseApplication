package com.pmn.baseapplication.ui.details.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmn.baseapplication.R
import com.pmn.baseapplication.ui.details.EntityContract
import com.pmn.baseapplication.ui.list.composables.EntityListLoading
import com.pmn.domain.model.BaseRemoteState
import com.pmn.domain.model.BaseRemoteSyncState
import com.pmn.domain.model.SomeEntityDomain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityScreen(
    state: BaseRemoteState<SomeEntityDomain>,
    onEventSent: (event: EntityContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEventSent.invoke(EntityContract.Event.Back)
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                when (val syncState = state.syncState) {
                    is BaseRemoteSyncState.Idle -> {}
                    is BaseRemoteSyncState.Loading -> {
                        EntityLoading()
                    }

                    is BaseRemoteSyncState.Failure -> {}
                    is BaseRemoteSyncState.NetworkError -> {}
                    is BaseRemoteSyncState.TimeoutError -> {}
                    is BaseRemoteSyncState.EmptyResponse -> {}
                    is BaseRemoteSyncState.Success -> {
                        EntityDetails(entity = syncState.data!!)
                    }
                }
            }
        }
    )
}


@Composable
fun EntityDetails(
    entity: SomeEntityDomain
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = entity.description.orEmpty(),
            color = colorResource(id = R.color.black)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EntityDetailsPreview() {
    EntityDetails(
        entity = SomeEntityDomain(
            id = 1,
            text = "User",
            description = "User description"
        )
    )
}