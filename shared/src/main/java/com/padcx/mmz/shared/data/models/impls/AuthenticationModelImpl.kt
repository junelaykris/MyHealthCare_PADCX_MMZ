package com.padcx.mmz.shared.data.models.impls

import com.padcx.mmz.shared.data.models.AuthenticationModel
import com.padcx.mmz.shared.network.auth.AuthManager
import com.padcx.mmz.shared.network.auth.FirebaseAuthManager

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */

object AuthenticationModelImpl : AuthenticationModel {

    override var mAuthManager: AuthManager = FirebaseAuthManager

    override fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.login(email, password, onSuccess, onFailure)
    }

    override fun register(
        username: String,
        email: String,
        password: String,
        deviceId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.register(username, email, password, onSuccess, onFailure)
    }

    override fun updateProfile(photoUrl: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.updateProfile(photoUrl, onSuccess, onFailure)
    }

}