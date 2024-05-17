package com.example.project1.view.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.project1.R
import com.example.project1.retrofit.Breed
import com.example.project1.retrofit.RetrofitClient
import com.example.project1.databinding.FragmentEditAppointmentBinding
import com.example.project1.model.Appointment
import com.example.project1.room.AppointmentApp
import com.example.project1.viewmodel.AppointmentViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentEditAppointment.newInstance] factory method to
 * create an instance of this fragment.
 */

class FragmentEditAppointment : Fragment() {
    private var _binding: FragmentEditAppointmentBinding? = null
    private  val binding get() = _binding!!

    val ERROR_MESSAGE = "Ocurrió un error!"

    var idAppointment: Int = 1

    lateinit var field_name : EditText
    lateinit var field_breed : EditText
    lateinit var field_owner : EditText
    lateinit var field_tel : EditText
    lateinit var btnEdit : Button
    private val app: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditAppointmentBinding.inflate(inflater, container, false)
        // Asignación de variables
        field_name = binding.editTextName
        field_breed = binding.editTextBreed
        field_owner = binding.editTextOwner
        field_tel = binding.editTextTelephone
        btnEdit = binding.editButton

        // Funciones necesarias
        loadAppointment(idAppointment)
        getBreeds()
        setToolbar()
        eventInputs()

        return  binding.root
    }

    private fun getBreeds (){
        val retrofitBring = RetrofitClient.consumeApi.getBring()
        retrofitBring.enqueue(object : Callback<Breed>{
            override fun onResponse(call: Call<Breed>, response: Response<Breed>) {
                val breedList = response.body()?.message ?: emptyList()
                setupAutoCompleteTextView(breedList)
            }

            override fun onFailure(call: Call<Breed>, t: Throwable) {
                Toast.makeText(requireContext(), ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setupAutoCompleteTextView(breedList: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedList)
        (binding.editTextBreed as? AutoCompleteTextView)?.apply {
            setAdapter(adapter)
            threshold = 2 // Configura el número de caracteres que se deben escribir antes de que se muestren las sugerencias
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setToolbar (){
        val toolbar : Toolbar = binding.contentToolbar.toolbarEdit
        toolbar.setNavigationOnClickListener {
            // Navegar a otro fragmento cuando se hace clic en el icono de navegación
            findNavController().navigate(R.id.action_editAppointmentFragment_to_homeFragment)
        }
    }

    private fun updateButtonState() {
        if (btnEdit.isEnabled) {
            btnEdit.setTextColor(Color.WHITE)
            btnEdit.setTypeface(null, Typeface.BOLD)
            btnEdit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.strong_pink))
        } else {
            btnEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.lightgrey))
            btnEdit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.strong_pink))
        }
    }
    private fun verifyFields (){
        val isFieldNameEmpty = field_name.text.isNullOrEmpty()
        val isFielBreedEmpty = field_breed.text.isNullOrEmpty()
        val isFieldOwnerEmpty = field_owner.text.isNullOrEmpty()
        val isFieldTelEmpty = field_tel.text.isNullOrEmpty()

        btnEdit.isEnabled = !(isFieldNameEmpty || isFielBreedEmpty ||
                isFieldOwnerEmpty || isFieldTelEmpty)

        updateButtonState()

    }
    private fun createTextWatcher(editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                verifyFields()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
    }

    private fun eventInputs(){
        field_name.addTextChangedListener(createTextWatcher(field_name))
        field_breed.addTextChangedListener(createTextWatcher(field_breed))
        field_owner.addTextChangedListener(createTextWatcher(field_owner))
        field_tel.addTextChangedListener(createTextWatcher(field_tel))

        btnEdit.setOnClickListener {
            updateAppointment(idAppointment)
        }
    }

    private fun loadAppointment(id:Int) {
//        lateinit var appointment : Appointment
//        try { lifecycleScope.launch {
//            // Get the appointment that needs to be updated
//            appointment = app.getAppointmentById(id)
//
//            // Actualizar la UI con los detalles de la cita
//            field_name.setText(appointment.name_pet)
//            field_breed.setText(appointment.breed)
//            field_owner.setText(appointment.name_owner)
//            field_tel.setText(appointment.phone_number)
//
//        }} catch (e: Exception) {
//            Log.d("error: ", e.toString())
//            // Maneja el error de alguna manera, como mostrar un mensaje al usuario
//        }
    }

    private fun updateAppointment(id:Int) {
        // Get updated appointment data from EditTexts
        val updatedName = field_name.text.toString()
        val updatedBreed = field_breed.text.toString()
        val updatedOwner = field_owner.text.toString()
        val updatedTelephone = field_tel.text.toString()

        try{lifecycleScope.launch {
            // Get the appointment that needs to be updated
            val appointment = app.getAppointmentById(id)
            // Create a new Appointment object with updated data
            val updatedAppointment = appointment.copy(
                name_pet = updatedName,
                breed = updatedBreed,
                name_owner = updatedOwner,
                phone_number = updatedTelephone
            )
            // Update the appointment in the database
            app.updateAppointment(updatedAppointment)
        }}
        catch(e: Exception){
            Log.d("error: ", e.toString())
        }

          // Navigate to previous Detail Fragment
        findNavController().navigate(R.id.action_editAppointmentFragment_to_homeFragment)
    }
}