package bp.lab1.models

import javax.persistence.*

@Entity
@Table(name = "BL_WALLETS")
class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wallet_id")
    var id: Long = 0,

    @Column(name = "balance")
    var balance: Double = 500.0,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User
) {

    infix fun isEnoughToPay(needMoney: Double) = balance >= needMoney
}
