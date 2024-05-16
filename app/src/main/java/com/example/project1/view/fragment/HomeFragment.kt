package com.example.project1.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val appointmentViewModel: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()

    }

    private fun controladores() {
        binding.button1.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createFragment)
        }

    }

    private fun observadorViewModel(){
        observerListAppointment()
        observerProgress()
    }

    private fun observerListAppointment(){

        appointmentViewModel.listAppointment
        appointmentViewModel.listAppointment.observe(viewLifecycleOwner){ listAppointment ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = AppointmentAdapter(listAppointment, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }
    private fun observerProgress(){
        appointmentViewModel.progresState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
    }
    }

}


