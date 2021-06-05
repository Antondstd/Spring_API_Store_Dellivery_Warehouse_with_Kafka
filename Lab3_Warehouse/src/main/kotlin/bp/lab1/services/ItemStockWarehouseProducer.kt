package bp.lab1.services

import bp.lab1.POJO.ItemStockMessage
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ItemStockWarehouseProducer(private val kafkaTemplate: KafkaTemplate<String, ItemStockMessage>) {

    fun send(itemStockMessage: ItemStockMessage) {
        kafkaTemplate.send("Item-Stock-Info-To-Delivery-From-Warehouse", itemStockMessage)
    }
}