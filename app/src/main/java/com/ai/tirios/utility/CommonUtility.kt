package com.ai.tirios.utility

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.ai.tirios.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Created by Lenovo on 10-05-2018.
 */
object CommonUtility {

    fun callToNumber(context: Context, mobileNum: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$mobileNum")
        context.startActivity(intent)

    }

    fun getHashKey(context: Context){
        try {
            val info = context.packageManager.getPackageInfo("com.ai.tirios", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash=====:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }
    }

//    fun isNetworkConnected(context: Context): Boolean {
//        var connected = false
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        connected =
//            cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isAvailable && cm.activeNetworkInfo!!.isConnected
//        return connected
//    }

    fun showNoInternetMessage(activity: Activity) {
        val builder = android.app.AlertDialog.Builder(activity)
        builder.setMessage("Please check your internet connection.")
        builder.setPositiveButton("Ok") { dialog, id -> }.show()

    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun checkEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



    fun dpToPx(con: Context, dp: Int): Int {
        val density = con.resources.displayMetrics.density
        return Math.round(dp * density)
    }

    fun getDayDate(epoch: Long): String {
        val sdf = SimpleDateFormat("EEE dd")
        val date = Date(epoch * 1000)
        return sdf.format(date)
    }

    fun getDay(epoch: Long): String {
        val sdf = SimpleDateFormat("EEE")
        val date = Date(epoch * 1000)
        return sdf.format(date)
    }

    fun openCalenderWithFutureDate(activity: Activity, getCalenderValue: GetCalenderValue?) {
        val date = arrayOfNulls<String>(1)
        val newCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val dateFormatter = SimpleDateFormat("MMM dd,hh:mm a")
        val dateFormatter2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ")
        val datePickerDialog =
            DatePickerDialog(
                activity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)

                    var d1 = dateFormatter.format(newDate.time)
                    var d2 = dateFormatter2.format(newDate.time)
                    getCalenderValue?.selectedDate(d1+"#"+d2)
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
            )
        val c2 = Calendar.getInstance()
        datePickerDialog.datePicker.minDate = c2.timeInMillis
        datePickerDialog.show()
    }

    fun showDateTimePicker(activity: Activity, getCalenderValue: GetCalenderValue?) {
        val dateFormatter = SimpleDateFormat("MMM dd,hh:mm a")
        val dateFormatter2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ")
        val currentDate: Calendar = Calendar.getInstance()
        var date = Calendar.getInstance()
        var datePickerFromToday = DatePickerDialog(activity, R.style.DialogTheme,
            { view, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)
                TimePickerDialog(activity, R.style.DialogTheme,
                    { view, hourOfDay, minute ->
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)
                        var d1 = dateFormatter.format(date.time)
                        var d2 = dateFormatter2.format(date.time)
                        getCalenderValue?.selectedDate(d1+"#"+d2)
                        Log.v("Testing", "The choosen one " + d1+"#"+d2)
                    },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    false
                ).show()
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DATE)
        )
        datePickerFromToday.datePicker.minDate = System.currentTimeMillis() + 24 * 60 * 60 * 1000
        datePickerFromToday.show()
    }

    fun showDatePicker(activity: Activity, getCalenderValue: GetCalenderValue?) {
        val dateFormatter = SimpleDateFormat("MMM dd yyyy")
        val dateFormatter2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ")
        val currentDate: Calendar = Calendar.getInstance()
        var date = Calendar.getInstance()
        DatePickerDialog(activity, R.style.DialogTheme,
            { view, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)
                var d1 = dateFormatter.format(date.time)
                var d2 = dateFormatter2.format(date.time)
                getCalenderValue?.selectedDate(d1+"#"+d2)
                Log.v("Testing", "The choosen one " + d1+"#"+d2)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DATE)
        ).show()
    }

    fun removeLastCharacter(str: String): String {
        var str = str
        if (str != null && str.length > 0 && str[str.length - 1] == 'x') {
            str = str.substring(0, str.length - 1)
        }
        return str
    }

    /*fun OpenCalender(activity: Activity, getCalenderValue: GetCalenderValue?) {
        val date = arrayOfNulls<String>(1)
        val newCalendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val datePickerDialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate.set(year, monthOfYear, dayOfMonth)
            date[0] = dateFormatter.format(newDate.time)
            getCalenderValue?.selectedDate(date[0])
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    fun OpenCalenderWithOutFutureDate(activity: Activity, getCalenderValue: GetCalenderValue?) {
        val date = arrayOfNulls<String>(1)
        val newCalendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val datePickerDialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate.set(year, monthOfYear, dayOfMonth)
            date[0] = dateFormatter.format(newDate.time)
            getCalenderValue?.selectedDate(date[0])
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
        val c2 = Calendar.getInstance()
        datePickerDialog.datePicker.maxDate = c2.timeInMillis
        datePickerDialog.show()
    }

    fun OpenLimitCalender(activity: Activity, getCalenderValue: GetCalenderValue?) {
        val date = arrayOfNulls<String>(1)
        val newCalendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val datePickerDialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate.set(year, monthOfYear, dayOfMonth)
            date[0] = dateFormatter.format(newDate.time)
            getCalenderValue?.selectedDate(date[0])
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
        val c2 = Calendar.getInstance()
        c2.add(Calendar.YEAR, -18)
        datePickerDialog.datePicker.maxDate = c2.timeInMillis
        datePickerDialog.show()
    }
*/
    fun formateDateFromstring(
        inputFormat: String,
        outputFormat: String,
        inputDate: String
    ): String {
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat(inputFormat, Locale.getDefault())
        val df_output = SimpleDateFormat(outputFormat, Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate

    }

    fun timeGap(startTime: String): Map<String, String> {
        var diffDays = ""
        var seconds = ""
        var diffHours = ""
        var diff = 0L
        var diffMinutes = ""
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val currentDateAndTime = dateFormatter.format(Date())
        var d1: Date? = null
        var d2: Date? = null

        try {
            d1 = dateFormatter.parse(currentDateAndTime)
            d2 = dateFormatter.parse(LocalTimeFormat(startTime))

            diff = d2!!.time - d1!!.time

            diffMinutes = (diff / (60 * 1000) % 60).toInt().toString()
            seconds = ((diff / 1000) % 60).toInt().toString()
            diffHours = (diff / (60 * 60 * 1000) % 24).toInt().toString()
            diffDays = (diff / (24 * 60 * 60 * 1000)).toInt().toString()
            if (diffDays.toInt() < 10) {
                diffDays = "0$diffDays"
            }
            if (diffMinutes.toInt() < 10) {
                diffMinutes = "0$diffMinutes"
            }
            if (seconds.toInt() < 10) {
                seconds = "0$seconds"
            }
            if (diffHours.toInt() < 10) {
                diffHours = "0$diffHours"
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mapOf(
            "days" to diffDays,
            "hours" to diffHours,
            "mins" to diffMinutes,
            "secs" to seconds
        )
    }


    fun timeDelta(startTime: String): Long {
        var diff = 0L
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val currentDateAndTime = dateFormatter.format(Date())
        var d1: Date? = null
        var d2: Date? = null

        try {
            d1 = dateFormatter.parse(currentDateAndTime)
            d2 = dateFormatter.parse(LocalTimeFormat(startTime))
            diff = d2!!.time - d1!!.time

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return diff
    }

    fun changeDateToAppFormat(inputDate: String): String {
//        println("value passed===>"+inputDate)
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        df_input?.timeZone = TimeZone.getTimeZone("IST")
        val df_output = SimpleDateFormat("E dd MMM HH:mm", Locale.getDefault())
        df_output?.timeZone = TimeZone.getTimeZone("IST")
        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }

    fun LocalTimeFormat(inputDate: String): String {
//        println("value passed===>"+inputDate)
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        df_input?.timeZone = TimeZone.getTimeZone("IST")
        val df_output = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }


    fun changeDateToAppDayFormat(inputDate: String): String {
//        println("value passed===>"+inputDate)
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        df_input?.timeZone = TimeZone.getTimeZone("IST")
        val df_output = SimpleDateFormat("MMM", Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }

    fun dateTimeToAppTimeFormat(inputDate: String): String {
//        println("value passed===>"+inputDate)
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        df_input?.timeZone = TimeZone.getTimeZone("IST")
        val df_output = SimpleDateFormat("HH:mm", Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }

    fun dateTimeToAppDateFormat(inputDate: String): String {
//        println("value passed===>"+inputDate)
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        df_input?.timeZone = TimeZone.getTimeZone("IST")
        val df_output = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }


    fun changeDateToAppDateFormat(inputDate: String): String {
//        println("value passed===>"+inputDate)
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        df_input?.timeZone = TimeZone.getTimeZone("IST")
        val df_output = SimpleDateFormat("dd", Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }

    fun changeDateToAppTimeFormat(inputDate: String): String {
//        println("value passed===>"+inputDate)
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        df_input?.timeZone = TimeZone.getTimeZone("IST")
        val df_output = SimpleDateFormat("HH", Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }

    fun changeDateToAppAmPmFormat(inputDate: String): String {
//        println("value passed===>"+inputDate)
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        df_input?.timeZone = TimeZone.getTimeZone("IST")
        val df_output = SimpleDateFormat("a", Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }

    fun changeTimeToAppFormat(inputDate: String): String {
        var parsed: Date? = null
        var outputDate = ""

        val df_input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val df_output = SimpleDateFormat("HH:mm", Locale.getDefault())

        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)

        } catch (e: ParseException) {
            Log.d("Error", "ParseException - dateFormat")
        }

        return outputDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return current.format(formatter)
    }

    fun getCurrentTimeZone(): String {
        val tz = TimeZone.getDefault()
        return tz.id
    }

    fun getDeviceId(activity: Activity): String {
        return Settings.Secure.getString(
            activity.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

//    fun startNewActivity(
//        activity: Activity,
//        className: Class<*>,
//        clearStack: Boolean,
//        isAnimation: Boolean
//    ) {
//        val intent = Intent(activity, className)
//        if (clearStack)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        activity.startActivity(intent)
//        Animatoo.animateSwipeLeft(activity)
//    }
//
//    fun startNewActivity(
//        activity: Activity,
//        className: Class<*>,
//        clearStack: Boolean,
//        isAnimation: Boolean,
//        key: String, extra_value: String
//    ) {
//        val intent = Intent(activity, className)
//        if (clearStack)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//        if (extra_value != null && extra_value.length > 0) {
//            intent.putExtra(key, extra_value)
//        }
//        activity.startActivity(intent)
//        if (isAnimation)
//            Animatoo.animateSwipeLeft(activity)
//    }


    fun IntRange.random() =
        Random().nextInt((endInclusive + 1) - start) + start

//    fun startNewActivity(
//        activity: Activity,
//        className: Class<*>,
//        bundle: Bundle,
//        clearStack: Boolean,
//        isAnimation: Boolean
//    ) {
//        val intent = Intent(activity, className)
//        intent.putExtras(bundle)
//        if (clearStack)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        activity.startActivity(intent)
//        val rnds = (1 until 4).random()
//        if (isAnimation) {
////            if (rnds == 1)
////                Animatoo.animateSpin(activity)
////            if (rnds == 2)
////                Animatoo.animateFade(activity)
////            if (rnds == 3)
////                Animatoo.animateZoom(activity)
////            else
//            Animatoo.animateSwipeLeft(activity)
//        }
//    }

    fun showAlertDialogueWithOk(activity: Activity, title: String, message: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialog, id -> }.show()
    }

    fun showAlertDialogueWithOk(activity: Activity, message: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialog, id -> }.show()
    }

    fun showAlertDialogueWithOk(
        activity: Activity,
        message: String,
        callBack: AlertDialogueCallBack
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialog, id -> callBack.onSubmit() }.show()
    }


    fun showAlertDialogue(activity: Activity, message: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("Ok") { dialog, id -> }
            .show()
    }

    fun showAlertDialogue(
        activity: Activity,
        message: String,
        positiveButton: String,
        negativeButton: String
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton) { dialog, id -> }
            .setNegativeButton(negativeButton) { dialog, which -> }.show()
    }

    fun showAlertDialogue(
        activity: Activity,
        title: String,
        message: String,
        positiveButton: String,
        negativeButton: String,
        callBack: AlertDialogueCallBack
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton) { dialog, id -> callBack.onSubmit() }
            .setNegativeButton(negativeButton) { dialog, which -> callBack.onCancel() }.show()
    }


    fun showAlertDialogue(
        activity: Activity,
        title: String,
        message: String,
        positiveButton: String,
        negativeButton: String,
        neutralButton: String,
        callBack: AlertDialogueCallBack
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton) { dialog, id -> callBack.onSubmit() }
            .setNegativeButton(negativeButton) { dialog, which -> callBack.onCancel() }.show()
    }

    fun showAlertDialogue(
        activity: Activity,
        message: String,
        positiveButton: String,
        callBack: AlertDialogueCallBack
    ) {
        val builder = android.app.AlertDialog.Builder(activity)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton) { dialog, id -> callBack.onSubmit() }.show()
    }

    fun showAlertDialogue(
        activity: Activity,
        title: String,
        message: String,
        positiveButton: String,
        callBack: AlertDialogueCallBack
    ) {
        var dialog: android.app.AlertDialog? = null
        val builder = android.app.AlertDialog.Builder(activity, R.style.CustomAlertDialog)
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_alert_dialog, null)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvDetail: TextView = view.findViewById(R.id.tv_detail)
        val btDone: Button = view.findViewById(R.id.bt_done)
        tvTitle.setText(title);
        tvDetail.setText(message)
        btDone.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
            callBack.onSubmit()
        })
        builder.setView(view)
        dialog = builder.create()
        dialog.show()

    }

    fun hideKeyBoard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /*    fun computeMD5Hash(password: String): String {
            var MD5Hash: StringBuffer? = null
            try {
                // Create MD5 Hash
                val digest = MessageDigest.getInstance("MD5")
                digest.update(password.toByteArray())
                val messageDigest = digest.digest()
                MD5Hash = StringBuffer()
                for (i in messageDigest.indices) {
                    var h = Integer.toHexString(0xFF and messageDigest[i])
                    while (h.length < 2)
                        h = "0" + h
                    MD5Hash.append(h)
                }
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

            return MD5Hash.toString()
        }
    */
    interface AlertDialogueCallBack {
        fun onSubmit()

        fun onCancel()
    }

    interface GetCalenderValue {
        fun selectedDate(date: String)
    }

    interface GetLatLongValue {
        fun location(latitude: String, longitude: String)
    }

    fun alertMessageNoGps(activity: Activity) {
        val builder =
            AlertDialog.Builder(activity)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog, id -> activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    fun turnGPSOn(activity: Activity) {
        val intent = Intent("android.location.GPS_ENABLED_CHANGE")
        intent.putExtra("enabled", true)
        activity.sendBroadcast(intent)

        val provider = Settings.Secure.getString(
            activity.contentResolver,
            Settings.Secure.LOCATION_PROVIDERS_ALLOWED
        )
        if (!provider.contains("gps")) { //if gps is disabled
            val poke = Intent()
            poke.setClassName(
                "com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider"
            )
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
            poke.data = Uri.parse("3")
            activity.sendBroadcast(poke)
        }
    }

    fun turnGPSOff(activity: Activity) {
        val provider = Settings.Secure.getString(
            activity.contentResolver,
            Settings.Secure.LOCATION_PROVIDERS_ALLOWED
        )

        if (provider.contains("gps")) { //if gps is enabled
            val poke = Intent()
            poke.setClassName(
                "com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider"
            )
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
            poke.data = Uri.parse("3")
            activity.sendBroadcast(poke)
        }
    }

    //    use the following to test if the existing version of the power control widget is one which will allow you to toggle the gps.

    fun canToggleGPS(activity: Activity): Boolean {
        val pacman = activity.packageManager
        var pacInfo: PackageInfo? = null

        try {
            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS)
        } catch (e: PackageManager.NameNotFoundException) {
            return false //package not found
        }

        if (pacInfo != null) {
            for (actInfo in pacInfo.receivers) {
                //test if recevier is exported. if so, we can toggle GPS.
                if (actInfo.name == "com.android.settings.widget.SettingsAppWidgetProvider" && actInfo.exported) {
                    return true
                }
            }
        }

        return false //default
    }

