package bp.lab1.controllers

import bp.lab1.models.Item
import bp.lab1.services.ItemService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Api(value = "Items Controller")
class ItemController {

    @Autowired
    lateinit var itemService: ItemService

    @ApiOperation(value = "Get all items")
    @GetMapping("/items")
    @ResponseBody
    fun getAllitems(): MutableList<Item> {
        return itemService.findAll()
    }

    @ApiOperation(value = "Add item")
    @PostMapping("/items")
    fun addItem(
        @RequestBody itemInformation: Item
    ): ResponseEntity<Item> {
        val item = itemService.addItem(itemInformation)
        if (item == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null)
        return ResponseEntity.ok(item)
    }
}
