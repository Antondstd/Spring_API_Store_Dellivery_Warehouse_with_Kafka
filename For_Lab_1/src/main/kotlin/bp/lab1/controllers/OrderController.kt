package bp.lab1.controllers

import bp.lab1.models.MyUserDetails
import bp.lab1.models.Order
import bp.lab1.models.Receipt
import bp.lab1.models.StatusOrder
import bp.lab1.reposetory.ItemRepository
import bp.lab1.reposetory.NotifyRepository
import bp.lab1.reposetory.OrderRepository
import bp.lab1.reposetory.ReceiptRepository
import bp.lab1.services.OrderService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@Api(value = "Orders Controller")
class OrderController {

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var receiptRepository: ReceiptRepository

    @Autowired
    lateinit var notifyRepository: NotifyRepository

    @ApiOperation(value = "Make order")
    @PostMapping("/orders")
    fun orderItem(
        @RequestParam(value = "id") idItem: Long,
        @RequestParam(value = "notify") isNotify: Boolean
    ): ResponseEntity<Order> {
        val order = orderService.orderItem(idItem, isNotify)
        if (order == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null)
        return ResponseEntity.ok(order)
    }

    @ApiOperation(value = "Change Order status")
    @PutMapping("/orders/{id}")
    fun changeOrderStatus(
        @PathVariable(value = "id") id: Long,
        @RequestParam("newState") newState: StatusOrder
    ): ResponseEntity<String> {
        val resultPair = orderService.changeStatus(id, newState)
        if (resultPair.first)
            return ResponseEntity.ok(resultPair.second)
        return ResponseEntity.badRequest().body(resultPair.second)
    }

    @ApiOperation(value = "Paying for order and create recipe")
    @PostMapping("/orders/{id}/pay")
    fun awaitPaymentAndgetReceipt(@PathVariable(value = "id") id: Long): ResponseEntity<Receipt?> {
        val receipt = orderService.getReceipt(id)
        if (receipt == null)
            return ResponseEntity.badRequest().body(null)
        return ResponseEntity.ok(receipt)
    }

    @ApiOperation(value = "Get all your orders selling stuff")
    @GetMapping("/shop/orders")
    @ResponseBody
    fun getAllSellOrders(response: HttpServletResponse): List<Order>? {
        return orderService.getAllSellersOrders()
//        val userDet = (SecurityContextHolder.getContext().authentication.principal as MyUserDetails)
//        if (userDet.user.company?.id == null) {
//            response.sendError(402, "Вы не являетесь продавцом")
//            return null
//        }
//        return orderRepository.findByItem_Company_Id(userDet.user.company!!.id)
    }

    @ApiOperation(value = "Get all your orders to buy")
    @GetMapping("/orders")
    @ResponseBody
    fun getAllBuyingOrders(): List<Order>? {
        return orderService.getAllBuyingOrders()
    }
}
