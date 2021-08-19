package com.ai.tirios.ui.add_properties

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.databinding.FragmentAddPropertyBinding
import com.ai.tirios.dataclasses.BodyAddProperty
import com.ai.tirios.dataclasses.BodyEditProperty
import com.ai.tirios.dataclasses.BodyPropertyImageUpload
import com.ai.tirios.ui.property_details.PropertyGalleryAdapter
import com.ai.tirios.utils.Constants.SharedPref.Companion.CAMERA_REQUEST_CODE
import com.ai.tirios.utils.Constants.SharedPref.Companion.GALLERY_IMAGE_CAPTURE
import com.ai.tirios.utils.Constants.SharedPref.Companion.REQUEST_IMAGE_CAPTURE
import com.ai.tirios.utils.Utilities
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import com.ai.tirios.custom.Symbols
import com.ai.tirios.listeners.AdapterItemClick
import com.ai.tirios.listeners.AdapterItemClickForBundle
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddPropertyFragment : BaseFragment<FragmentAddPropertyBinding, AddPropertyViewModel>(), AddPropertyMedium {

    var binding: FragmentAddPropertyBinding?= null
    var sharedStorage: SharedStorage? = null
    var is_edit_property = false
    var state_array = arrayListOf<String>("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND","OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY", "AA", "AE", "AP", "AS", "FM", "GU", "MH", "MP", "PR", "PW", "UM", "VI")
    var selectedState = "AL"
    var imageBitmapBase64 = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        binding!!.addProperty = viewModel
        viewModel.medium = this
        binding!!.isEditProperty = is_edit_property
        binding!!.etMortgageAmount.setCurrency(Symbols.NONE)
        binding!!.etTaxAmount.setCurrency(Symbols.NONE)
        sharedStorage = SharedStorage(requireActivity())

        var arrayAdapter = ArrayAdapter(
            requireActivity(), R.layout.spinner_selected_item, state_array)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding!!.spinnerState.adapter = arrayAdapter

        var linearLayoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager

        if (requireArguments().containsKey("property_id")) {
            viewModel.getProperty(requireArguments().getInt("property_id").toString())
            is_edit_property = true
            binding!!.isEditProperty = is_edit_property
        }
        if (is_edit_property){
            binding!!.etStreet.isEnabled = false
            binding!!.etCity.isEnabled = false
            binding!!.etZipCode.isEnabled = false
            binding!!.spinnerState.isEnabled = false
        }

        viewModel.property.observe(requireActivity(), Observer {
            binding!!.property = it
            if (it.Documents != null)
                binding!!.rcView.adapter = PropertyGalleryAdapter(requireActivity(), it.Documents,
                object : AdapterItemClickForBundle {
                    override fun onItemClick(position: Int, bundle: Bundle, type: String) {

                    }

                })

            binding!!.spinnerState.setSelection(
                if (arrayAdapter.getPosition(it.State) != -1){
                    arrayAdapter.getPosition(it.State)
                }else{
                    0
                }
            )
        })

        viewModel.property_upload.observe(requireActivity(), Observer {
            if (imageBitmapBase64 != ""){
                viewModel.propertyImageUpload(it.Id.toString()
                    , BodyPropertyImageUpload(imageBitmapBase64,  "jpg",
                        System.currentTimeMillis().toString(), it.Id))
            }else{
                onBackArrowPressed()
            }
        })

        binding!!.spinnerState.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedState = state_array.get(binding!!.spinnerState.selectedItemPosition)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        })

        binding!!.etMortgageAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().contains(".")){
                    var splitArray = s.toString().split(".")
                    if (splitArray[0].length != 5){
                        binding!!.etMortgageAmount.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(s.toString().length + 2 ))
                    }else{
                        binding!!.etMortgageAmount.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(7))
                    }

                }else{
                    binding!!.etMortgageAmount.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding!!.etTaxAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().contains(".")){
                    var splitArray = s.toString().split(".")
                    if (splitArray[0].length != 5){
                        binding!!.etTaxAmount.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(s.toString().length + 2 ))
                    }else{
                        binding!!.etTaxAmount.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(7))
                    }

                }else{
                    binding!!.etTaxAmount.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_add_property
    override lateinit var viewModel: AddPropertyViewModel
        @Inject internal set

    override fun onBackArrowPressed() {
        requireActivity().onBackPressed()
        /*val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.addPropertyFragment, true)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.addProperty_to_PropertyList, Bundle(), navOptions)*/
    }

    override fun datePicker(from: String) {
        val c = Calendar.getInstance()
        var mYear = c[Calendar.YEAR]
        var mMonth = c[Calendar.MONTH]
        var mDay = c[Calendar.DAY_OF_MONTH]

        var datePickerDialog = DatePickerDialog(requireActivity(),R.style.DialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (from.equals(requireActivity().resources.getString(R.string.mortgage_due_date))) {
                    binding!!.tvMortgageDueDate.setText((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
                } else if (from.equals(requireActivity().resources.getString(R.string.due_date))) {
                    binding!!.tvDueDate.setText((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
                }
            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    override fun addProperty() {
        if (TextUtils.isEmpty(binding!!.etPropertyName.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_property_name))
        }else if(TextUtils.isEmpty(binding!!.etStreet.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_street_name))
        }else if(TextUtils.isEmpty(binding!!.etCity.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_city_name))
        }else if(TextUtils.isEmpty(binding!!.etZipCode.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_zipcode_name))
        }else if(TextUtils.isEmpty(selectedState)){
            showToast(requireActivity().resources.getString(R.string.please_enter_state_name))
        }/*else if(TextUtils.isEmpty(binding!!.etMortgageNo.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_mortgage_number))
        }else if(TextUtils.isEmpty(binding!!.etMortgageAmount.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_mortgage_amount))
        }else if(TextUtils.isEmpty(binding!!.tvMortgageDueDate.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_mortgage_due_date))
        }else if(TextUtils.isEmpty(binding!!.etPropertyTaxNo.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_property_tax_no))
        }else if(TextUtils.isEmpty(binding!!.etTaxAmount.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_tax_amount))
        }*/else if(TextUtils.isEmpty(binding!!.tvDueDate.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_due_date))
        }else {
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            if (is_edit_property) {
                viewModel.editProperty(
                    BodyEditProperty(
                        binding!!.etCity.text.toString(),
                        if(TextUtils.isEmpty(binding!!.etMortgageAmount.getCleanStringValue().trim())){
                            0.0
                        }else{
                            binding!!.etMortgageAmount.getCleanStringValue().trim().toDouble()
                        },
                        if(TextUtils.isEmpty(binding!!.tvMortgageDueDate.text.toString())){
                            null
                        }else{
                            Utilities.convertToUtc(binding!!.tvMortgageDueDate.text.toString()+" "+currentTime)
                        },
                        binding!!.etMortgageNo.text.toString(),
                        binding!!.etPropertyName.text.toString(),
                        requireArguments().getInt("property_id"),
                        if(TextUtils.isEmpty(binding!!.etTaxAmount.getCleanStringValue().trim())){
                            0.0
                        }else{
                            binding!!.etTaxAmount.getCleanStringValue().trim().toDouble()
                        },
                        if(TextUtils.isEmpty(binding!!.tvDueDate.text.toString())){
                            null
                        }else{
                            Utilities.convertToUtc(binding!!.tvDueDate.text.toString()+" "+currentTime)
                        },
                        binding!!.etPropertyTaxNo.text.toString(),
                        selectedState,
                        binding!!.etStreet.text.toString(),
                        binding!!.etZipCode.text.toString()))
            } else {
                println("======="+binding!!.etMortgageAmount.getCleanStringValue())
                println("======="+binding!!.etMortgageAmount.getCleanStringValue().toString().trim())
                viewModel.addProperty(
                    BodyAddProperty(
                        binding!!.etCity.text.toString(),
                        sharedStorage!!.getemail()!!,
                        sharedStorage!!.getfirstName()!!,
                        sharedStorage!!.getid()!!,
                        sharedStorage!!.getlastName()!!,
                        sharedStorage!!.getusername()!!,
                        if(TextUtils.isEmpty(binding!!.etMortgageAmount.getCleanStringValue().trim())){
                            0.0
                        }else{
                            binding!!.etMortgageAmount.getCleanStringValue().trim().toDouble()
                        },
                        if(TextUtils.isEmpty(binding!!.tvMortgageDueDate.text.toString())){
                            null
                        }else{
                            Utilities.convertToUtc(binding!!.tvMortgageDueDate.text.toString()+" "+currentTime)
                        },
                        binding!!.etMortgageNo.text.toString(),
                        binding!!.etPropertyName.text.toString(),
                        if(TextUtils.isEmpty(binding!!.etTaxAmount.getCleanStringValue().trim())){
                            0.0
                        }else{
                            binding!!.etTaxAmount.getCleanStringValue().trim().toDouble()
                        },
                        if(TextUtils.isEmpty(binding!!.tvDueDate.text.toString())){
                            null
                        }else{
                            Utilities.convertToUtc(binding!!.tvDueDate.text.toString()+" "+currentTime)
                        },
                        binding!!.etPropertyTaxNo.text.toString(),
                        selectedState,
                        binding!!.etStreet.text.toString(),
                        binding!!.etZipCode.text.toString()
                    )
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            var imageBitmap = data!!.extras!!.get("data") as Bitmap
            binding!!.imagePhoto.setImageBitmap(imageBitmap)
            imageBitmapBase64 = Utilities.bitmapToBase64(imageBitmap)!!
        }else if (requestCode == GALLERY_IMAGE_CAPTURE){
            val selectedImageUri: Uri = data!!.getData()!!
            binding!!.imagePhoto.setImageURI(selectedImageUri)
            var imageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri)
            imageBitmapBase64 = Utilities.bitmapToBase64(imageBitmap)!!
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_REQUEST_CODE->{
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //dispatchTakePictureIntent()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
