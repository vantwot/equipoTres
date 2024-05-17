package com.example.project1.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project1.R
import com.example.project1.databinding.FragmentDateDetailBinding
import com.example.project1.viewmodel.AppointmentViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentDateDetail.newInstance] factory method to
 * create an instance of this fragment.
 */

class FragmentDateDetail : Fragment() {
    private lateinit var binding: FragmentDateDetailBinding
    private val app: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDateDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        controladores()
        // Inflate the layout for this fragment
        return binding.root
    }

    //    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment FragmentDateDetail.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            FragmentDateDetail().apply {
//                arguments = Bundle().apply {
//                }
//            }
//    }
    private fun controladores() {
        navigateBack ()
        binding.editButton.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_detailFragment_to_editAppointmentFragment)
            } catch(e: Exception) {
                Log.d("error",e.toString())
            }
        }

    }
    private fun navigateBack (){
        val arrow  = binding.backButton
        arrow.setOnClickListener {
            // Navegar a otro fragmento cuando se hace clic en el icono de navegación
            findNavController().popBackStack()
        }
    }
}