package com.ai.tirios.ui.maintenance_bot

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.adapter.MaintenanceBotAdapter
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityMaintenanceBotBinding
import com.ai.tirios.dataclasses.Message
import com.ai.tirios.dataclasses.UploadImageBody
import com.ai.tirios.utility.CommonUtility
import com.ai.tirios.utils.Constants
import com.ai.tirios.utils.URIPathHelper
import com.ai.tirios.utils.Utilities
import com.ibm.cloud.sdk.core.http.Response
import com.ibm.cloud.sdk.core.http.ServiceCall
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.assistant.v2.Assistant
import com.ibm.watson.assistant.v2.model.*
import kotlinx.android.synthetic.main.activity_maintenance_bot.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MaintenanceBotActivity :
    BaseActivity<ActivityMaintenanceBotBinding, MaintenanceBotViewModel>(),
    MaintenanceBotMedium {
    var binding: ActivityMaintenanceBotBinding? = null
    lateinit var assistant: Assistant
    lateinit var authenticator: IamAuthenticator
    lateinit var sharedStorage: SharedStorage

    @RequiresApi(Build.VERSION_CODES.O)
    val VERSION = CommonUtility.getCurrentDate()
    val TIMEZONE = CommonUtility.getCurrentTimeZone()

    private var watsonAssistantSession: Response<SessionResponse>? = null
    private var mAdapter: MaintenanceBotAdapter? = null
    private var messageArrayList: ArrayList<Message>? = null
    private var dummyArrayList = ArrayList<Message>()
    var countryCode = "1"
    var maintenanceId = ""
    var tenantId = "93"
    var imageBitmapBase64 = ""
    lateinit var videoUri : Uri
    lateinit var imageBitMap:Bitmap
    var videoFullPath = ""
    var isMoveAhead = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.activity = this
        binding!!.maintenance = viewModel
        sharedStorage = SharedStorage(this)
        getData()
        initView()
    }

    private fun getData(){
        tenantId = intent.getStringExtra("tenantId").toString()
    }

    private fun initView() {
        messageArrayList = ArrayList()
        mAdapter = MaintenanceBotAdapter(this, messageArrayList!!)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MaintenanceBotActivity)
            adapter = mAdapter
        }
        onAttachListener()
        setUpConnection()
    }

    private fun setUpConnection() {
        showProgressbar()
        println("==============>" + TIMEZONE)
        authenticator = IamAuthenticator(Constants.SharedPref.WATSON_API_KEY)
        assistant = Assistant(VERSION, authenticator)
        assistant.setServiceUrl(Constants.SharedPref.WATSON_URL)
//        var msg = "rohit#gupta#+919999999999#false#c8933876-a00d-4971-8dcc-725ba6b4ac94#null#rohit@adtechcorp.io"
        var msg = sharedStorage.getfirstName() + "#" + TIMEZONE
//        var msg = sharedStorage.getfirstName() + "#Asia/calcutta"
        Log.v("url", msg)
        dismissProgressbar()
        sendMsg(msg)
    }

    private fun onAttachListener() {
        mAdapter?.setOnChatClickListener(object : MaintenanceBotAdapter.OnChatClick {
            override fun onImageClick(position: Int) {
            }

            override fun onVideoClick(position: Int) {

            }

            override fun onButtonOption1Click(position: Int) {
                println("========1=========" + messageArrayList?.get(position)?.btnOption1)
                if (messageArrayList?.get(position)?.btnOption1!!.contains("upload image", true))
                    imageCapture()
                else if (messageArrayList?.get(position)?.btnOption1!!.contains(
                        "record video",
                        true
                    )
                )
                    recordVideo()
                else if (messageArrayList?.get(position)?.btnOption1!!.contains(
                        "pick a date and time",
                        true
                    )
                )
                    openCalender()
                else {
                    val inputMessage = Message()
                    inputMessage.message = messageArrayList?.get(position)?.btnOption1
                    inputMessage.id = "1"
                    inputMessage.type = Message.Type.TEXT
                    messageArrayList!!.add(inputMessage)
                    sendMsg(messageArrayList?.get(position)?.btnOption1!!)
                }
            }

            override fun onButtonOption2Click(position: Int) {
                println("========2=========" + messageArrayList?.get(position)?.btnOption2)
                val inputMessage = Message()
                inputMessage.message = messageArrayList?.get(position)?.btnOption2
                inputMessage.id = "1"
                inputMessage.type = Message.Type.TEXT
                messageArrayList!!.add(inputMessage)
                sendMsg(messageArrayList?.get(position)?.btnOption2!!)
            }
        })
    }

    private fun sendMsg(msg: String) {
        onUpdateAdapter()
//        mAdapter?.notifyDataSetChanged()
        val thread = Thread(Runnable {
            try {
                if (watsonAssistantSession == null) {
                    val call: ServiceCall<SessionResponse> = assistant.createSession(
                        CreateSessionOptions.Builder()
                            .assistantId(Constants.SharedPref.WATSON_MAINTENANCE_ID).build()
                    )
                    watsonAssistantSession = call.execute()
                }
                val input = MessageInput.Builder()
                    .text(msg)
                    .build()
                val options = MessageOptions.Builder()
                    .assistantId(Constants.SharedPref.WATSON_MAINTENANCE_ID)
                    .input(input)
                    .sessionId(watsonAssistantSession?.result?.sessionId)
                    .build()
                val response: Response<MessageResponse> = assistant.message(options).execute()

                if (response != null && response.result.output != null &&
                    response.result.output.generic.isNotEmpty()
                ) {
                    val responses: List<RuntimeResponseGeneric> = response.result.output.generic
//                    println("========" + responses)
                    Log.v("responses", "" + responses)
                    for (r in responses) {
                        var outMessage: Message
                        when (r.responseType()) {
                            "text" -> {
                                outMessage = Message()
                                outMessage.message = r.text()
                                outMessage.id = "2"
                                outMessage.type = Message.Type.TEXT
                                if (outMessage?.message?.length!! > 0)
                                    dummyArrayList?.add(outMessage)
                                if (r.text().contains("thanks for scheduling", true))
                                    isMoveAhead = true
                            }
                            "option" -> {
                                outMessage = Message()
                                outMessage.type = Message.Type.OPTIONS
                                var i = 0
                                while (i < r.options().size) {

                                    val option: DialogNodeOutputOptionsElement =
                                        r.options().get(i)
                                    if (i == 0) {
                                        outMessage.btnOption1 = option.label
                                    } else
                                        outMessage.btnOption2 = option.label
                                    i++
                                }
                                outMessage.message = r.title()
                                outMessage.id = "2"
                                dummyArrayList?.add(outMessage)
                            }
                            "image" -> {
                                outMessage = Message(r)
                                outMessage.type = Message.Type.IMAGE
                                dummyArrayList?.add(outMessage)
                            }
                            else -> Log.e("Error", "Unhandled message type")
                        }
                    }
                    runOnUiThread {
//                        mAdapter?.notifyDataSetChanged()
                        onUpdateAdapter()
                        if (isMoveAhead) {
                            ll_bottom.visibility = View.GONE
                            processComplete()
                        }
                        if (mAdapter?.getItemCount()!! > 1) {
                            recyclerView?.layoutManager?.smoothScrollToPosition(
                                recyclerView,
                                null,
                                mAdapter!!.getItemCount() - 1
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        thread.start()
    }

    private fun imageCapture() {
        viewModel.dispatchTakePictureIntent()
    }

    private fun recordVideo() {
        viewModel.dispatchTakeVideoIntent()
    }

    private fun openCalender() {
        CommonUtility.showDateTimePicker(this, object:CommonUtility.GetCalenderValue{
            override fun selectedDate(date: String) {
//                println("========>"+date)
                val list = date.split("#")
//                println("========>"+list[1])
                val inputMessage = Message()
                inputMessage.url = list[0]
                inputMessage.message = list[1]
                inputMessage.id = "1"
                inputMessage.type = Message.Type.TIME
                messageArrayList!!.add(inputMessage)
                sendMsg(list[1])
            }
        })
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_maintenance_bot
    override lateinit var viewModel: MaintenanceBotViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun onAttachFile() {
    }

    override fun onSendMsg() {
        if (!TextUtils.isEmpty(et_msg.text.toString())) {
            CommonUtility.hideKeyBoard(this)
            val inputMessage = Message()
            inputMessage.message = et_msg.text.toString()
            inputMessage.id = "1"
            inputMessage.type = Message.Type.TEXT
            messageArrayList!!.add(inputMessage)
            sendMsg(et_msg.text.toString())
            et_msg.text?.clear()
        }else
            Toast.makeText(this, "Message can't be blank", Toast.LENGTH_SHORT).show()
    }

    override fun onMaintenanceIdUpdate(mId: Int, dataMsg: String) {
        maintenanceId = mId.toString()
        var outMessage = Message()
        outMessage.message = dataMsg
        outMessage.url = imageBitmapBase64
        outMessage.imageBitMap = imageBitMap
        outMessage.id = "1"
        outMessage.type = Message.Type.IMAGE
        messageArrayList?.add(outMessage)
        sendMsg(dataMsg)
    }

    override fun onMaintenanceUpdate(dataMsg: String) {
        var outMessage = Message()
        outMessage.message = dataMsg
        outMessage.url = imageBitmapBase64
        outMessage.uri = videoUri
        outMessage.id = "1"
        outMessage.type = Message.Type.VIDEO
        messageArrayList?.add(outMessage)
        sendMsg(dataMsg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, datas: Intent?) {
        super.onActivityResult(requestCode, resultCode, datas)
        if (requestCode == Constants.SharedPref.REQUEST_IMAGE_CAPTURE && datas != null) {
            imageBitMap = datas!!.extras!!.get("data") as Bitmap
            imageBitmapBase64 = Utilities.bitmapToBase64(imageBitMap)!!
            uploadImageCapture(imageBitmapBase64)
        } else if (requestCode == Constants.SharedPref.REQUEST_VIDEO_CAPTURE && datas != null){
            videoFullPath = URIPathHelper().getPath(this, datas!!.data!!)!!
            videoUri = datas!!.data!!
//            videoView.setVideoURI(videoUri)
            uploadVideoCapture(videoFullPath)
        }
    }

    private fun uploadImageCapture(base64: String) {
        var imageName = "maintenance_bot_" + System.currentTimeMillis()
//        println("=============="+imageName)
//        println("=============="+base64)
//        println("=============="+tenantId!!.toInt())
        if (maintenanceId.isNullOrEmpty())
            viewModel.uploadImage(
                this, Constants.SharedPref.PROPERTY_BASE_URL + "maintenance",
                UploadImageBody(
                    base64, "jpg", imageName, 1,
                    tenantId!!.toInt()
                )
            )
        else
            viewModel.uploadImageWithId(
                this, Constants.SharedPref.PROPERTY_BASE_URL + "maintenance/"
                        + maintenanceId + "/upload/image", UploadImageBody(
                    base64, "jpg", imageName, 1,
                    tenantId!!.toInt()
                )
            )
    }

    private fun uploadVideoCapture(path: String) {
        var imageName = "maintenance_bot_video" + System.currentTimeMillis()
        viewModel.uploadVideo(this, Constants.SharedPref.PROPERTY_BASE_URL + "maintenance/" + maintenanceId + "/upload/video",
            maintenanceId.toInt(), path, imageName,2,"mp4")
    }

    fun onUpdateAdapter() {
//        if (messageArrayList?.size!! < 3) {
//            for (item in dummyArrayList?.indices!!) {
//                messageArrayList?.add(dummyArrayList?.get(item)!!)
//                Thread.sleep(2000)
//                mAdapter?.notifyDataSetChanged()
//            }
//        } else
            for (item in dummyArrayList?.indices!!) {
                messageArrayList?.add(dummyArrayList?.get(item)!!)
                mAdapter?.notifyDataSetChanged()
            }

        dummyArrayList?.clear()
//        mAdapter?.notifyDataSetChanged()
    }

    private fun processComplete() {
        CommonUtility.showAlertDialogue(this, "Thank You","Thanks for scheduling your service Request. Please send email to members@tirios.ai in case you need to cancel or reschedule your appointment.", "OK", object: CommonUtility.AlertDialogueCallBack{
            override fun onSubmit() {
                onBackPressed()
            }

            override fun onCancel() {
            }
        })
    }
}
