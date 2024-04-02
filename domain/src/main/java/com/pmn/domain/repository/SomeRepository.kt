package com.pmn.domain.repository

import com.pmn.domain.model.ResponseHandler
import com.pmn.domain.model.SomeEntityDomain

interface SomeRepository {
   suspend fun getSomeEntityList(): ResponseHandler<List<SomeEntityDomain>>
   suspend fun getSomeEntity(
      id: Long
   ): ResponseHandler<SomeEntityDomain>
}