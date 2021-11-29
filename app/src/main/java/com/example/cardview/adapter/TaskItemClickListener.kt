package com.example.cardview.adapter

import com.example.cardview.model.Tarefas


interface TaskItemClickListener {

    fun onTaskClicked(tarefas: Tarefas)
}