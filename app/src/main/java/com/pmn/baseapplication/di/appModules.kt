package com.pmn.baseapplication.di

import com.pmn.baseapplication.ui.details.EntityViewModel
import com.pmn.baseapplication.ui.list.EntityListViewModel
import com.pmn.data.remote.service.ServiceFactory
import com.pmn.data.repository.SomeRepositoryImpl
import com.pmn.domain.repository.SomeRepository
import com.pmn.domain.usecase.GetSomeEntityListUseCase
import com.pmn.domain.usecase.GetSomeEntityUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        ServiceFactory.makeService()
    }
    single<SomeRepository> {
        SomeRepositoryImpl(
            service = get()
        )
    }
    single {
        GetSomeEntityListUseCase(
            repository = get(),
            defaultDispatcher = Dispatchers.IO
        )
    }
    single {
        GetSomeEntityUseCase(
            repository = get(),
            defaultDispatcher = Dispatchers.IO
        )
    }
    viewModel {
        EntityListViewModel(
            getSomeEntityListUseCase = get()
        )
    }
    viewModel {
        EntityViewModel(
            getSomeEntityUseCase = get()
        )
    }
}