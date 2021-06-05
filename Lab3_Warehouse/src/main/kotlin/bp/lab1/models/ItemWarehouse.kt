package bp.lab1.models

import javax.persistence.*

@Entity
@Table(name = "BL_ItemWarehouse")
data class ItemWarehouse(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    var id: Long = 0,

    @Column(name = "category", nullable = false)
    var category: Int = 1,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "company_name", nullable = false)
    var companyName: String? = null,

    @Column(name = "amount", nullable = false)
    var amount:Int = 0
)