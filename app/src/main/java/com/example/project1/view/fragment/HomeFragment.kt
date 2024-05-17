package com.example.project1.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.R
import com.example.project1.databinding.FragmentHomeBinding
import com.example.project1.view.adapter.AppointmentAdapter
import com.example.project1.viewmodel.AppointmentViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val app: AppointmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        Log.d("Lista de citas:", app.getAllAppointment().toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity?.moveTaskToBack(true)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun controladores() {
        binding.button1.setOnClickListener {
            try {
                Log.e("Exito","It works")
                findNavController().navigate(R.id.action_homeFragment_to_createFragment)
            } catch(e: Exception) {
                Log.e("Error","Navegación fallida",e)
            }
        }

    }

    private fun observadorViewModel(){
        observerListAppointment()
        observerProgress()
    }

    private fun observerListAppointment(){
        app.listAppointment
        app.listAppointment.observe(viewLifecycleOwner){ listAppointment ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = AppointmentAdapter(listAppointment, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }
    private fun observerProgress(){
        app.progresState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
    }
    }

}
