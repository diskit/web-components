package app

import java.util.*

class TokenStorage {

    private val storage: MutableSet<Token> = mutableSetOf()

    fun isExist(token: Token) = storage.contains(token)

    fun store(token: Token) = storage.add(token)
}

data class Token(val value: String) {

    companion object {
        fun generate() = UUID.randomUUID().toString().let(::Token)
    }
}