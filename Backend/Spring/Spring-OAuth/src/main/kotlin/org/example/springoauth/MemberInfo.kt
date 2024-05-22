package org.example.springoauth

import org.springframework.security.core.userdetails.User

class MemberInfo(
    val id: String,
    val attributes: Map<String, Any>,
) : User(id, "", emptyList())
