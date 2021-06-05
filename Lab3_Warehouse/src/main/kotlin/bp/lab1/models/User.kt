package bp.lab1.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "BL_DelivUSERS")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    var id: Long = 0,

    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "phone_number", nullable = false, unique = true)
    var phoneNumber: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "role", nullable = false)
    var role: UserRoles = UserRoles.ROLE_USER,

//    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    var orders: Set<Order>? = null

) : Serializable {
//    @OneToOne(mappedBy = "user")
//    lateinit var wallet: Wallet
//
//    @OneToOne(mappedBy = "user")
//    var company: Company? = null
}

enum class UserRoles {
    ROLE_USER,
    ROLE_COMPANY_OWNER,
    ROLE_MODERATOR,
    ROLE_ADMIN
}
