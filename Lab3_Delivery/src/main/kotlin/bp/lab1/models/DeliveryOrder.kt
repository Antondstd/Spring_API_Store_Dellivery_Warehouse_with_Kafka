package bp.lab1.models

import javax.persistence.*

@Entity
@Table(name = "BL_Delivery_Order")
class DeliveryOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long = 0,

    @Column(name = "order_id")
    val orderID: Long = 0,

    @Column(name = "itemName")
    val itemName:String? = null,

    @Column(name = "companyName")
    val companyName: String? = null,

    @Column(name = "clientPhone")
    var clientPhone:String? = null,

    @Column(name = "status")
    var status: StatusOrder = StatusOrder.AWAITS_CONFIRMATION_FROM_WAREHOUSE
)