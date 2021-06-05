package bp.lab1.services

import bp.lab1.POJO.DeliveryStatus
import bp.lab1.models.StatusOrder
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class DeliveryStatusProducer(private val kafkaTemplate: KafkaTemplate<String, DeliveryStatus>) {

    fun send(deliveryStatus: DeliveryStatus) {
        kafkaTemplate.send("FromDeliveryOrdersStatus", deliveryStatus)
    }

}