package com.ai.tirios.ui.signupchatbot

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.adapter.ChatAdapter
import com.ai.tirios.databinding.ActivitySignUpChatBotBinding
import com.ai.tirios.dataclasses.Message
import com.ai.tirios.ui.navigation.NavigationActivity
import com.ai.tirios.utility.CommonUtility
import com.ai.tirios.utils.Constants.SharedPref.Companion.WATSON_API_KEY
import com.ai.tirios.utils.Constants.SharedPref.Companion.WATSON_ID
import com.ai.tirios.utils.Constants.SharedPref.Companion.WATSON_IMAGE
import com.ai.tirios.utils.Constants.SharedPref.Companion.WATSON_URL
import com.ibm.cloud.sdk.core.http.Response
import com.ibm.cloud.sdk.core.http.ServiceCall
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.assistant.v2.Assistant
import com.ibm.watson.assistant.v2.model.*
import com.ai.tirios.base.BaseActivity
import com.withpersona.sdk.inquiry.Environment
import com.withpersona.sdk.inquiry.Inquiry
import kotlinx.android.synthetic.main.activity_maintenance_bot.*
import kotlinx.android.synthetic.main.activity_sign_up_chat_bot.*
import kotlinx.android.synthetic.main.activity_sign_up_chat_bot.et_msg
import kotlinx.android.synthetic.main.activity_sign_up_chat_bot.ll_bottom
import kotlinx.android.synthetic.main.activity_sign_up_chat_bot.recyclerView
import java.lang.Double
import javax.inject.Inject


