package com.example.cardview.repository

import com.example.cardview.api.RetrofitInstance
import com.example.cardview.model.Tarefas
import retrofit2.Response

class Repository {


    suspend fun listTarefas(): Response<List<Tarefas>>{
        return RetrofitInstance.api.listTarefas()
    }

    suspend fun addTarefa(tarefas: Tarefas): Response<Tarefas>{
        return RetrofitInstance.api.addTarefa(tarefas)
    }

    suspend fun updateTarefa(tarefas: Tarefas): Response<Tarefas>{
        return RetrofitInstance.api.updateTarefa(tarefas)
    }

    suspend fun deleteTarefa(valor: Int): Response<Tarefas>{
        return RetrofitInstance.api.deleteTarefa(valor)
    }

}