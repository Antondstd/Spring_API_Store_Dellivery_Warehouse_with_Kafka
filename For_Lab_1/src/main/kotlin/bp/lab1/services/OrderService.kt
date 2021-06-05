package bp.lab1.services

import bp.lab1.models.Order
import bp.lab1.models.Receipt
import bp.lab1.models.StatusOrder
import bp.lab1.reposetory.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository) :
    OrderRepository by orderRepository {

    @Autowired
    lateinit var itemService: ItemService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var notifyService: notifyService

    @Autowired
    lateinit var receiptService: ReceiptService

    fun orderItem(idItem: Long, isNotify: Boolean): Order? {
        val user = userService.getCurrentUser()
        try {
            val item = itemService.findById(idItem).get()
            if (user.id == item.company.user.id)
                return null
            val order = Order(
                item = item,
                user = user
            )
            if (isNotify) {
                val notify = notifyService.create()
                notify.order = save(order)
                return notifyService.save(notify).order
            }
            return save(order)
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    fun changeStatus(id: Long, newStatus: StatusOrder): Pair<Boolean, String> {
        try {
            val order = findById(id).get()
            val user = userService.getCurrentUser()
            if (order.status.ordinal > newStatus.ordinal && user.role.ordinal <= 1)
                return Pair(false, "You cannot change the status of that order to lower level")
            if (order.user.id == user.id || user.role.ordinal > 1) {
                when (newStatus) {
                    StatusOrder.ACCEPTED, StatusOrder.PAYED, StatusOrder.RECEIVED -> {
                        order.status = newStatus
                        orderRepository.save(order)
                        return Pair(true, "Order of item ${order.item.name} is change to status $newStatus")
                    }
                    else -> return Pair(false, "You cant use that state as Buyer")
                }
            }
            if (order.item.company.user.id == user.id || user.role.ordinal > 1) {
                when (newStatus) {
                    StatusOrder.INWORK -> {
                        order.status = newStatus
                        orderRepository.save(order)
                        var notify = notifyService.findByOrder_Id(order.id)
                        if (notify != null)
                            notifyService.delete(notify)
                        return Pair(true, "Order of item ${order.item.name} is change to status $newStatus")
                    }

                    StatusOrder.ACCEPTED, StatusOrder.DELIVERING, StatusOrder.CANCELLED, StatusOrder.SPAM -> {
                        order.status = newStatus
                        orderRepository.save(order)
                        return Pair(true, "Order of item ${order.item.name} is change to status $newStatus")
                    }

                    else -> {
                        return Pair(false, "You cant use that state as Seller")
                    }
                }
            }
            return Pair(false, "You can't change the status of that order")
        } catch (e: NoSuchElementException) {
            return Pair(false, "That order doesn't exist")
        }
    }

    fun getReceipt(id: Long): Receipt? {
        try {
            val order = findById(id).get()
            val user = userService.getCurrentUser()
            if (order.user.id == user.id) {
                order.status = StatusOrder.AWAITS_PAYMENT
                val receipt = Receipt(order = order)
                return receiptService.save(receipt)
            }
            return null
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    fun getOrderById(orderID:Long):Order?{
        try {
            val order = findById(orderID).get()
            return order
        }
        catch (e: NoSuchElementException) {
            return null
        }
    }

    fun getAllSellersOrders():List<Order>?{
        val id = userService.getCurrentUser().company?.id
        if (id != null)
            return findByItem_Company_Id(id)
        else
            return null
    }

    fun getAllBuyingOrders(): List<Order>? {
        return findByUser_Id(userService.getCurrentUser().id)
    }
}
