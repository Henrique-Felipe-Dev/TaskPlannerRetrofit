package com.example.cardview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardview.adapter.TarefaAdapter
import com.example.cardview.adapter.TaskItemClickListener
import com.example.cardview.databinding.FragmentFormBinding
import com.example.cardview.databinding.FragmentListBinding
import com.example.cardview.model.Tarefas
import com.example.cardview.repository.Repository
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment(), TaskItemClickListener {

    val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)

        val adapter = TarefaAdapter(this, mainViewModel)
        binding.recyclerTarefa.layoutManager = LinearLayoutManager(context)
        binding.recyclerTarefa.adapter = adapter
        binding.recyclerTarefa.setHasFixedSize(true)

        mainViewModel.listTarefas()
        mainViewModel.myResponse.observe(viewLifecycleOwner, {
            response ->
            response.body()?.let { adapter.setData(it) }
        })

        mainViewModel.myDeleteResponse.observe(viewLifecycleOwner, {
            mainViewModel.listTarefas()
            Toast.makeText(
                context, "Tarefa deletada!",
                Toast.LENGTH_LONG
            ).show()
        })

        binding.floatingActionButton.setOnClickListener {
            mainViewModel.tarefaSelecionada = null
            findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }

        return binding.root
    }

    override fun onTaskClicked(tarefas: Tarefas) {
        mainViewModel.tarefaSelecionada = tarefas
        findNavController().navigate(R.id.action_listFragment_to_formFragment)
    }

}