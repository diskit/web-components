package app

data class ComponentResource(private val storage: TokenStorage, private val configuration: ResourceConfiguration) {
    fun path(credential: Credential): String {
        return Token.generate()
            .also { storage.store(it, credential) }
            .let { "${configuration.endpoint}?${configuration.key}=${it.value}" }
    }
}

data class ResourceConfiguration(val endpoint: String, val key: String)