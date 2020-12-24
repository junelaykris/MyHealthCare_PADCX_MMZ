package com.padcx.mmz.shared.data.models

import com.padcx.mmz.shared.network.auth.AuthManager

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface AuthenticationModel {
    var mAuthManager: AuthManager

    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun register(
        username: String,
        email: String,
        password: String,
        deviceId:String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )


    fun updateProfile(
        photoUrl : String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

}