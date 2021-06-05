package bp.lab1.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "BL_Order")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    var id: Long = 0,

    @JsonIgnore
    @OneToOne(mappedBy = "order")
    var receipt: Receipt? = null,

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    val item: Item,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "status")
    var status: StatusOrder = StatusOrder.CREATED,

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    val created_time: Date = Date(),

    @JsonIgnore
    @OneToOne(mappedBy = "order")
    var notify: Notify? = null

)

enum class StatusOrder {
    CREATED,
    INWORK,
    ACCEPTED,
    AWAITS_PAYMENT,
    PAYED,
    AWAITS_CONFIRMATION_FROM_WAREHOUSE,
    AWAITS_DELIVERY_FROM_WAREHOUSE,
    NOT_AT_WAREHOUSE,
    DELIVERING,
    RECEIVED,
    CANCELLED,
    SPAM
}
