package com.pmn.data.repository

import com.pmn.data.remote.model.toDomain
import com.pmn.data.remote.service.Service
import com.pmn.domain.model.ResponseHandler
import com.pmn.domain.model.SomeEntityDomain
import com.pmn.domain.repository.SomeRepository

class SomeRepositoryImpl(
    private val service: Service
) : SomeRepository {

    override suspend fun getSomeEntityList(): ResponseHandler<List<SomeEntityDomain>> {
        val response = service.getSomeEntityList()
        return ResponseHandler(
            data = response.body()?.map { it.toDomain() },
            message = response.message(),
            code = response.code()
        )
    }

    override suspend fun getSomeEntity(id: Long): ResponseHandler<SomeEntityDomain> {
        val response = service.getSomeEntity()
        return ResponseHandler(
            data = response.body()?.toDomain(),
            message = response.message(),
            code = response.code()
        )
    }
}