package example.micronaut

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface EmployeeProducer {

    @Topic("new-employee-producer")
    fun send(@KafkaKey eid: String, employee: Employee)
}