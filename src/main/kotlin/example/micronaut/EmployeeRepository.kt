package example.micronaut

import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import jakarta.validation.Valid

interface EmployeeRepository {

    fun list(): Publisher<Employee>

    fun getByEID(eid: String): Publisher<Employee>

    fun updateAField(eid: String, field: String, value: String): Mono<Boolean>

    fun dropAnEmployee(eid: String): Mono<Boolean>

    fun save(@Valid employee: Employee): Mono<Boolean>
}
