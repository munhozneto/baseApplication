package com.pmn.data.remote.service

import com.pmn.data.remote.model.SomeEntityRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("https://d40fcf483f744edcb436512182535b73.api.mockbin.io/")
    suspend fun getSomeEntityList(): Response<List<SomeEntityRemote>>

    @GET("https://7ade34661f484f5d8d38937b0c4ea5c4.api.mockbin.io/")
    suspend fun getSomeEntity(
      //  @Path("id") id: Long
    ): Response<SomeEntityRemote>
}