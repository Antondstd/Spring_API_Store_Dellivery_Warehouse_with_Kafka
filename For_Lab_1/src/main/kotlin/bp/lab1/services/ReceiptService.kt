package bp.lab1.services

import bp.lab1.POJO.DeliveryMessageInformation
import bp.lab1.models.StatusOrder
import bp.lab1.reposetory.ReceiptRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReceiptService(private val receiptRepository: ReceiptRepository) :
    ReceiptRepository by receiptRepository {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var walletService: WalletService

    @Autowired
    lateinit var orderInfoToDeliveryProducer: OrderInfoToDeliveryProducer

    @Transactional
    fun pay(id: Long): Boolean {
        try {
            val user = userService.getCurrentUser()
            var order = findById(id).get().order
            if ((user.wallet isEnoughToPay order.item.price)) {
                user.wallet.balance -= order.item.price
                order.item.company.user.wallet.balance += order.item.price
                walletService.save(user.wallet)
                walletService.save(order.item.company.user.wallet)
                order.status = StatusOrder.PAYED
                order = orderService.save(order)
                orderInfoToDeliveryProducer.send(DeliveryMessageInformation(itemName = order.item.name,
                itemCategory = order.item.category,
                companyName = order.item.company.name,
                phone = order.user.phoneNumber,
                orderID = order.id))
                return true
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }
}
