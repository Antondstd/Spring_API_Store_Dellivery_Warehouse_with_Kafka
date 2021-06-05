package bp.lab1.services

import bp.lab1.models.Item
import bp.lab1.reposetory.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService(private val itemRepository: ItemRepository) :
    ItemRepository by itemRepository {

    @Autowired
    lateinit var userService: UserService

    fun addItem(item: Item): Item? {
        val user = userService.getCurrentUser()
        // TODO("Доделать права")
        if (user == null || user.company == null)
            return null
        item.company = user.company!!
        return save(item)
    }
}
