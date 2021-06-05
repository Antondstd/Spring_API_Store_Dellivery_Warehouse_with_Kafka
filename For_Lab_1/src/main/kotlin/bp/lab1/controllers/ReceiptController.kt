package bp.lab1.controllers

import bp.lab1.models.MyUserDetails
import bp.lab1.models.Receipt
import bp.lab1.reposetory.ReceiptRepository
import bp.lab1.services.ReceiptService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Api(value = "Receipts Controller")
class ReceiptController {

    @Autowired
    lateinit var receiptRepository: ReceiptRepository

    @Autowired
    lateinit var receiptService: ReceiptService

    @ApiOperation(value = "Set receipt as paid")
    @PostMapping("/receipts/{id}/pay")
    @ResponseBody
    fun setPayed(@PathVariable(value = "id") id: Long): ResponseEntity<Any> {
        if (receiptService.pay(id)){

            return ResponseEntity(HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @ApiOperation(value = "Get all your receipts to buy")
    @GetMapping("/receipts")
    @ResponseBody
    fun getChecks(@AuthenticationPrincipal userDetails: MyUserDetails): List<Receipt> =
        receiptRepository.findByOrder_User_Id(userDetails.user.id)
}