//    fun isFieldEmpty(editText: FontEditText): Boolean {
//        return editText.text.toString().isEmpty()
//    }

//    fun isInValidMobileNumber(
//        etMobileNumber: FontEditText,
//        activity: Activity
//
//    ): Boolean {
//        var num = etMobileNumber.text.toString()
//        return when {
//            num.toString().isEmpty() -> {
//                showToast(activity, "Please enter your mobile number")
//                true
//            }
//            num.toString().length != 10 -> {
//                showToast(activity, "Please enter your 10 valid digit mobile number")
//                true
//            }
//            num.startsWith("0") || num.startsWith("1") || num.startsWith("2") || num.startsWith("3") || num.startsWith(
//                "4"
//            ) || num.startsWith("5") -> {
//                showToast(activity, "Please enter  valid  mobile number")
//                true
//            }
//            else -> false
//        }
//
//    }


    /*
        fun logoutForInvalidToken(activity: Activity) {
            showToast(activity, "Invalid user access")
            ObjectPreference.removeAllObject(activity)
            startNewActivity(activity, SplashActivity::class.java, true, true)
        }
    */
//    open fun showMultiplePermissionRequest(context: Context?): Unit {
//        Dexter.withActivity(context as Activity?)
//            .withPermissions(
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ).withListener(object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//                    if (report.deniedPermissionResponses.size != 0) {
//                        // show alert dialog navigating to Settings
//                        if (context != null) {
//                            showSettingsDialog(context)
//                        }
//                    }
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: List<PermissionRequest>,
//                    token: PermissionToken
//                ) {
//                    token.continuePermissionRequest()
//                }
//            }).withErrorListener {
//                Toast.makeText(context, "Error occurred! ", Toast.LENGTH_SHORT).show()
//            }
//            .onSameThread().check()
//    }

    fun showSettingsDialog(context: Context) {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Need Permissions")
        builder.setCancelable(false)
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GOTO SETTINGS"
        ) { dialog, which ->
            dialog.cancel()
            openSettings(context)
        }


        /* builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });*/builder.show()
    }

    fun openSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri =
            Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        (context as Activity).startActivityForResult(intent, 101)
    }

    private fun showAlertDialogue(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.app_name)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("Ok") { dialog, id -> }
            /*.setNegativeButton("Cancel") { dialog, which -> }*/.show()
    }

    fun getOtp(): String {
        val rnd = Random()
        val number = rnd.nextInt(9999)
        // this will convert any number sequence into 6 character.
        return String.format("%04d", number)
    }

    fun calculateNoOfColumns(
        context: Context,
        columnWidthDp: Float
    ): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

    fun showDatePicker(context: Context, tv_dob: EditText) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                var day = dayOfMonth.toString()
                var mon = monthOfYear + 1
                if (dayOfMonth.toString().length == 1) day = "0$day"
                tv_dob.setText("$year-$mon-$day")

            },
            year,
            month,
            day
        )

        dpd.show()
    }

    fun getCurrentDateTimeForChat(): String {
        val pattern = "MMM dd, HH:mm a"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(Date())
    }

    fun saveImage(context: Context, myBitmap: Bitmap): String {
        val IMAGE_DIRECTORY = "/Gromor"
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File((Environment.DIRECTORY_DCIM).toString() + IMAGE_DIRECTORY)
        val storageDir: File? =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES.toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee", storageDir.toString())
        if (!storageDir?.exists()!!) {
            storageDir?.mkdir()

            Log.d("success", "Directory created.")
        } else
            Log.d("error", "Directory cannot be created.")
        try {
            Log.d("heel", storageDir?.toString())
            val f = File(
                storageDir,
                ((Calendar.getInstance().getTimeInMillis()).toString() + ".jpg")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                context,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"),
                null
            )
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())
            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }
}

