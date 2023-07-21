package example.micronaut

import io.micronaut.core.annotation.Creator
import io.micronaut.serde.annotation.Serdeable
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import jakarta.validation.constraints.NotBlank

@Serdeable // <1>
data class Employee @Creator @BsonCreator constructor( // <4>
    @field:BsonProperty("eid") @param:BsonProperty("eid") @field:NotBlank val eid: String, // <2> <3>
    @field:BsonProperty("name") @param:BsonProperty("name") @field:NotBlank val name: String,
    @field:BsonProperty("salary") @param:BsonProperty("salary") var salary: Int?,
    @field:BsonProperty("dept") @param:BsonProperty("dept") var dept: String?,
    @field:BsonProperty("location") @param:BsonProperty("location") var location: String?) { // <3>

    constructor(eid: String, name: String) : this(eid, name, null, null, null)
}
