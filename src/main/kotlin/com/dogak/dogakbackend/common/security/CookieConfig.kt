package com.dogak.dogakbackend.common.security


import javax.servlet.http.Cookie

data class CookieConfig(
    val name: String = "",
    val value: String = "",
    val expires: Int = 0,
    val secure: Boolean = false
) {
    fun create(): Cookie {
        val cookie = Cookie(name, value)
        cookie.maxAge = expires;
        cookie.secure = secure;
        cookie.isHttpOnly = false;
        cookie.path = "/";
        return cookie;
    }

    companion object {
        fun dummyCookieConfig(name: String, secure: Boolean) = CookieConfig(
            name = name,
            value = "",
            expires = 10,
            secure = secure
        )
    }
}