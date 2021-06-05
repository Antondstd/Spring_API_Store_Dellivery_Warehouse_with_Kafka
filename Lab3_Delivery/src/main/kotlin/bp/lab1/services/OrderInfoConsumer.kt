package bp.lab1.services

import bp.lab1.POJO.DeliveryMessageInformation
import bp.lab1.models.DeliveryOrder
import bp.lab1.reposetory.DeliveryOrderRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class OrderInfoConsumer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var deliveryOrderRepository: DeliveryOrderRepository

    @Autowired
    lateinit var deliveryToWarehouseOrderProducer: DeliveryToWarehouseOrderProducer

    @Transactional
    @KafkaListener(topics = ["OrderInfoToDelivery"], containerFactory = "websiteOrdersListener")
    fun processMessage(deliveryMessageInformation: DeliveryMessageInformation) {
        logger.info("got message: {}", deliveryMessageInformation.toString())
        val deliveryOrder = DeliveryOrder(orderID = deliveryMessageInformation.orderID,
        itemName = deliveryMessageInformation.itemName,
        companyName = deliveryMessageInformation.companyName,
        clientPhone = deliveryMessageInformation.phone)
        deliveryOrderRepository.save(deliveryOrder)
        deliveryToWarehouseOrderProducer.send(deliveryMessageInformation)
    }
}