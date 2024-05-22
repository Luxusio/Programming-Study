package org.example.springoauth.config

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private val oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {

        //	2번
        val oAuth2User = oAuth2UserService.loadUser(userRequest)

        //	3번
        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName


        // 4번
        val oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.attributes)

        val memberAttribute: Map<String, Any?> = oAuth2Attribute.convertToMap()

        return DefaultOAuth2User(
            listOf(),
            memberAttribute, "email"
        )
    }
}
