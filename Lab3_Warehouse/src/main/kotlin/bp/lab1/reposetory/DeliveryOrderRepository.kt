package bp.lab1.reposetory

import bp.lab1.models.DeliveryOrder
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryOrderRepository: JpaRepository<DeliveryOrder, Long> {
}