package bp.lab1.controllers

import bp.lab1.models.StatusOrder
import bp.lab1.reposetory.DeliveryOrderRepository
import bp.lab1.services.DeliveryService
import bp.lab1.services.DeliveryStatusProducer
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class DeliveryController {

    @Autowired
    lateinit var deliveryStatusProducer: DeliveryStatusProducer

    @Autowired
    lateinit var deliveryService: DeliveryService

    //TODO Насрать post для иземенения статуса заказа
    @ApiOperation(value = "Change Order status")
    @PutMapping("/orders/{id}")
    fun changeOrderStatus(
        @PathVariable(value = "id") id: Long,
        @RequestParam("newState") newState: StatusOrder
    ): ResponseEntity<Boolean> {
        if (deliveryService.changeStatus(id,newState))
            return ResponseEntity.ok(true)
        return ResponseEntity.badRequest().body(false)
    }
}