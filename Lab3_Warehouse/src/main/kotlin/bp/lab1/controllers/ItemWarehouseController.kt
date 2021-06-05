package bp.lab1.controllers

import bp.lab1.POJO.DeliveryMessageInformation
import bp.lab1.models.ItemWarehouse
import bp.lab1.services.ItemWarehouseService
import bp.lab1.services.WarehouseOrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/")
class ItemWarehouseController{

//    @PostConstruct
//    private fun init() {
//        kotlinProducer.send("shit happens");
//    }
    @Autowired
    lateinit var itemWarehouseService: ItemWarehouseService

    @Autowired
    lateinit var warehouseOrderService: WarehouseOrderService

    @PostMapping("/item")
    fun addItem(@RequestBody item: ItemWarehouse): ResponseEntity<Boolean> {
        if (itemWarehouseService.addItem(item)) {

            return ResponseEntity.ok(true)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false)
    }
}