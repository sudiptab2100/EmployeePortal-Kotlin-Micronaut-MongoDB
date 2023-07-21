package example.micronaut

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.micronaut.core.annotation.NonNull
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import jakarta.validation.Valid
import com.mongodb.client.model.Filters.eq


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

    private val collection: MongoCollection<Employee>
        get() = mongoClient.getDatabase(mongoConf.name)
                .getCollection(mongoConf.collection, Employee::class.java)
}
