package org.example.springoauth.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.springoauth.MyTokenService
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2SuccessHandler(
    private val myTokenService: MyTokenService,
    private val objectMapper: ObjectMapper,
) : SimpleUrlAuthenticationSuccessHandler() {
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication
    ) {
        val oAuth2User = authentication.principal as OAuth2User
//        val userDto: UserDto = userRequestMapper.toDto(oAuth2User)

//        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User)
//        log.info("토큰 발행 시작")

        val token: AccessRefreshToken = myTokenService.generateToken(oAuth2User.getAttribute("a")!!, "USER")
        // 최초 로그인 시 회원가입을 진행한다.


        val targetUrl = UriComponentsBuilder.fromUriString("/home")
            .queryParam("token", "token")
            .build().toUriString()
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}
