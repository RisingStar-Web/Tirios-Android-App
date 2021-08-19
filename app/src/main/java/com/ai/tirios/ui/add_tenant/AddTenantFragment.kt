package com.ai.tirios.ui.add_tenant

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.BuildConfig
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.base.BaseFragment
import com.ai.tirios.databinding.FragmentAdTenantBinding
import com.ai.tirios.dataclasses.BodyTenantUpload
import com.ai.tirios.utils.Constants
import com.ai.tirios.utils.Utilities
import com.google.gson.JsonObject
import com.rilixtech.widget.countrycodepicker.Country
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AddTenantFragment : BaseFragment<FragmentAdTenantBinding, AddTenantViewModel>(), AddTenantMidium {

    var binding: FragmentAdTenantBinding? = null
    var family_members = 1
    var second_due_data_enable = false
    var countryCode = "+1"
    var sharedStorage: SharedStorage? = null
    var is_edit_tenant = false
    var imageBitmapBase64 = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = dataBinding
        binding!!.addTenant = viewModel
        viewModel.medium = this
        binding!!.familyMembers = family_members
        binding!!.secondDueDataEnable = second_due_data_enable
        sharedStorage = SharedStorage(requireActivity())

        var arrayAdapter = ArrayAdapter(
            requireActivity(), R.layout.spinner_selected_item,
            resources.getStringArray(R.array.payment_frequency))
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding!!.spinnerPayment.adapter = arrayAdapter

        var linearLayoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding!!.rcView.layoutManager = linearLayoutManager

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Navigation.findNavController(binding!!.root).popBackStack()
                    //requireActivity().onBackPressed()
                }
            }
        )

        if (requireArguments().containsKey("tenant_id")) {
            viewModel.getTenant(requireArguments().getInt("tenant_id").toString())
            is_edit_tenant = true
            binding!!.isEditTenant = is_edit_tenant
        }

        viewModel.tenant.observe(requireActivity(), androidx.lifecycle.Observer {
            binding!!.tenantBody = it
            bindRent(it.Rent.toString())
            bindRentDeposit(it.RentDeposit.toString())
            family_members = it.NumberOfFamilyMembers
            binding!!.familyMembers = family_members
            binding!!.spinnerPayment.setSelection(it.PaymentFrequency-1)
            binding!!.ccp.setCountryForPhoneCode(it.getCountryCode().toInt())

            if (it.TenantDocuments != null)
                binding!!.rcView.adapter = TenantAgreementAdapter(requireActivity(), it.TenantDocuments)
        })

        viewModel.tenant_edit.observe(requireActivity(), androidx.lifecycle.Observer {
            if (!imageBitmapBase64.equals("")){
                viewModel.uploadTenantImage(requireArguments().getInt("tenant_id").toString(), BodyTenantUpload(
                    imageBitmapBase64,3,"LeaseAgreement","jpg",
                    System.currentTimeMillis().toString(),
                    requireArguments().getInt("tenant_id")
                ))
            }else{
                onBackArrowPressed()
            }
        })

        binding!!.spinnerPayment.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0){
                    binding!!.secondDueDataEnable = false
                }else{
                    binding!!.secondDueDataEnable = true
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        })

        binding!!.ccp.setOnCountryChangeListener(object : CountryCodePicker.OnCountryChangeListener{
            override fun onCountrySelected(selectedCountry: Country?) {
                countryCode = "+"+binding!!.ccp.selectedCountryCode.toString()
            }
        })

        binding!!.etRentDeposit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().contains(".")){
                    var splitArray = s.toString().split(".")
                    if (splitArray[0].length != 5){
                        binding!!.etRentDeposit.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(s.toString().length + 2 ))
                    }else{
                        binding!!.etRentDeposit.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(7))
                    }

                }else{
                    binding!!.etRentDeposit.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding!!.etRent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().contains(".")){
                    var splitArray = s.toString().split(".")
                    if (splitArray[0].length != 5){
                        binding!!.etRent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(s.toString().length + 2 ))
                    }else{
                        binding!!.etRent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(7))
                    }

                }else{
                    binding!!.etRent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding!!.ccp.isClickable = !Utilities.isProduction()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.fragment_ad_tenant
    override lateinit var viewModel: AddTenantViewModel
        @Inject internal set

    override fun onBackArrowPressed() {
        /*var bundle= Bundle()
        bundle.putInt("Id", requireArguments().getInt("property_id"))
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.adTenantFragment, true)
            .build()
        Navigation.findNavController(binding!!.root)
            .navigate(R.id.adtenant_to_property_details, bundle, navOptions)*/
        requireActivity().onBackPressed()
    }

    override fun adFamilyMember() {
        family_members = family_members + 1
        binding!!.familyMembers = family_members
    }

    override fun removeFamilyMember() {
        if (family_members != 1){
            family_members = family_members -1
            binding!!.familyMembers = family_members
        }
    }

    override fun datePicker(from: String) {
        val c = Calendar.getInstance()
        var mYear = c[Calendar.YEAR]
        var mMonth = c[Calendar.MONTH]
        var mDay = c[Calendar.DAY_OF_MONTH]

        DatePickerDialog(requireActivity(),R.style.DialogTheme,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if (from.equals(requireActivity().resources.getString(R.string.due_date))){
                    binding!!.tvFirstDueDate.setText((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
                }else if (from.equals(requireActivity().resources.getString(R.string.first_due_date))){
                    binding!!.tvFirstDueDate.setText((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
                }else if (from.equals(requireActivity().resources.getString(R.string.second_due_data))){
                    binding!!.tvSecondDueDate.setText((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
                }
                              }, mYear, mMonth, mDay
        ).show()
    }

    override fun adTenant() {
        if (TextUtils.isEmpty(binding!!.etFirstName.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_tenant_first_name))
        }else if(TextUtils.isEmpty(binding!!.etLastName.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_tenant_last_name))
        }else if(TextUtils.isEmpty(binding!!.etMobile.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_mobile_number))
        }else if(binding!!.etMobile.text.toString().length != 10){
            showToast(requireActivity().resources.getString(R.string.please_enter_a_valid_ten_digit_mobile_number))
        }else if(TextUtils.isEmpty(binding!!.etEmail.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_email_address))
        }else if(Utilities.isEmailValid(binding!!.etEmail.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_valid_email_address))
        }else if(TextUtils.isEmpty(binding!!.etRentDeposit.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_rent_deposit))
        }else if(TextUtils.isEmpty(binding!!.etRent.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_rent))
        }else if(TextUtils.isEmpty(binding!!.tvFirstDueDate.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_due_date))
        }else if(TextUtils.isEmpty(binding!!.tvFirstDueDate.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_first_due_date))
        }else if(binding!!.spinnerPayment.selectedItemPosition == 1
            && TextUtils.isEmpty(binding!!.tvSecondDueDate.text.toString())){
            showToast(requireActivity().resources.getString(R.string.please_enter_second_due_date))
        }else{
            val currentTime= SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            if (is_edit_tenant){
                var jsonObject= JsonObject()
                //jsonObject.addProperty("documentsUpdated", false)
                jsonObject.addProperty("dueDate",Utilities.convertToUtc(binding!!.tvFirstDueDate.text.toString()+" "+currentTime))
                jsonObject.addProperty("email",binding!!.etEmail.text.toString())
                jsonObject.addProperty("firstName",binding!!.etFirstName.text.toString())
                /*jsonObject.addProperty("isActive",true)
                jsonObject.addProperty("isInvited",true)
                jsonObject.addProperty("isResiding",true)*/
                jsonObject.addProperty("lastName",binding!!.etLastName.text.toString())
                jsonObject.addProperty("mobile",countryCode+binding!!.etMobile.text.toString())
                jsonObject.addProperty("numberOfFamilyMembers",binding!!.tvFamilyMembers.text.toString().toInt())
                jsonObject.addProperty("paymentFrequency",binding!!.spinnerPayment.selectedItemPosition+1)
                jsonObject.addProperty("propertyId", requireArguments().getInt("property_id", 0))
                jsonObject.addProperty("rent",binding!!.etRent.text.toString().toDouble())
                jsonObject.addProperty("rentDeposit",binding!!.etRentDeposit.text.toString().toDouble())
                if (binding!!.spinnerPayment.selectedItemPosition == 1){
                    jsonObject.addProperty("secondDueDate",Utilities.convertToUtc(binding!!.tvSecondDueDate.text.toString()+" "+currentTime))
                }
                jsonObject.addProperty("tenantPropertyId",requireArguments().getInt("tenant_id"))
                //jsonObject.addProperty("status",2)
                //jsonObject.addProperty("userId",sharedStorage!!.getid()!!)

                viewModel.editTenant(jsonObject)

            }else{
                var jsonObject= JsonObject()
                jsonObject.addProperty("dueDate", Utilities.convertToUtc(binding!!.tvFirstDueDate.text.toString()+" "+currentTime))
                jsonObject.addProperty("email",binding!!.etEmail.text.toString())
                jsonObject.addProperty("firstName",binding!!.etFirstName.text.toString())
                jsonObject.addProperty("isActive",true)
                jsonObject.addProperty("isInvited",true)
                jsonObject.addProperty("isResiding",true)
                jsonObject.addProperty("lastName",binding!!.etLastName.text.toString())
                jsonObject.addProperty("mobile",countryCode+binding!!.etMobile.text.toString())
                jsonObject.addProperty("numberOfFamilyMembers",binding!!.tvFamilyMembers.text.toString().toInt())
                jsonObject.addProperty("paymentFrequency",binding!!.spinnerPayment.selectedItemPosition+1)
                jsonObject.addProperty("propertyId", requireArguments().getInt("property_id", 0))
                jsonObject.addProperty("rent",binding!!.etRent.text.toString().toDouble())
                jsonObject.addProperty("rentDeposit",binding!!.etRentDeposit.text.toString().toDouble())
                if (binding!!.spinnerPayment.selectedItemPosition == 1){
                    jsonObject.addProperty("secondDueDate",Utilities.convertToUtc(binding!!.tvSecondDueDate.text.toString()+" "+currentTime))
                }
                jsonObject.addProperty("status",1)
                jsonObject.addProperty("userId",sharedStorage!!.getid()!!)
                jsonObject.addProperty("userName",sharedStorage!!.getusername()!!)

                viewModel.adTenant(jsonObject)

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.SharedPref.REQUEST_IMAGE_CAPTURE && data!= null) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            binding!!.imagePhoto.setImageBitmap(imageBitmap)
            imageBitmapBase64 = Utilities.bitmapToBase64(imageBitmap)!!
        }else if (requestCode == Constants.SharedPref.GALLERY_IMAGE_CAPTURE && data!= null){
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
            Constants.SharedPref.CAMERA_REQUEST_CODE ->{
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    dispatchTakePictureIntent()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun bindRent(s: String){
        if (s.toString().contains(".")){
            var splitArray = s.toString().split(".")
            if (splitArray[0].length != 5){
                binding!!.etRent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(s.toString().length + 2 ))
            }else{
                binding!!.etRent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(7))
            }

        }else{
            binding!!.etRent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
        }
        binding!!.etRent.setText(s)
    }

    fun bindRentDeposit(s: String){
        if (s.toString().contains(".")){
            var splitArray = s.toString().split(".")
            if (splitArray[0].length != 5){
                binding!!.etRentDeposit.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(s.toString().length + 2 ))
            }else{
                binding!!.etRentDeposit.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(7))
            }

        }else{
            binding!!.etRentDeposit.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(5))
        }
        binding!!.etRentDeposit.setText(s)
    }

}
