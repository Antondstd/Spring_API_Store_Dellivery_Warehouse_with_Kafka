package bp.lab1.POJO

import java.io.Serializable

data class DeliveryMessageInformation(
    var itemName:String? = null,
    var itemCategory: Int = 0,
    var companyName: String? = null,
    var phone:String? = null,
    var orderID:Long = 0
):Serializable