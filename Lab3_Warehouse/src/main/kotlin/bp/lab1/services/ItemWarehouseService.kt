package bp.lab1.services

import bp.lab1.POJO.ItemStockMessage
import bp.lab1.models.ItemWarehouse
import bp.lab1.reposetory.ItemWarehouseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemWarehouseService(private val itemWarehouseRepository: ItemWarehouseRepository) :
    ItemWarehouseRepository by itemWarehouseRepository {

    @Autowired
    lateinit var warehouseOrderService: WarehouseOrderService

    @Autowired
    lateinit var itemStockWarehouseProducer: ItemStockWarehouseProducer

    @Transactional
    fun addItem(newItem: ItemWarehouse): Boolean {
        if (newItem.name == null || newItem.companyName == null)
            return false
        var item = findByNameAndCompanyName(newItem.name!!, newItem.companyName!!)
        if (item == null)
            item = save(newItem)
        else {
            item.amount += newItem.amount
            item = save(item)
        }
        val listOrder = warehouseOrderService.findByItemNameAndCompanyName(
            itemName = item.name!!,
            companyName = item.companyName!!
        )
        if (listOrder.size > 0) {
            var counter = 0;
            while (item.amount > 0 && counter < listOrder.size) {
                val order = listOrder.get(counter)
                itemStockWarehouseProducer.send(ItemStockMessage(orderID = order.orderID, isInStock = true))
                warehouseOrderService.delete(order)
                item.amount--
                counter++
            }
            save(item)
        }
        return true
    }

    @Transactional
    fun takeItem(item: ItemWarehouse): Boolean {
        if (item.name == null || item.companyName == null)
            return false
        val foundedItem = findByNameAndCompanyName(item.name!!, item.companyName!!)
        if (foundedItem == null || foundedItem.amount == 0)
            return false
        foundedItem.amount--
        save(foundedItem)
        return true
    }
}