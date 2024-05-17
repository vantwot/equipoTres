package com.example.project1.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.project1.R
import com.example.project1.databinding.CardAppointmentBinding
import com.example.project1.model.Appointment


// navController: NavController permite navegar en el viewHolder ya que findNavController() no sirve
// en un viewholder
class AppointmentViewHolder(binding: CardAppointmentBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingAppointment = binding
    val navController = navController
    fun setCardAppointment(appointment: Appointment) {
        bindingAppointment.petNamev.text = appointment.name_pet
        bindingAppointment.petId.text = "#${appointment.id.toString()}"
        bindingAppointment.petSimto.text = appointment.symptoms
        // Cargar la imagen desde la URL y aplicar la transformación CircleCrop
        Glide.with(bindingAppointment.root.context)
            .load(appointment.photo)
            .transform(CircleCrop())
            .into(bindingAppointment.petImage)

        bindingAppointment.itemCardView.setOnClickListener {
            val bundle = Bundle()
            //El bundle captura la información de la cita específica
            // El bundle permite pasar información de un fragmento a otro.
            bundle.putSerializable("clave", appointment)
            navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }

    }
}