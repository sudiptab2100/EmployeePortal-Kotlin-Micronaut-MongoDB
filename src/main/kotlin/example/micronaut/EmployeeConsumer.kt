package example.micronaut

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.slf4j.Logger
import org.slf4j.LoggerFactory



@KafkaListener(
    groupId = "new-employee-consumer",
    clientId = "kafka-new-employee-consumer",
    offsetReset = OffsetReset.EARLIEST,
    batch = true
)
class EmployeeConsumer(private val employeeService: EmployeeRepository) {

    private val logger: Logger = LoggerFactory.getLogger(EmployeeConsumer::class.java)
    @Topic("new-employee-producer")
    fun receive(employees: List<Employee>) {

        for(employee in employees) {
            logger.debug("Consuming New Employee: {}", employee)
            val res = employeeService.save(employee).block()
            if(res == true) EmployeeController.statusOf[employee.eid] = 1
            else EmployeeController.statusOf[employee.eid] = 2
            logger.debug("Consumption Result: {}", res)
        }
    }
}