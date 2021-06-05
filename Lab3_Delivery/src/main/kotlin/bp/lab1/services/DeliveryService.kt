package bp.lab1.services

import bp.lab1.POJO.DeliveryStatus
import bp.lab1.models.StatusOrder
import bp.lab1.reposetory.DeliveryOrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeliveryService(private val deliveryOrderRepository: DeliveryOrderRepository) :
    DeliveryOrderRepository by deliveryOrderRepository {

    @Autowired
    lateinit var deliveryStatusProducer: DeliveryStatusProducer

    @Transactional
    fun changeStatus(id: Long, newStatus: StatusOrder): Boolean {
        val order = findByOrderID(id)
        if (order == null || newStatus < order.status)
            return false
        order.status = newStatus
        save(order)
        deliveryStatusProducer.send(
            DeliveryStatus(
                idOrder = order.orderID,
                statusOrder = order.status
            )
        )

        return true
    }
}