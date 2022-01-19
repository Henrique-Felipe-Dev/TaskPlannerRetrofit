package com.example.cardview.repository

import androidx.lifecycle.LiveData
import com.example.cardview.api.RetrofitInstance
import com.example.cardview.data.TarefaDao
import com.example.cardview.model.Tarefas
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class Repository (private val tarefaDao: TarefaDao) {

    //Requisições API

    suspend fun listTarefas(): Response<List<Tarefas>>{
        return RetrofitInstance.api.listTarefas()
    }

    suspend fun findTarefaById(valor: Int): Response<Tarefas>{
        return RetrofitInstance.api.findTarefaById(valor)
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

    //Requisições Room

    fun queryAllTarefas(): Flow<List<Tarefas>> {
        return tarefaDao.queryAllTarefas()
    }


    suspend fun insertTarefa(tarefas: Tarefas){
        tarefaDao.insertTarefa(tarefas)
    }

    fun queryById(id: Int): Flow<Tarefas?>{
        return tarefaDao.queryById(id)
    }

    suspend fun update(tarefas: Tarefas){
        tarefaDao.update(tarefas)
    }

    suspend fun deleteTarefaRoom(tarefas: Tarefas){
        return tarefaDao.deleteTarefaRoom(tarefas)
    }

}