package com.example.cardview.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cardview.model.Tarefas
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTarefa(tarefas: Tarefas)

    @Query("SELECT * FROM tarefas_table ORDER BY id ASC")
    fun queryAllTarefas(): Flow<List<Tarefas>>

    @Query("SELECT * FROM tarefas_table WHERE id = :id")
    fun queryById(id: Int): Flow<Tarefas?>

    @Update
    suspend fun update(task: Tarefas)

    @Delete
    suspend fun deleteTarefaRoom(task: Tarefas)

}