package disk.token

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController()
@RequestMapping("/v1/token")
class TokenResource(private val storage: TokenStorage) {

    @GetMapping
    fun get(): TokenJson {
        return TokenJson("value")
    }

    @PostMapping
    fun post(@RequestBody request: TokenRequest): ResponseEntity<TokenJson> {
        val token = UUID.randomUUID().toString()
        storage.set(token)
        return ResponseEntity.status(201).body(TokenJson(token))
    }

    @DeleteMapping("/{value}")
    fun delete(@PathVariable("value") token: String): ResponseEntity<Void> {
        return takeIf { storage.remove(token) }
            ?.let { ResponseEntity.status(204).body(null) }
            ?: ResponseEntity.status(404).body(null)
    }
}

data class TokenRequest(val consumer: String)

data class TokenJson(val token: String)
