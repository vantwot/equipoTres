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
import com.bumptech.glide.Glide
import com.example.project1.R
import com.example.project1.databinding.FragmentDateDetailBinding
import com.example.project1.model.Appointment
import com.example.project1.viewmodel.AppointmentViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentDateDetail.newInstance] factory method to
 * create an instance of this fragment.
 */

class FragmentDateDetail : Fragment() {
    private lateinit var binding: FragmentDateDetailBinding
    private val app: AppointmentViewModel by viewModels()
    private lateinit var receivedAppointment: Appointment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDateDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardImage.bringToFront()
        controladores()
        catchIncomingData()
    }

    private fun controladores() {
        navigateBack ()
        binding.deleteButton.setOnClickListener {
            deleteAppointment()
        }
        binding.editButton.setOnClickListener {
            try {
                val bundle = Bundle()
                bundle.putSerializable("dataAppointment", receivedAppointment)
                findNavController().navigate(R.id.action_detailFragment_to_editAppointmentFragment, bundle)
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

    private fun catchIncomingData(){
        val receivedBundle = arguments
        receivedAppointment = receivedBundle?.getSerializable("clave") as Appointment
        // Cargar la imagen desde la URL
        Glide.with(this)
            .load(receivedAppointment.photo)
            .into(binding.petBreedImage)
        binding.numberAppointmnet.text = "#${receivedAppointment.id.toString()}"
        binding.titleTextDetailsName.text = receivedAppointment.name_pet
        binding.petBreedName.text = receivedAppointment.breed
        binding.ownerPhone.text = "Telefono: ${receivedAppointment.phone_number}"
        binding.ownerName.text = "Propietario: ${receivedAppointment.name_owner}"
        binding.petSymptoms.text = receivedAppointment.symptoms
    }

    private fun deleteAppointment(){
        app.deleteAppointment(receivedAppointment)
        app.getAllAppointment()
        findNavController().popBackStack()
    }
}