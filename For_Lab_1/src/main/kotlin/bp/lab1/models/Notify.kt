package bp.lab1.models

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "BL_NOTIFY")
class Notify(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "check_id")
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    val createdTime: Date = Date()
) {
    @OneToOne
    @JoinColumn(name = "order_id")
    lateinit var order: Order
}
