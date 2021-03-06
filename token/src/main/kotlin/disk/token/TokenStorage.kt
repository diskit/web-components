package disk.token

import org.springframework.stereotype.Component

@Component
class TokenStorage {
    private val values: MutableSet<String> = mutableSetOf()

    fun set(value: String) {
        values.takeUnless { values.contains(value) }
            ?.let { it.add(value) }
            ?: throw Exception("$value is already exist.")
    }

    fun remove(value: String) = values.remove(value)
}