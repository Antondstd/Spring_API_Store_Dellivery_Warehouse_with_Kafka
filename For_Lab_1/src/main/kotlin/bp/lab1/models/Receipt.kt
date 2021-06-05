package bp.lab1.models


import java.util.*
import javax.persistence.*

@Entity
@Table(name = "BL_RECEIPT")
class Receipt(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "receipt_id")
    var id: Long = 0,

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    var order: Order,

    @Column(name = "status")
    var isPayed: Boolean = false,

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    var createdTime: Date = Date()

)