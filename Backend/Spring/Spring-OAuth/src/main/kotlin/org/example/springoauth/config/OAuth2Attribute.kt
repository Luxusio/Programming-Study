package org.example.springoauth.config


class OAuth2Attribute(
    val attributes: Map<String, Any>? = null,
    val attributeKey: String? = null,
    val email: String? = null,
    val name: String? = null,
    val picture: String? = null,
) {

    fun convertToMap(): Map<String, Any?> {
        val map: MutableMap<String, Any?> = HashMap()
        map["id"] = attributeKey
        map["key"] = attributeKey
        map["name"] = name
        map["email"] = email
        map["picture"] = picture

        return map
    }

    companion object {
        fun of(
            provider: String, attributeKey: String,
            attributes: Map<String, Any>
        ): OAuth2Attribute {
            return when (provider) {
                "google" -> ofGoogle(attributeKey, attributes)
                else -> throw RuntimeException()
            }
        }

        private fun ofGoogle(
            attributeKey: String,
            attributes: Map<String, Any>
        ): OAuth2Attribute {
            return OAuth2Attribute(
                attributes,
                attributeKey,
                attributes["email"] as String?,
                attributes["name"] as String?,
                attributes["picture"] as String?,
            )
        }
    }
}
