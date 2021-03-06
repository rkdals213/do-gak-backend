package com.dogak.dogakbackend.common.config

import com.dogak.dogakbackend.common.properties.FrontendUrl
import com.dogak.dogakbackend.common.security.JwtInterceptor
import com.dogak.dogakbackend.common.security.JwtSessionArgumentResolver
import com.dogak.dogakbackend.common.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtArgsResolver: JwtSessionArgumentResolver,
    private val frontendUrl: FrontendUrl
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtInterceptor())
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(jwtArgsResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOriginPatterns(frontendUrl.url)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "DELETE")
            .allowCredentials(true)
    }

    @Bean
    fun jwtInterceptor(): JwtInterceptor {
        return JwtInterceptor(jwtTokenProvider)
    }
}