package com.example.cardview.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefas_table")
data class Tarefas (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var description: String,
    var assignetTo: String,
    var dueDate: String,
    var status: String
    ) {
}