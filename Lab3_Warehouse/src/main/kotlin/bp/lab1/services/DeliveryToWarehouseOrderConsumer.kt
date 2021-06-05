package bp.lab1.services

import bp.lab1.POJO.DeliveryMessageInformation
import bp.lab1.POJO.ItemStockMessage
import bp.lab1.models.ItemWarehouse
import bp.lab1.models.WarehouseOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DeliveryToWarehouseOrderConsumer {
    @Autowired
    lateinit var itemWarehouseService: ItemWarehouseService

    @Autowired
    lateinit var itemStockWarehouseProducer: ItemStockWarehouseProducer

    @Autowired
    lateinit var warehouseOrderService: WarehouseOrderService

    @Transactional
    @KafkaListener(topics = ["DeliveryToWarehouseOrder"], containerFactory = "deliveryListener")
    fun processMessage(message: DeliveryMessageInformation) {
        val itemWarehouse = ItemWarehouse(
            category = message.itemCategory,
            companyName = message.companyName,
            name = message.itemName
        )
        if (itemWarehouseService.takeItem(itemWarehouse)) {
            itemStockWarehouseProducer.send(
                ItemStockMessage(
                    orderID = message.orderID,
                    isInStock = true
                )
            )
        } else {
            itemStockWarehouseProducer.send(
                ItemStockMessage(
                    orderID = message.orderID,
                    isInStock = false
                )
            )
            warehouseOrderService.save(
                WarehouseOrder(
                    orderID = message.orderID,
                    itemName = message.itemName,
                    companyName = message.companyName
                )
            )
        }
    }
}