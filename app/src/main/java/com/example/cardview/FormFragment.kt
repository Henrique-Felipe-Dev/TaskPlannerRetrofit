package com.example.cardview

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cardview.databinding.FragmentFormBinding
import com.example.cardview.databinding.FragmentListBinding
import com.example.cardview.fragment.DatePickerFragment
import com.example.cardview.fragment.TimePickerListener
import com.example.cardview.model.Tarefas
import com.example.cardview.repository.Repository
import java.text.SimpleDateFormat
import java.util.*

class FormFragment : Fragment(), TimePickerListener, AdapterView.OnItemSelectedListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var _tarefaSelecionada: Tarefas? = null
    private val tarefaSelecionada get() = _tarefaSelecionada!!

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentFormBinding.inflate(inflater, container, false)

        carregarDados()

        mainViewModel.selectedDateLiveData.observe(viewLifecycleOwner, {
            selectedDate -> binding.editData.setText(selectedDate.toString())
        })

        binding.editData.setOnClickListener {
            DatePickerFragment(this).show(parentFragmentManager, "DatePicker")
        }

        binding.buttonAdd.setOnClickListener {
            inserirNoBanco()
        }

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun inputCheck(
        nome: String, desc: String, dono: String,
        data: String, status: String
    ): Boolean{
        return !(TextUtils.isEmpty(nome) &&
                TextUtils.isEmpty(desc) &&
                TextUtils.isEmpty(dono) &&
                TextUtils.isEmpty(data) &&
                TextUtils.isEmpty(status)
                )
    }

    fun inserirNoBanco(){
        val nome = binding.editNome.text.toString()
        val desc = binding.editDesc.text.toString()
        val dono = binding.editDono.text.toString()
        val data = binding.editData.text.toString()
        val status = binding.editStatus.text.toString()

        if(inputCheck(nome, desc, dono, data, status)){
            _tarefaSelecionada = mainViewModel.tarefaSelecionada
            var atualizarCriar = ""
            if (_tarefaSelecionada != null) {
                val tarefas = Tarefas(tarefaSelecionada.id, nome, desc, dono,
                    mainViewModel.selectedDateLiveData.value!!,
                    status
                )
                mainViewModel.updateTarefa(tarefas)
                atualizarCriar = "Tarefa Atualizada!"
            }else{
                val tarefas = Tarefas(0, nome, desc, dono,
                    mainViewModel.selectedDateLiveData.value!!,
                    status
                )
                mainViewModel.addTarefa(tarefas)
                atualizarCriar = "Tarefa Adicionada!"
            }
            Toast.makeText(
                context, atualizarCriar,
                Toast.LENGTH_LONG
            ).show()

            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }else{
            Toast.makeText(
                context, "Preencha todos os campos!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun carregarDados() {
        _tarefaSelecionada = mainViewModel.tarefaSelecionada
        if (_tarefaSelecionada != null) {
            binding.editNome.setText(tarefaSelecionada.name)
            binding.editDesc.setText(tarefaSelecionada.description)
            binding.editDono.setText(tarefaSelecionada.assignetTo)
            binding.editData.setText(tarefaSelecionada.dueDate)
            binding.editStatus.setText(tarefaSelecionada.status)
        } else {
            binding.editNome.text = null
            binding.editDesc.text = null
            binding.editDono.text = null
            binding.editData.text = null
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTimeSelected(date: Date) {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatedDate = formatter.format(date).toString()
        mainViewModel.selectedDateLiveData.postValue(formatedDate)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val itemAtPosition: String = p0?.getItemAtPosition(p2) as String
        Log.d("Developer", "itemAtPosition: $itemAtPosition")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }



}