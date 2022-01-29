package com.dogak.dogakbackend.common.security

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HttpSupport {
    companion object {
        fun getCookie(req: HttpServletRequest, name: String): Cookie = req.cookies
            .toCollection(mutableListOf())
            .first { it.name == name && it.value.isNotEmpty() }

        fun createCookie(cookieConfig: CookieConfig) = cookieConfig.create()

        fun removeCookie(cookie: Cookie, httpServletResponse: HttpServletResponse) {
            val removed = CookieConfig.dummyCookieConfig(cookie.name, cookie.secure)
                .create()
            httpServletResponse.addCookie(removed)
        }
    }
}
