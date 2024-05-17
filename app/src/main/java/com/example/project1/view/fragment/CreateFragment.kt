package com.example.project1.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
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
import com.example.project1.data.AppointmentDB
import com.example.project1.databinding.FragmentCreateBinding
import com.example.project1.retrofit.Breed
import com.example.project1.retrofit.RetrofitClient
import com.example.project1.model.Appointment
import com.example.project1.retrofit.ImagenResponse
import com.example.project1.viewmodel.AppointmentViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [CreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private  val binding get() = _binding!!

    private val optionsSpinner = listOf("Síntomas", "Solo duerme", "No come", "Fractura extremidad", "Tiene pulgas", "Tiene garrapatas", "Bota demasiado pelo")

    val ERROR_MESSAGE = "Ocurrió un error!"

    lateinit var field_name : EditText
    lateinit var field_breed : EditText
    lateinit var field_owner : EditText
    lateinit var field_tel : EditText
    lateinit var field_sel : Spinner
    lateinit var btnCreate : Button
    private  var opcion = "Síntomas"
    private val app: AppointmentViewModel by viewModels()

    private fun setupSpinner() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, optionsSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.syntomsSelection.adapter = adapter

        binding.syntomsSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedBreed = optionsSpinner[position]
                opcion = selectedBreed
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar el caso en que no se seleccione nada, si es necesario
            }
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)

        // Asignación de variables
        field_name = binding.editTextName
        field_breed = binding.editTextBreed
        field_owner = binding.editTextOwner
        field_tel = binding.editTextTelephone
        field_sel = binding.syntomsSelection
        btnCreate = binding.createButton

        // Funciones necesarias
        getBreeds()
        setupSpinner()
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

    private fun getPhoto (raza : String){
        // Get updated appointment data from EditTexts
        val createdName = field_name.text.toString()
        val createdOwner = field_owner.text.toString()
        val createdTelephone = field_tel.text.toString()
        val retrofitBring = RetrofitClient.consumeApi.getRandomDogImage(raza)
        var razaImageView = ""
        retrofitBring.enqueue(object : Callback<ImagenResponse> {
            override fun onResponse(
                call: Call<ImagenResponse>,
                response: Response<ImagenResponse>
            ) {
                razaImageView = response.body()?.message ?: ""
                if (razaImageView == "Breed not found (master breed does not exist)") {razaImageView = ""}
                Log.d("AHHHHHHHHHHHHHHHHH", razaImageView)
                val createAppointment = Appointment(
                    photo = razaImageView,
                    name_pet = createdName,
                    breed = raza,
                    name_owner = createdOwner,
                    phone_number = createdTelephone,
                    symptoms = opcion
                )
                // Create the appointment in the database
                app.insertAppointment(createAppointment)
                // Navigate to previous Detail Fragment
                findNavController().navigate(R.id.action_createFragment_to_homeFragment)

            }
            override fun onFailure(call: Call<ImagenResponse>, t: Throwable) {
                Log.d("entro en error: ", ERROR_MESSAGE)
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
            findNavController().navigate(R.id.action_createFragment_to_homeFragment)
        }
    }

    private fun updateButtonState() {
        if (btnCreate.isEnabled) {
            btnCreate.setTextColor(Color.WHITE)
            btnCreate.setTypeface(null, Typeface.BOLD)
            btnCreate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.strong_pink))
        } else {
            btnCreate.setTextColor(ContextCompat.getColor(requireContext(), R.color.lightgrey))
            btnCreate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.strong_pink))
        }
    }
    private fun verifyFields (){
        val isFieldNameEmpty = field_name.text.isNullOrEmpty()
        val isFielBreedEmpty = field_breed.text.isNullOrEmpty()
        val isFieldOwnerEmpty = field_owner.text.isNullOrEmpty()
        val isFieldTelEmpty = field_tel.text.isNullOrEmpty()

        btnCreate.isEnabled = !(isFieldNameEmpty || isFielBreedEmpty ||
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
        btnCreate.setOnClickListener {
            createAppointment()
        }
    }

    private fun showSymptomSelectionDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Selecciona un síntoma")
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }


    private fun createAppointment() {
        // Get updated appointment data from EditTexts
        val createBreed = field_breed.text.toString()

        try{lifecycleScope.launch {
            if (opcion != "Síntomas") {
                getPhoto(createBreed)
            } else {
                showSymptomSelectionDialog(requireContext())
            }
        }}
        catch(e: Exception){
            Log.d("error: ", e.toString())
        }
    }
}