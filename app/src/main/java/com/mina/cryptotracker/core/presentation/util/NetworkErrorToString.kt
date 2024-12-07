package com.mina.cryptotracker.core.presentation.util

import android.content.Context
import com.mina.cryptotracker.R
import com.mina.cryptotracker.core.domain.util.NetworkError

fun NetworkError.toString(context:Context):String{
    val resId =  when(this){
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_Requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_server_error
        NetworkError.SERIALIZATION -> R.string.error_serializtion
        NetworkError.UNKNOWN -> R.string.error_unkown

    }
    return context.getString(resId)
}