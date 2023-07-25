package example.micronaut

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class CustomOK(
    val status: Int,
    val message: String,
) {

}
