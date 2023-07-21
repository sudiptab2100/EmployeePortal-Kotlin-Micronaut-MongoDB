package example.micronaut

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.micronaut.core.annotation.NonNull
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import jakarta.validation.Valid
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import org.bson.conversions.Bson


@Singleton // <1>
open class MongoDbEmployeeRepository(
    private val mongoConf: MongoDbConfiguration,  // <2>
    private val mongoClient: MongoClient) : EmployeeRepository { // <3>

    override fun save(@Valid employee: Employee): Mono<Boolean> =
        Mono.from(collection.insertOne(employee)) // <4>
                .map { true }
                .onErrorReturn(false)

    @NonNull
    override fun list(): Publisher<Employee> = collection.find() // <4>

    @NonNull
    override fun getByEID(eid: String): Publisher<Employee> = collection.find(eq("eid", eid))

    private fun updateSalary(eid: String, salary: Int): Mono<Boolean> {
        val updates: Bson = Updates.combine(
            Updates.set("salary", salary)
        )
        return Mono.from(collection.updateOne(eq("eid", eid), updates))
            .map { true }
            .onErrorReturn(false)
    }

    override fun updateAField(eid: String, field: String, value: String): Mono<Boolean> {
        if(field == "salary") {
            return updateSalary(eid, value.toInt())
        }
        val updates: Bson = Updates.combine(
            Updates.set(field, value)
        )
        return Mono.from(collection.updateOne(eq("eid", eid), updates))
            .map { true }
            .onErrorReturn(false)
    }

    override fun dropAnEmployee(eid: String): Mono<Boolean> =
        Mono.from(collection.findOneAndDelete(eq("eid", eid))) // <4>
            .map { true }
            .onErrorReturn(false)

    private val collection: MongoCollection<Employee>
        get() = mongoClient.getDatabase(mongoConf.name)
                .getCollection(mongoConf.collection, Employee::class.java)
}
