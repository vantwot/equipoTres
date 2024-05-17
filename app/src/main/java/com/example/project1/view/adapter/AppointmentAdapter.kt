package com.example.project1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.databinding.CardAppointmentBinding
import com.example.project1.model.Appointment
import com.example.project1.view.viewholder.AppointmentViewHolder

class AppointmentAdapter(private val listAppointment:MutableList<Appointment>, private val navController: NavController):RecyclerView.Adapter<AppointmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = CardAppointmentBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return AppointmentViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listAppointment.size
    }

    // Detecta en donde se dió clic
    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = listAppointment[position]
        holder.setCardAppointment(appointment)
    }
}