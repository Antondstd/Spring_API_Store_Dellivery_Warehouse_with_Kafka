package bp.lab1.controllers

import bp.lab1.POJO.DeliveryMessageInformation
import bp.lab1.services.OrderInfoToDeliveryProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class DeliveryController(private val kotlinProducerOrderInfoTo: OrderInfoToDeliveryProducer) {


//    @PostMapping("/message")
//    fun publish(@RequestBody message: String) {
//        val del = DeliveryMessageInformation("Вешалка",1,"Рога и Копыта","+79881010")
//        kotlinProducerOrderInfoTo.send(message,del)
//    }
}