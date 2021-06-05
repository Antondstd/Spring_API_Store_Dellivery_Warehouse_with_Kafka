package bp.lab1.services

import bp.lab1.POJO.DeliveryStatus
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class DeliveryConsumer {

    @Autowired
    lateinit var orderService: OrderService

    @KafkaListener(topics = ["FromDeliveryOrdersStatus"], containerFactory = "deliveryListener")
    fun processMessage(message: DeliveryStatus) {
        val order = orderService.getOrderById(message.idOrder)
        if (order != null && message.statusOrder != null) {
            order.status = message.statusOrder
            orderService.save(order)
        }
    }
}