package com.dogak.dogakbackend.common.security

import com.dogak.dogakbackend.common.config.WebConfig
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CookieManager(
    private val defaultJwtService: DefaultJwtService
) {
    fun setCookie(token: String, req: HttpServletRequest, res: HttpServletResponse) {
        val cookie = HttpSupport.createCookie(
            CookieConfig(
                name = WebConfig.JWT_COOKIE_NAME,
                value = token,
                expires = WebConfig.EXPIRATION,
                secure = "https" == req.scheme
            )
        )

        res.addCookie(cookie)
    }

    fun updateCookie(token: String, req: HttpServletRequest, res: HttpServletResponse) {
        check(defaultJwtService.isUsable(token)) { "토큰이 유효하지 않습니다" }
        setCookie(token, req, res)
    }

    fun removeCookie(req: HttpServletRequest, res: HttpServletResponse) {
        val cookie = HttpSupport.getCookie(req, WebConfig.JWT_COOKIE_NAME)
        HttpSupport.removeCookie(cookie, res)
    }

}