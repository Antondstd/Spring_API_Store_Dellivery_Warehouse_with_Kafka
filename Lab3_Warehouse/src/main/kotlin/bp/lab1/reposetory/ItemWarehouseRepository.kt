package bp.lab1.reposetory

import bp.lab1.models.ItemWarehouse
import org.springframework.data.jpa.repository.JpaRepository

interface ItemWarehouseRepository : JpaRepository<ItemWarehouse, Long> {
    fun findByNameAndCompanyName(name:String,companyName:String):ItemWarehouse?
}