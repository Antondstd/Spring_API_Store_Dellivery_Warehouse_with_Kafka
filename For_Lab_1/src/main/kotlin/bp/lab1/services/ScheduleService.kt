package bp.lab1.services

import bp.lab1.models.Item
import bp.lab1.models.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import java.util.*
import java.util.concurrent.TimeUnit

class ScheduleService {

    @Autowired
    lateinit var itemService: ItemService

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var notifyService: notifyService

    @Scheduled(cron = "0 0/2 * * * *")
    fun checkNotifications() {
        val listNotify =
            notifyService.findByCreatedTimeBefore(Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(1)))
        var listItems: List<Item>
        for (notify in listNotify) {
            listItems = itemService.findByCategoryEqualsAndCompany_IdNot(
                notify.order.item.category,
                notify.order.item.company.id
            )
            if (listItems.size > 0) {
                for (item in listItems) {
                    var order = Order(item = item, user = notify.user)
                    orderService.save(order)
                }
            }
            notifyService.delete(notify)
        }
    }
}
