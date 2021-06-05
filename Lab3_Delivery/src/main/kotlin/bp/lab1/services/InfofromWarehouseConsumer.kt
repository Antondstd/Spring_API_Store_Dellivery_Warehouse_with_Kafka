package bp.lab1.services

import bp.lab1.POJO.DeliveryStatus
import bp.lab1.POJO.ItemStockMessage
import bp.lab1.models.StatusOrder
import bp.lab1.reposetory.DeliveryOrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InfofromWarehouseConsumer {
    @Autowired
    lateinit var deliveryOrderRepository: DeliveryOrderRepository

    @Autowired
    lateinit var deliveryStatusProducer: DeliveryStatusProducer

    @Transactional
    @KafkaListener(topics = ["Item-Stock-Info-To-Delivery-From-Warehouse"], containerFactory = "warehouseListener")
    fun processMessage(message: ItemStockMessage) {
        val order = deliveryOrderRepository.findByOrderID(message.orderID)
        if (order != null) {
            val status: StatusOrder

            if (message.isInStock)
                status = StatusOrder.AWAITS_DELIVERY_FROM_WAREHOUSE
            else
                status = StatusOrder.NOT_AT_WAREHOUSE

            order.status = status

            deliveryOrderRepository.save(order)
            deliveryStatusProducer.send(DeliveryStatus(order.orderID, order.status))
        }
    }
}