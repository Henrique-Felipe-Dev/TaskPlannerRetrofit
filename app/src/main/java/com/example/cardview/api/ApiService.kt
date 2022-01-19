package com.example.cardview.api

import com.example.cardview.model.Tarefas
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/todo")
    suspend fun listTarefas(): Response<List<Tarefas>>

    @GET("api/todo/{valor}")
    suspend fun findTarefaById(
        @Path("valor") valor: Int
    ): Response<Tarefas>

    @POST("api/todo")
    suspend fun addTarefa(
        @Body tarefas: Tarefas
    ): Response<Tarefas>

    @PUT("api/todo")
    suspend fun updateTarefa(
        @Body tarefas: Tarefas
    ): Response<Tarefas>

    @DELETE("api/todo/{tarefa}")
    suspend fun deleteTarefa(
        @Path("tarefa") valor: Int
    ): Response<Tarefas>

}