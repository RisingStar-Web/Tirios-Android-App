package com.ai.tirios.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Parcelable
import android.provider.Settings
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ai.tirios.BuildConfig
import java.io.ByteArrayOutputStream
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class Utilities(private val mContext: Context) {

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    fun showNetworkSettings() {
        val chooserIntent = Intent.createChooser(
            getSettingsIntent(Settings.ACTION_DATA_ROAMING_SETTINGS),
            "Complete action using"
        )
        val networkIntents = ArrayList<Intent>()
        networkIntents.add(getSettingsIntent(Settings.ACTION_WIFI_SETTINGS))
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            networkIntents.toTypedArray<Parcelable>()
        )
        startActivityBySettings(mContext, chooserIntent)
    }

    private fun getSettingsIntent(settings: String) = Intent(settings)

    private fun startActivityBySettings(context: Context) =
        context.startActivity(getSettingsIntent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

    private fun startActivityBySettings(context: Context, intent: Intent) =
        context.startActivity(intent)

    fun getString(resId: Int): String? = mContext.resources.getString(resId)

    fun getColor(colorId: Int) = ContextCompat.getColor(mContext, colorId)

    fun showToast(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun isNameFormat(name: String?) =
        (name.isNullOrBlank() || name.length > 25).not()

    fun isValidMobile(phone: String?) =
        (phone.isNullOrBlank() || phone.length != 10).not() && Patterns.PHONE.matcher(
            phone
        ).matches()

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun isNull(`object`: Any?) =
        null == `object` || `object`.toString().compareTo("null") == 0

    fun isValidEmail(target: CharSequence?) =
        target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()

    companion object {

        fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches().not()

        @SuppressLint("SimpleDateFormat")
        fun stringToDate(date: String): String? {
            val givenFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val targetFormat = SimpleDateFormat("dd-MM-yyyy")
            val givenDate: Date
            var targetDate: String = date
            try {
                givenDate = givenFormat.parse(date.removeSuffix("Z"))
                targetDate = targetFormat.format(givenDate)
            } catch (e: ParseException) {
            }
            return targetDate
        }

        fun getNewNumberFormat(d: Double): String {
            val text = java.lang.Double.toString(d)
            val integerPlaces = text.indexOf('.')
            val decimalPlaces = text.length - integerPlaces - 1
            when {
                decimalPlaces == 2 -> return text
                decimalPlaces == 1 -> return text + "0"
                decimalPlaces == 0 -> return "$text.00"
                decimalPlaces > 2 -> {
                    val converted = (Math.round(d * 100).toDouble() / 100).toString()
                    val convertedIntegers = converted.indexOf('.')
                    val convertedDecimals = converted.length - convertedIntegers - 1
                    return when (convertedDecimals) {
                        2 -> converted
                        1 -> converted + "0"
                        0 -> "$converted.00"
                        else -> converted
                    }
                }
                else -> return text
            }
        }

        fun getSFProBold(context: Context): Typeface {
            return Typeface.createFromAsset(context.resources.assets, "SFProBold.ttf")
        }

        fun getSFProMedium(context: Context): Typeface {
            return Typeface.createFromAsset(context.resources.assets, "SFProMedium.ttf")
        }

        fun getSFProRegular(context: Context): Typeface {
            return Typeface.createFromAsset(context.resources.assets, "SFProRegular.ttf")
        }

        fun getSFProSemibold(context: Context): Typeface {
            return Typeface.createFromAsset(context.resources.assets, "SFProSemibold.ttf")
        }

        fun convertToUtc(date_time: String): String {
            var spf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            val newDate = spf.parse(date_time)
            spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            return spf.format(newDate)
        }

        fun convertToUtc1(date_time: String): Date {
            var spf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            val newDate = spf.parse(date_time)
            spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            return spf.parse(spf.format(newDate))
        }

        fun convertUtcToNormal(date_time: String): String {
            var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            val newDate = spf.parse(date_time)
            spf = SimpleDateFormat("MM/dd/yyyy")
            return spf.format(newDate)
        }

        fun convertUtcToDateWithMonthName(date_time: String): String {
            var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            val newDate = spf.parse(date_time)
            spf = SimpleDateFormat("MMM dd, hh:mm a")
            return spf.format(newDate)
        }

        fun convertUtcToDateWithMonthNameAndYear(date_time: String): String {
            var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            val newDate = spf.parse(date_time)
            spf = SimpleDateFormat("MMM dd, yyyy")
            return spf.format(newDate)
        }

        fun convertUtcToDateNotification(date_time: String): String {
            var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            val newDate = spf.parse(date_time)
            spf = SimpleDateFormat("MMM dd, hh:mm a")
            return spf.format(newDate)
        }

        fun convertUtcToNormalTenant(date_time: String): String {
            var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            val newDate = spf.parse(date_time)
            spf = SimpleDateFormat("MM/dd/yyyy")
            return spf.format(newDate)
        }

        fun currentTimeUtc(): String {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
            return dateFormat.format(System.currentTimeMillis())
        }

        fun getLocalDateFromUtc(date_time: String): String{
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.ENGLISH)
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date = df.parse(date_time)
            df.timeZone = TimeZone.getDefault()
            return convertUtcToDateWithMonthName(df.format(date))
        }

        fun getLocalIpAddress(): String? {
            try {
                val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf: NetworkInterface = en.nextElement()
                    val enumIpAddr: Enumeration<InetAddress> = intf.getInetAddresses()
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress: InetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress() && inetAddress is Inet4Address) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            } catch (ex: SocketException) {
                ex.printStackTrace()
            }
            return null
        }

        fun bitmapToBase64(bitmap: Bitmap): String? {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
            val imageBytes = byteArrayOutputStream.toByteArray()
            var imageString = encodeToString(imageBytes, Base64.NO_WRAP)
            return imageString
        }

        fun indianPriceFormat(amount: Double): String? {
            return DecimalFormat("#,###,###.00").format(amount.toLong())
        }
        fun isProduction(): Boolean {
            return (BuildConfig.BUILD_TYPE == "release" && !BuildConfig.DEBUG)
        }

        fun payingPriceFormat(amount: Int) : String? {
            return DecimalFormat("#,###,###.##").format(amount.toDouble()/100)

        }

        fun convertValidPhoneNumber(str: String) : String {
            return str.filter { it.isDigit() }
        }
    }
}
