package bp.lab1.POJO

import bp.lab1.models.StatusOrder

data class DeliveryStatus(val idOrder: Long = 0,
                          val statusOrder: StatusOrder? = null) {
}