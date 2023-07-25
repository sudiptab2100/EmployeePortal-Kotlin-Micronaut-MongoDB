package example.micronaut

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CustomError(
    val status: Int,
    val error: String,
    val message: String
) {

}
