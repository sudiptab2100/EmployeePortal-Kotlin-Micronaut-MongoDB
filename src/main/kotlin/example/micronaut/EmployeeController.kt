package example.micronaut

import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.CONFLICT
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import jakarta.validation.Valid

@Controller("/api/emp") // <1>
open class EmployeeController(private val employeeService: EmployeeRepository) { // <2>

    @Get("/all") // <3>
    fun list(): Publisher<Employee> = employeeService.list()

    @Get("/filter/{eid}")
    fun getById(eid: String) = employeeService.getByEID(eid)

    @Post("/add") // <4>
    open fun save(@Valid employee: Employee): Mono<HttpStatus> { // <5>
        return employeeService.save(employee) // <6>
                .map { added: Boolean -> if (added) CREATED else CONFLICT }
    }
}
