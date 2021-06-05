package bp.lab1.models

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "BL_Company")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    val id: Long = 0,

    @Column(name = "name", nullable = false, unique = true)
    var name: String,

    @Column(name = "phone_number", nullable = false, unique = true)
    var phoneNumber: String,

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    var items: Set<Item>? = null
) {
    @JsonIgnore
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "user_id")
    lateinit var user: User
}
