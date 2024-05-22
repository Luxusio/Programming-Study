package org.example.springoauth.config

import org.example.springoauth.JwtFilter
import org.example.springoauth.MyTokenService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val successHandler: OAuth2SuccessHandler,
    private val myTokenService: MyTokenService,
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
//            .authorizeRequests()
//            .anyRequest().permitAll()
//            .antMatchers("/token/**").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .addFilterBefore(
//                JwtExceptionFilter(),
//                OAuth2LoginAuthenticationFilter::class.java
//            )
            .addFilterBefore(JwtFilter(myTokenService), UsernamePasswordAuthenticationFilter::class.java)
            .oauth2Login().loginPage("/token/expired")
            .successHandler(successHandler)
            .userInfoEndpoint().userService(customOAuth2UserService)
    }
}
