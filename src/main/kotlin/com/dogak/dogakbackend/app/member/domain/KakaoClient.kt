package com.dogak.dogakbackend.app.member.domain

import com.dogak.dogakbackend.app.member.dto.KakaoMemberInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(value =  "kakao", url = "https://kapi.kakao.com")
interface KakaoClient {

    @GetMapping("/v2/user/me")
    fun getUserInfo(@RequestHeader("Authorization") header: String) : KakaoMemberInfo

}