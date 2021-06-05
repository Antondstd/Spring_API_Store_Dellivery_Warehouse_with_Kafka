package bp.lab1.services

import bp.lab1.models.Notify
import bp.lab1.reposetory.NotifyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class notifyService(private val notifyRepository: NotifyRepository) :
    NotifyRepository by notifyRepository {

    @Autowired
    lateinit var userService: UserService

    fun create(): Notify {
        return Notify(user = userService.getCurrentUser())
    }
}
