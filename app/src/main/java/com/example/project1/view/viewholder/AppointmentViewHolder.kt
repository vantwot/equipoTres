package com.example.project1.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.R
import com.example.project1.databinding.CardAppointmentBinding
import com.example.project1.model.Appointment

class AppointmentViewHolder(binding: CardAppointmentBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingAppointment = binding
    val navController = navController
    fun setCardAppointment(appointment: Appointment) {
        bindingAppointment.petNamev.text = appointment.name_pet
        bindingAppointment.petId.text = appointment.id.toString();
        bindingAppointment.petSimto.text = appointment.symptoms

        bindingAppointment.itemCardView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("appointment", appointment)
            navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }

    }
}