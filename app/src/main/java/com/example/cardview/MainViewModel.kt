package com.example.cardview

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardview.data.TarefaDao
import com.example.cardview.model.Tarefas
import com.example.cardview.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val tarefaDao: TarefaDao
): ViewModel() {

    private val _myGetResponse = MutableLiveData<Response<List<Tarefas>>>()
    val myResponse: LiveData<Response<List<Tarefas>>> = _myGetResponse

    private val _myDeleteResponse = MutableLiveData<Response<Tarefas>>()
    val myDeleteResponse: LiveData<Response<Tarefas>> = _myDeleteResponse

    var tarefaSelecionada: Tarefas? = null

    val selectedDateLiveData: MutableLiveData<String> = MutableLiveData()

    private lateinit var _myQueryResponse: Flow<List<Tarefas>>

    init {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = formatter.format(Date())
        selectedDateLiveData.postValue(date.toString())
        viewModelScope.launch {
            _myQueryResponse = repository.queryAllTarefas()
            Log.d("Pintão", _myQueryResponse.first().toString())
        }
    }

    val myQueryResponse: Flow<List<Tarefas>> = _myQueryResponse

    fun listTarefas(){
        viewModelScope.launch {
            /*
            val response = repository.queryAllTarefas()
            _myQueryResponse = response
            for (tarefa in response.first()){
                try{
                    val findTarefaApi = repository.findTarefaById(tarefa.id)
                    if (findTarefaApi.body() == null){
                        try {
                            repository.addTarefa(tarefa)
                            Log.d("Pinto", "Tarefa adicionada!")
                        }catch (e: Exception){
                            Log.d("Pinto", e.message.toString())
                        }
                    }

                }catch (e: Exception){
                    Log.d("Pinto", e.message.toString())
                }
            }
             */

            try{
                val response = repository.listTarefas()
                _myGetResponse.value = response
                if (response.isSuccessful){
                    val listTarefas = response.body()!!
                    for (tarefa in listTarefas){
                        val findTarefa = repository.queryById(tarefa.id)
                        if (findTarefa.first() != null){
                            repository.update(tarefa)
                        }else{
                            repository.insertTarefa(tarefa)
                        }
                    }
                }else{
                    Log.d("Developer",
                        "Request Error: ${response.errorBody().toString()}")
                }
            }catch (e: Exception){
                Log.e("Developer", "Error", e)
            }
        }
    }

    fun addTarefa(tarefas: Tarefas){
        viewModelScope.launch {
            try{
                val response = repository.addTarefa(tarefas)
                if (response.isSuccessful) {
                    tarefaDao.insertTarefa(response.body()!!)
                } else {
                    tarefaDao.insertTarefa(tarefas)
                }
            }catch (e: Exception){
                tarefaDao.insertTarefa(tarefas)
            }
        }
    }

    fun updateTarefa(tarefas: Tarefas){
        viewModelScope.launch {
            try{
                val response = repository.updateTarefa(tarefas)
                repository.update(tarefas)
            }catch (e: Exception){
                repository.update(tarefas)
            }

        }
    }

    fun deleteTarefa(tarefas: Tarefas){
        viewModelScope.launch {
            try{
                val response = repository.deleteTarefa(tarefas.id)
                _myDeleteResponse.value = response
                repository.deleteTarefaRoom(tarefas)
            }catch (e: Exception){
                repository.deleteTarefaRoom(tarefas)
            }

        }
    }
}