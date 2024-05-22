package org.example.springoauth

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(
    private val myTokenService: MyTokenService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val context = SecurityContextHolder.getContext()
        try {
            context.authentication = myTokenService.getAuthenticationIfValid(request)
        } finally {
            chain.doFilter(request, response)
        }
    }
}
