package com.ai.tirios.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.ai.tirios.BuildConfig
import com.ai.tirios.utility.CommonUtility
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by Maruthi Ram Yadav on 10/9/2020.
 */
interface Constants{
    interface InternalHttpCode{
        companion object{
            const val SUCCESS_CODE = 200
            const val UNAUTHORIZED_ACCESS = 401
            const val FORBIDDEN = 403
            const val METHOD_NOT_ALLOWED = 405
            const val TIMEOUT_ERROR = 408
            const val TEAPOT = 418
            const val INTERNAL_SERVER_ERROR = 500
            const val SERVICE_UNAVAILABLE = 503
            const val TOO_MANY_REQUESTS = 429
            const val LOCKED = 423
            const val VIDEO_UPLOAD_SIZE_FAIL = 413
        }
    }
    interface SharedPref {
        companion object {
            const val BASE_URL = "http://ec2-3-229-26-172.compute-1.amazonaws.com/"
//            const val IDENTITY_BASE_URL = "http://ec2-3-229-26-172.compute-1.amazonaws.com/"
            const val IDENTITY_BASE_URL = BuildConfig.IDENTITY_BASE_URL
            const val PROPERTY_BASE_URL = BuildConfig.PROPERTY_BASE_URL
            const val SERVICE_BASE_URL = "http://ec2-3-229-26-172.compute-1.amazonaws.com/"
            //const val NOTIFICATION_BASE_URL = "http://ec2-3-229-26-172.compute-1.amazonaws.com/"
            const val IDENTITY_SWAGGER_BASE_URL = BuildConfig.IDENTITY_SWAGGER_BASE_URL
            const val NOTIFICATION_BASE_URL = BuildConfig.NOTIFICATION_BASE_URL
           // const val NOTIFICATION_BASE_URL = "http://api-dev-notification.tirios.ai/"

            const val JSON_TYPE = "application/json"
            const val SHARED_PREF = "SHARED_PREF_TIRIOS"
            const val IS_LOGIN = "IS_LOGIN"
            const val TOKEN = "token"
            const val ACCESS_TOKEN = "access_token"
            const val ID = "id"
            const val FIRST_NAME = "firstName"
            const val LAST_NAME = "lastName"
            const val USER_NAME = "username"
            const val ROLE = "role"
            const val EMAIL = "email"
            const val STATUS = "status"
            const val EMAIL_CONFIRMED = "emailConfirmed"
            const val DOCUMENTS_UPLOADED= "documentsUploaded"
            const val TENANT_ID = "tenant_id"
            const val WATSON_IMAGE = "f12aa5fd-71bc-4cc2-887c-b00351723c17"
            const val WATSON_VIDEO = "98df5f3a-d2b7-4f3e-a84b-53fb2e635fce"
            const val ISLANDLORD = "is_landlord"
            val REQUEST_IMAGE_CAPTURE = 1
            val CAMERA_REQUEST_CODE = 2
            val GALLERY_IMAGE_CAPTURE = 3
            val REQUEST_VIDEO_CAPTURE = 4
            val GALLERY_VIDEO_CAPTURE = 5
            //watson chatbot development

            const val WATSON_ID= BuildConfig.WATSON_ID
            const val WATSON_MAINTENANCE_ID= BuildConfig.WATSON_MAINTENANCE_ID
            const val WATSON_ADD_TENANT_ID= BuildConfig.WATSON_ADD_TENANT_ID
            const val WATSON_API_KEY= "LahBoN55jW2rVjXjBb0uXxCTOFbP7dtTENoI-o3sk7rN"
            const val WATSON_URL= "https://api.us-south.assistant.watson.cloud.ibm.com/instances/97d15aa1-c047-4969-a82b-6f254b2f5cf5"

            const val NOTIFICATION_LIST_URL = "api/notification/"
            //watson chatbot production
//            const val VERSION= "2021-05-18"
//            const val WATSON_ID= "aac72cb8-5e9f-4b98-b661-e37443d79652"
//            const val WATSON_API_KEY= "LahBoN55jW2rVjXjBb0uXxCTOFbP7dtTENoI-o3sk7rN"
//            const val WATSON_URL= "https://api.us-south.assistant.watson.cloud.ibm.com/instances/97d15aa1-c047-4969-a82b-6f254b2f5cf5"

            const val UPDATE_USER_INFO_URL = "api/auth/updateUserInfo"
            const val DEVICE_TOKEN = "reg_token"
            const val NOTIFICATION_USER_URL = "api/user"
            const val COUNTRY_FLAG = "country_flag"
        }
    }
}