class SignUpChatBotActivity : BaseActivity<ActivitySignUpChatBotBinding, SignUpChatBotViewModel>(),
    SignUpChatBotMedium {
    var binding: ActivitySignUpChatBotBinding? = null
    lateinit var assistant: Assistant
    lateinit var authenticator: IamAuthenticator

    @RequiresApi(Build.VERSION_CODES.O)
    val VERSION = CommonUtility.getCurrentDate()
    private var watsonAssistantSession: Response<SessionResponse>? = null
    private var mAdapter: ChatAdapter? = null
    private var dummyArrayList = ArrayList<Message>()
    private var messageArrayList: ArrayList<Message>? = null
    var fName: String? = ""
    var lName: String? = ""
    var userId: String? = ""
    var mobileNo: String? = ""
    var tenantId: String? = ""
    var email: String? = "null"
    var activityFrom = ""
    val VERIFY_REQUEST_CODE = 43
    val TEMPLATE_ID = "tmpl_NDGPz4fpQvB9iN87JQRzgG4T"
    var isMoveAhead = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = dataBinding
        viewModel.medium = this
        binding!!.activity = this
        binding!!.signupchatbot = viewModel
        initView()
        getData()
    }

    private fun initView() {
        messageArrayList = ArrayList()
        mAdapter = ChatAdapter(this, messageArrayList!!)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@SignUpChatBotActivity)
            adapter = mAdapter
        }
        onAttachListener()
    }

    private fun getData() {
        fName = intent.getStringExtra("FIRST_NAME")?.trim()
        lName = intent.getStringExtra("LAST_NAME")?.trim()
        userId = intent.getStringExtra("USER_ID")
        mobileNo = intent.getStringExtra("MOBILE")
        if(intent.hasExtra("TENANT_ID")){
            email = intent.getStringExtra("EMAIL")
            tenantId = intent.getStringExtra("TENANT_ID")
        }
        setUpConnection()
    }

    private fun setUpConnection() {
        authenticator = IamAuthenticator(WATSON_API_KEY)
        assistant = Assistant(VERSION, authenticator)
        assistant.setServiceUrl(WATSON_URL)
//        var msg = "rohit#gupta#+919999999999#false#c8933876-a00d-4971-8dcc-725ba6b4ac94#null#rohit@adtechcorp.io"
        var msg = ""
        if(intent.hasExtra("TENANT_ID"))
            msg = fName+"#"+lName+"#"+mobileNo+"#true#"+userId+"#"+tenantId+"#"+email
        else
            msg = fName+"#"+lName+"#"+mobileNo+"#false#"+userId+"#null#"+email
        Log.v("url", msg)
        sendMsg(msg)
    }

    private fun onAttachListener() {
        mAdapter?.setOnChatClickListener(object : ChatAdapter.OnChatClick {
            override fun onImageClick(position: Int) {
            }

            override fun onVideoClick(position: Int) {
            }

            override fun onButtonOption1Click(position: Int) {
                println("========1=========" + messageArrayList?.get(position)?.btnOption1)
                if (messageArrayList?.get(position)?.btnOption1!!.contains("verify", true))
                    openWithPersona()
                else if (messageArrayList?.get(position)?.btnOption1!!.contains("move ahead", true))
                    verificationComplete()
                else {
                    val inputMessage = Message()
                    inputMessage.message = messageArrayList?.get(position)?.btnOption1
                    inputMessage.id = "1"
                    messageArrayList!!.add(inputMessage)
                    sendMsg(messageArrayList?.get(position)?.btnOption1!!)
                }
            }

            override fun onButtonOption2Click(position: Int) {
                println("========2=========" + messageArrayList?.get(position)?.btnOption2)
                if (messageArrayList?.get(position)?.btnOption2!!.contains("verify", true))
                    openWithPersona()
                else if (messageArrayList?.get(position)?.btnOption2!!.contains("move ahead", true))
                    verificationComplete()
                else {
                    val inputMessage = Message()
                    inputMessage.message = messageArrayList?.get(position)?.btnOption2
                    inputMessage.id = "1"
                    messageArrayList!!.add(inputMessage)
                    sendMsg(messageArrayList?.get(position)?.btnOption2!!)
                }
            }
        })
    }

    private fun verificationComplete() {
        CommonUtility.showAlertDialogue(this, resources.getString(R.string.thank_you),resources.getString(R.string.onboarding_success), "OK", object: CommonUtility.AlertDialogueCallBack{
            override fun onSubmit() {
                navigateTo(
                    NavigationActivity::class.java, true, Bundle(),
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                )
            }

            override fun onCancel() {
            }
        })
    }

    private fun openWithPersona() {
        Inquiry.fromTemplate(TEMPLATE_ID)
            .environment(Environment.PRODUCTION)
            .build()
            .start(this, VERIFY_REQUEST_CODE)
    }

    override fun onClick(p0: View?) {

    }

    override val layoutId: Int
        get() = R.layout.activity_sign_up_chat_bot
    override lateinit var viewModel: SignUpChatBotViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    override fun onBackPressed() {
        //super.onBackPressed()
        navigateTo(
            NavigationActivity::class.java, false, Bundle(),
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onAttachFile() {
    }

    override fun onSendMsg() {
        if (!TextUtils.isEmpty(et_msg.text.toString())) {
            println("========>" + messageArrayList?.size)
            CommonUtility.hideKeyBoard(this)
            if (messageArrayList?.last()?.message?.contains("zip code") == true) {
                var numeric = true
                try {
                    val num = Double.parseDouble(et_msg.text.toString().trim())
                } catch (e: NumberFormatException) {
                    numeric = false
                }
                if(numeric && et_msg.text.toString().trim().length==5){
                    val inputMessage = Message()
                    inputMessage.message = et_msg.text.toString().trim()
                    inputMessage.id = "1"
                    inputMessage.type = Message.Type.TEXT
                    messageArrayList!!.add(inputMessage)
                    sendMsg(et_msg.text.toString())
                    et_msg.text?.clear()
                }else
                    Toast.makeText(this, "Zip code need to be numerics and of 5-digits", Toast.LENGTH_SHORT).show()

            }else {
                val inputMessage = Message()
                inputMessage.message = et_msg.text.toString()
                inputMessage.id = "1"
                messageArrayList!!.add(inputMessage)
                sendMsg(et_msg.text.toString())
                et_msg.text?.clear()
            }
        }else
            Toast.makeText(this, "Message can't be blank", Toast.LENGTH_SHORT).show()
    }

    private fun sendMsg(msg: String) {
//        mAdapter?.notifyDataSetChanged()//need this for future purpose
        onUpdateAdapter()
        val thread = Thread(Runnable {
            try {
                if (watsonAssistantSession == null) {
                    val call: ServiceCall<SessionResponse> = assistant.createSession(
                        CreateSessionOptions.Builder()
                            .assistantId(WATSON_ID).build()
                    )
                    watsonAssistantSession = call.execute()
                }
                val input = MessageInput.Builder()
                    .text(msg)
                    .build()
                val options = MessageOptions.Builder()
                    .assistantId(WATSON_ID)
                    .input(input)
                    .sessionId(watsonAssistantSession?.result?.sessionId)
                    .build()
                val response: Response<MessageResponse> = assistant.message(options).execute()

                if (response != null && response.result.output != null &&
                    response.result.output.generic.isNotEmpty()
                ) {
                    val responses: List<RuntimeResponseGeneric> = response.result.output.generic
                    println("========" + responses)
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
                                        if (option.label.contains("move ahead", true))
                                            isMoveAhead = true
                                    }else
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
                        if(isMoveAhead)
                            ll_bottom.visibility = View.GONE
//                        mAdapter?.notifyDataSetChanged() //need this for future purpose
                        onUpdateAdapter()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VERIFY_REQUEST_CODE) {
            when (val result = Inquiry.onActivityResult(data)) {
                is Inquiry.Response.Success -> {
                    // ...
                    var msg = WATSON_IMAGE+"#pass"
                    sendMsg(msg)
                }
                is Inquiry.Response.Failure -> {
                    // ...
                    var msg = WATSON_IMAGE+"#fail"
                    sendMsg(msg)
                }
                Inquiry.Response.Cancel -> {
                    // ...
                }
                is Inquiry.Response.Error -> {
                    // ...
                }
            }
        }

    }

    fun onUpdateAdapter() {
//        if (messageArrayList?.size!! < 3) {
//            for (item in dummyArrayList?.indices!!) {
//                Thread.sleep(2000)
//                messageArrayList?.add(dummyArrayList?.get(item)!!)
//                mAdapter?.notifyDataSetChanged()
//            }
//        } else
        for (item in dummyArrayList?.indices!!) {
            messageArrayList?.add(dummyArrayList?.get(item)!!)
            mAdapter?.notifyDataSetChanged()
        }

        dummyArrayList?.clear()
    }

}
