package com.ai.tirios.ui.add_tenant_bot

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
import com.ai.tirios.adapter.AddTenantBotAdapter
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityAddTenantBotBinding
import com.ai.tirios.dataclasses.BodyTenantUpload
import com.ai.tirios.dataclasses.Message
import com.ai.tirios.utility.CommonUtility
import com.ai.tirios.utils.Constants
import com.ai.tirios.utils.Utilities
import com.ibm.cloud.sdk.core.http.Response
import com.ibm.cloud.sdk.core.http.ServiceCall
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.assistant.v2.Assistant
import com.ibm.watson.assistant.v2.model.*
import kotlinx.android.synthetic.main.activity_maintenance_bot.*
import java.lang.Double.parseDouble
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class AddTenantBotActivity :
    BaseActivity<ActivityAddTenantBotBinding, AddTenantBotViewModel>(),
    AddTenantBotMedium {
    var binding: ActivityAddTenantBotBinding? = null
    lateinit var assistant: Assistant
    lateinit var authenticator: IamAuthenticator
    lateinit var sharedStorage: SharedStorage

    @RequiresApi(Build.VERSION_CODES.O)
    val VERSION = CommonUtility.getCurrentDate()
    val TIMEZONE = CommonUtility.getCurrentTimeZone()

    private var watsonAssistantSession: Response<SessionResponse>? = null
    private var mAdapter: AddTenantBotAdapter? = null
    private var messageArrayList: ArrayList<Message>? = null
    var maintenanceId = ""
    var propertyId = 0
    var tenantId = ""
    var shortLink = ""
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
        binding!!.addTenant = viewModel
        sharedStorage = SharedStorage(this)
        getData()
        initView()
    }

    private fun getData(){
        propertyId = intent.getIntExtra("property_id", 0)
    }

    private fun initView() {
        messageArrayList = ArrayList()
        mAdapter = AddTenantBotAdapter(this, messageArrayList!!)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@AddTenantBotActivity)
            adapter = mAdapter
        }
        onAttachListener()
        setUpConnection()
    }

    private fun setUpConnection() {
        showProgressbar()
//        println("==============>" + TIMEZONE)
        authenticator = IamAuthenticator(Constants.SharedPref.WATSON_API_KEY)
        assistant = Assistant(VERSION, authenticator)
        assistant.setServiceUrl(Constants.SharedPref.WATSON_URL)
//        var msg = "rohit#gupta#+919999999999#false#c8933876-a00d-4971-8dcc-725ba6b4ac94#null#rohit@adtechcorp.io"
        var msg = sharedStorage.getfirstName()?.trim() + "#" + sharedStorage.getid()?.trim() + "#" + propertyId
//        var msg = sharedStorage.getfirstName() + "#Asia/calcutta"
        Log.v("url", msg)
        sendMsg(msg)
    }

    private fun onAttachListener() {
        mAdapter?.setOnChatClickListener(object : AddTenantBotAdapter.OnChatClick {
            override fun onImageClick(position: Int) {
            }

            override fun onButtonOption1Click(position: Int) {
                println("========1=========" + messageArrayList?.get(position)?.btnOption1)
                if (messageArrayList?.get(position)?.btnOption1!!.contains("upload", true))
                    imageCapture()
                else if (messageArrayList?.get(position)?.btnOption1!!.contains(
                        "pick your date",
                        true
                    )
                ) {
                    if (!messageArrayList?.get(position)?.isReplied!!)
                        openCalender()
                    else
                        Toast.makeText(this@AddTenantBotActivity, "Date is already picked", Toast.LENGTH_SHORT).show()
                }
                else if (messageArrayList?.get(position)?.btnOption1!!.contains(
                        "exit",
                        true
                    )
                )
                    processComplete()
                else if (messageArrayList?.get(position)?.btnOption1!!.contains(
                        "send invite",
                        true
                    )
                )
                    shareLink(shortLink)
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
                if (messageArrayList?.get(position)?.btnOption2!!.contains(
                        "send invite",
                        true
                    )
                )
                    shareLink(shortLink)
                else {
                    val inputMessage = Message()
                    inputMessage.message = messageArrayList?.get(position)?.btnOption2
                    inputMessage.id = "1"
                    inputMessage.type = Message.Type.TEXT
                    messageArrayList!!.add(inputMessage)
                    sendMsg(messageArrayList?.get(position)?.btnOption2!!)
                }
            }

            override fun onButtonOption3Click(position: Int) {
                println("========2=========" + messageArrayList?.get(position)?.btnOption3)
                val inputMessage = Message()
                inputMessage.message = messageArrayList?.get(position)?.btnOption3
                inputMessage.id = "1"
                inputMessage.type = Message.Type.TEXT
                messageArrayList!!.add(inputMessage)
                sendMsg(messageArrayList?.get(position)?.btnOption3!!)
            }

            override fun onButtonOption4Click(position: Int) {
                println("========2=========" + messageArrayList?.get(position)?.btnOption4)
                val inputMessage = Message()
                inputMessage.message = messageArrayList?.get(position)?.btnOption4
                inputMessage.id = "1"
                inputMessage.type = Message.Type.TEXT
                messageArrayList!!.add(inputMessage)
                sendMsg(messageArrayList?.get(position)?.btnOption4!!)
            }

            override fun onButtonOption5Click(position: Int) {
                println("========2=========" + messageArrayList?.get(position)?.btnOption5)
                val inputMessage = Message()
                inputMessage.message = messageArrayList?.get(position)?.btnOption5
                inputMessage.id = "1"
                inputMessage.type = Message.Type.TEXT
                messageArrayList!!.add(inputMessage)
                sendMsg("5")
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
                            .assistantId(Constants.SharedPref.WATSON_ADD_TENANT_ID).build()
                    )
                    watsonAssistantSession = call.execute()
                }
                val input = MessageInput.Builder()
                    .text(msg)
                    .build()
                val options = MessageOptions.Builder()
                    .assistantId(Constants.SharedPref.WATSON_ADD_TENANT_ID)
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
                                    messageArrayList?.add(outMessage)
                                if (r.text().contains("thanks for scheduling", true))
                                    isMoveAhead = true
                            }
                            "option" -> {
                                outMessage = Message()
                                var isButton  = false
                                var i = 0
                                while (i < r.options().size) {
                                    val option: DialogNodeOutputOptionsElement =
                                        r.options().get(i)
                                    if (i == 0) {
                                        outMessage.btnOption1 = option.label
                                    } else if (i==1)
                                        outMessage.btnOption2 = option.label
                                    else if (i==2) {
                                        outMessage.btnOption3 = option.label
                                        isButton = true
                                    }
                                    else if (i==3)
                                        outMessage.btnOption4 = option.label
                                    else if (i==4)
                                        outMessage.btnOption5 = option.label
                                    i++
                                }
                                if(isButton)
                                    outMessage.type = Message.Type.BUTTON
                                else
                                    outMessage.type = Message.Type.OPTIONS

                                outMessage.message = r.title()
                                if (r.description()!=null) {
                                    if (r.description().contains(sharedStorage.getid().toString())) {
                                        var data = r.description().split("#")
                                        tenantId = data[1]
                                    } else if (r.description().contains("Link#")) {
                                        var data = r.description().split("#")
                                        shortLink = data[1]
                                    }
                                }
                                outMessage.id = "2"
                                messageArrayList?.add(outMessage)
                            }
                            "image" -> {
                                outMessage = Message(r)
                                outMessage.type = Message.Type.IMAGE
                                messageArrayList?.add(outMessage)
                            }
                            else -> Log.e("Error", "Unhandled message type")
                        }
                    }
                    dismissProgressbar()
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

    private fun openCalender() {
        CommonUtility.showDatePicker(this, object:CommonUtility.GetCalenderValue{
            override fun selectedDate(date: String) {
//                println("========>"+date)
                val list = date.split("#")
//                println("========>"+list[1])
                val inputMessage = Message()
                inputMessage.url = list[0]
                inputMessage.message = list[1]
                inputMessage.id = "1"
                inputMessage.type = Message.Type.TIME
                messageArrayList?.last()?.isReplied = true
                messageArrayList!!.add(inputMessage)
                sendMsg(list[1])
            }
        })
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override val layoutId: Int
        get() = R.layout.activity_add_tenant_bot
    override lateinit var viewModel: AddTenantBotViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun onAttachFile() {
    }

    override fun onSendMsg() {
        if(!TextUtils.isEmpty(et_msg.text.toString())){
            CommonUtility.hideKeyBoard(this)
            if (messageArrayList?.last()?.message?.contains("rent?") == true) {
                var numeric = true
                try {
                    val num = parseDouble(et_msg.text.toString().trim())
                } catch (e: NumberFormatException) {
                    numeric = false
                }
                if(numeric){
                    val inputMessage = Message()
                    inputMessage.message = "$"+et_msg.text.toString().trim()
                    inputMessage.id = "1"
                    inputMessage.type = Message.Type.TEXT
                    messageArrayList!!.add(inputMessage)
                    sendMsg(et_msg.text.toString())
                    et_msg.text?.clear()
                }else
                    Toast.makeText(this, "Rent amount need to be in numerics", Toast.LENGTH_SHORT).show()
            }else{
                val inputMessage = Message()
                inputMessage.message = et_msg.text.toString()
                inputMessage.id = "1"
                inputMessage.type = Message.Type.TEXT
                messageArrayList!!.add(inputMessage)
                sendMsg(et_msg.text.toString())
                et_msg.text?.clear()
            }
        }else
            Toast.makeText(this, "Message can't be blank", Toast.LENGTH_SHORT).show()
    }

    override fun onAddTenantImageUpload(dataMsg: String, url : String) {
        var outMessage = Message()
        outMessage.message = dataMsg
        outMessage.url = imageBitmapBase64
        outMessage.imageBitMap = imageBitMap
        outMessage.uri = Uri.parse(url)
        outMessage.id = "1"
        outMessage.type = Message.Type.IMAGE
        messageArrayList?.add(outMessage)
        sendMsg(dataMsg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, datas: Intent?) {
        super.onActivityResult(requestCode, resultCode, datas)
        if (requestCode == Constants.SharedPref.REQUEST_IMAGE_CAPTURE) {
            imageBitMap = datas!!.extras!!.get("data") as Bitmap
            imageBitmapBase64 = Utilities.bitmapToBase64(imageBitMap)!!
            uploadImageCapture(imageBitmapBase64)
        }
    }

    private fun uploadImageCapture(base64: String) {
        var imageName = "LeaseAgreement_" + System.currentTimeMillis()

        if (maintenanceId.isNullOrEmpty())
           viewModel.uploadTenantImage(tenantId, BodyTenantUpload(
                imageBitmapBase64,3,imageName,"jpg",
                System.currentTimeMillis().toString(), tenantId.toInt())
            )
    }


    fun onUpdateAdapter(){
//        if (messageArrayList?.size==2 || messageArrayList?.size==3) {
//            dummymessageArrayList = messageArrayList as ArrayList<Message>
//            messageArrayList?.clear()
//            for(item in dummymessageArrayList.indices){
//                messageArrayList?.add(dummymessageArrayList[item])
//                if (item == 2 || item == 3)
//                    Handler().postDelayed(Runnable {
//                        mAdapter?.notifyDataSetChanged()
//                    }, 2000)
//                else
//                    mAdapter?.notifyDataSetChanged()
//            }
//        }else
            mAdapter?.notifyDataSetChanged()
    }

    private fun processComplete() {
        CommonUtility.showAlertDialogue(this, resources.getString(R.string.thank_you),"Tenant added successfully.", "OK", object: CommonUtility.AlertDialogueCallBack{
            override fun onSubmit() {
                onBackPressed()
            }

            override fun onCancel() {
            }
        })
    }

    private fun shareLink(link: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
            shareIntent.putExtra(Intent.EXTRA_TEXT, link)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }
}
