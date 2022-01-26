package com.uszkaisandor.mealdeas.shared

import com.uszkaisandor.mealdeas.api.AuthInterceptor
import com.uszkaisandor.mealdeas.data.AccountProperties

class SessionManager
constructor(
    private val authInterceptor: AuthInterceptor
) {

    private var cachedAccountProperties: AccountProperties? = null

    fun setAccountProperties(accountProperties: AccountProperties?) {
        authInterceptor.token = accountProperties?.token ?: ""
        cachedAccountProperties = accountProperties
    }
}