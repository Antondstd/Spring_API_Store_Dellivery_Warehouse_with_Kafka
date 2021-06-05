package bp.lab1.services

import bp.lab1.POJO.DeliveryMessageInformation
import bp.lab1.models.StatusOrder
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class DeliveryToWarehouseOrderProducer(private val kafkaTemplate: KafkaTemplate<String, DeliveryMessageInformation>) {
    fun send(deliveryMessageInformation: DeliveryMessageInformation) {
        kafkaTemplate.send("DeliveryToWarehouseOrder", deliveryMessageInformation)
    }
}