package bp.lab1.services

import bp.lab1.models.ItemWarehouse
import bp.lab1.reposetory.ItemWarehouseRepository
import bp.lab1.reposetory.WarehouseOrderRepository
import org.springframework.stereotype.Service

@Service
class WarehouseOrderService(private val warehouseOrderRepository: WarehouseOrderRepository) :
    WarehouseOrderRepository by warehouseOrderRepository {
}