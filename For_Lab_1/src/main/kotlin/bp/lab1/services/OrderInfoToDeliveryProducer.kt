package bp.lab1.services

import bp.lab1.POJO.DeliveryMessageInformation
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderInfoToDeliveryProducer(private val kafkaTemplate: KafkaTemplate<String, DeliveryMessageInformation>) {

    fun send(info:DeliveryMessageInformation) {
        kafkaTemplate.send("OrderInfoToDelivery", info)
    }

}