package bp.lab1.reposetory


import bp.lab1.models.WarehouseOrder
import org.springframework.data.jpa.repository.JpaRepository

interface WarehouseOrderRepository: JpaRepository<WarehouseOrder, Long> {
    fun findByCompanyNameAndOrderID(companyName:String,orderID:Long):List<WarehouseOrder>
    fun findByItemNameAndCompanyName(itemName:String,companyName: String):List<WarehouseOrder>
}