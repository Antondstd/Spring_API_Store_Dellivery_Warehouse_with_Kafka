package bp.lab1.services

import bp.lab1.models.Company
import bp.lab1.models.User
import bp.lab1.models.UserRoles
import bp.lab1.reposetory.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CompanyService(private val companyRepository: CompanyRepository) :
    CompanyRepository by companyRepository {

    @Autowired
    lateinit var userService: UserService

    fun saveCompany(company: Company): Long? {
        try {
            val user = userService.getCurrentUser()
            user.role = UserRoles.ROLE_COMPANY_OWNER
            company.user = user
            return save(company).id
        } catch (e: Exception) {
            return null
        }
    }

    fun getOwner(id: Long): User? {
        return findById(id).get().user
    }
}
