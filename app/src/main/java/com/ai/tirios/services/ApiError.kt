package com.ai.tirios.services

import com.ai.tirios.R
import retrofit2.HttpException

class ApiError constructor(error: Throwable) {
    var message = "An error occurred"

    init {
        if (error is HttpException) {
            println("print===>" + error.code())
            val errorJsonString = error.response()?.errorBody()?.string()
            if (error.code() == 401) {
                    this.message = R.string.invaid_token.toString()
            }else if (error.code() == 403) {
                this.message = "Invalid mobile number or passcode"
            }else if (error.code() == 409) {
                this.message = "User with this email already exists"
            }else if (error.code() == 200) {
                this.message = R.string.blank_response.toString()
            }else if (error.code() == 404){
                this.message = R.string.blank_response.toString()
            }
        } else {
            this.message = error.message ?: this.message
        }
    }
}