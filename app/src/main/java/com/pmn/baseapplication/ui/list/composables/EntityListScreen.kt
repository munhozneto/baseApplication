package com.pmn.baseapplication.ui.list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.pmn.baseapplication.ui.details.composables.EntityDetails
import com.pmn.baseapplication.ui.list.EntityListContract
import com.pmn.domain.model.BaseRemoteState
import com.pmn.domain.model.BaseRemoteSyncState
import com.pmn.domain.model.SomeEntityDomain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityListScreen(
    state: BaseRemoteState<List<SomeEntityDomain>>,
    onEventSent: (event: EntityListContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                navigationIcon = {}
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                when (val syncState = state.syncState) {
                    is BaseRemoteSyncState.Idle -> {}
                    is BaseRemoteSyncState.Loading -> {
                        EntityListLoading()
                    }

                    is BaseRemoteSyncState.Failure -> {}
                    is BaseRemoteSyncState.NetworkError -> {}
                    is BaseRemoteSyncState.TimeoutError -> {}
                    is BaseRemoteSyncState.EmptyResponse -> {}
                    is BaseRemoteSyncState.Success -> {
                        EntityList(entityList = syncState.data!!) {
                            onEventSent(
                                EntityListContract.Event.EntitySelection(it)
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun EntityList(
    entityList: List<SomeEntityDomain>,
    onItemClick: (Long) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(
                key = { it.id },
                items = entityList
            ) { entity ->
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable { onItemClick(entity.id) },
                    text = entity.text,
                    color = colorResource(id = R.color.black)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntityListPreview() {
    val entityList = listOf(
        SomeEntityDomain(id = 1, text = "User"),
        SomeEntityDomain(id = 2, text = "User II"),
        SomeEntityDomain(id = 3, text = "User III")
    )
    EntityList(entityList = entityList)
}