//package com.example.project1.view.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.project1.databinding.CardAppointmentBinding
//import com.example.project1.databinding.FragmentCreateBinding
//import com.example.project1.model.Appointment
//import com.example.project1.view.viewholder.AppointmentViewHolder
//
//class AppointmentAdapter (private val listAppointment:MutableList<Appointment>):RecyclerView.Adapter<AppointmentViewHolder>{
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
//        val binding = CardAppointmentBinding.inflate(LayoutInflater.from(parent.context),parent, false)
//        return AppointmentViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
//        val appointment = listAppointment[position]
//        holder.setItemInventory(appointment)
//    }
//
//    override fun getItemCount(): Int {
//        return listAppointment.size
//    }
//
//}