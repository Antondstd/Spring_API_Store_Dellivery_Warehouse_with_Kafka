package bp.lab1.controllers

import bp.lab1.models.Company
import bp.lab1.models.MyUserDetails
import bp.lab1.reposetory.CompanyRepository
import bp.lab1.services.CompanyService
import bp.lab1.services.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Company Controller")
class CompanyController {
    @Autowired
    lateinit var companyService: CompanyService

    @ApiOperation(value = "Add company")
    @PostMapping("/company")
    @ResponseBody
    fun addCompany(
        @RequestBody company: Company
    ): ResponseEntity<Long?> {
        val idCompany = companyService.saveCompany(company)
        if (idCompany == null)
            return ResponseEntity.badRequest().body(null)
        return ResponseEntity.ok(idCompany)
    }
}
