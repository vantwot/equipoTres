package com.example.project1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.project1.R
import com.example.project1.databinding.FragmentEditAppointmentBinding
import com.example.project1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private  var _binding: FragmentHomeBinding? = null
    private  val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        nav_createView()
        return  binding.root

    }

    private fun nav_createView(){
        binding.button1.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_createFragment)
        }
    }

}

