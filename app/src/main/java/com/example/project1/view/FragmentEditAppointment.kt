package com.example.project1.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.project1.R
import com.example.project1.retrofit.Breed
import com.example.project1.retrofit.RetrofitClient
import com.example.project1.databinding.FragmentEditAppointmentBinding
import com.example.project1.room.Appointment
import com.example.project1.room.AppointmentApp
import com.example.project1.room.AppointmentDB
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

    lateinit var field_name : EditText
    lateinit var field_breed : EditText
    lateinit var field_owner : EditText
    lateinit var field_tel : EditText
    lateinit var btnEdit : Button

    lateinit var app: AppointmentApp

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

        // Obtener la instancia de la aplicación de la base de datos de citas (AppointmentApp)
        val applicationContext = requireContext().applicationContext
        if (applicationContext is AppointmentApp) {
            app = applicationContext
        } else {
            Toast.makeText(requireContext(), "Error: No se pudo obtener la aplicación", Toast.LENGTH_SHORT).show()
        }

        // Funciones necesarias
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
        val toolbar : Toolbar = binding.contentToolbar.findViewById(R.id.toolbar_edit)
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
            updateAppointment()
        }
    }

    private fun getAppointmentById(id: Int): LiveData<Appointment> {
        val appointmentLiveData = MutableLiveData<Appointment>()
        lifecycleScope.launch {
            val appointment = app.room.appointmentDao().getAppointmentById(id)
            appointmentLiveData.postValue(appointment)
        }
        return appointmentLiveData
    }


    private fun updateAppointment() {

        // Get updated appointment data from EditTexts
        val updatedName = field_name.text.toString()
        val updatedBreed = field_breed.text.toString()
        val updatedOwner = field_owner.text.toString()
        val updatedTelephone = field_tel.text.toString()

        val appointmentId = id

        lifecycleScope.launch {
            // Get the appointment that needs to be updated
            val appointment = app.room.appointmentDao().getAppointmentById(appointmentId)
            // Create a new Appointment object with updated data
            val updatedAppointment = appointment.copy(
                name_pet = updatedName,
                breed = updatedBreed,
                name_owner = updatedOwner,
                phone_number = updatedTelephone
            )
            // Update the appointment in the database
            app.room.appointmentDao().updateAppointment(updatedAppointment)
        }

          // Navigate to previous Detail Fragment
        findNavController().navigate(R.id.action_editAppointmentFragment_to_homeFragment)
    }
}