package disk.token

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TokenApplication

fun main(args: Array<String>) {
	runApplication<TokenApplication>(*args)
}
