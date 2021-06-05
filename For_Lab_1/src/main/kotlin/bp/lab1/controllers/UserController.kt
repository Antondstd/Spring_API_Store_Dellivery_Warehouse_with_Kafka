package bp.lab1.controllers

import bp.lab1.models.AuthenticationRequest
import bp.lab1.services.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Users Controller")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @PostMapping("/user")
    @ApiOperation(value = "Add User")
    fun adduser(
        @RequestBody @ApiParam(value = "A JSON value representing a user details, Needs only email and password") authenticationRequest: AuthenticationRequest
    ): ResponseEntity<String> {
        val token = userService.registerUser(authenticationRequest)
        if (token == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not able to register user")
        return ResponseEntity.ok(token)
    }

    @PostMapping("/authenticate")
    fun authentification(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<String> {

        val token = userService.authorization(authenticationRequest)
        if (token == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Login or Password")
        return ResponseEntity.ok(token)
    }

    @GetMapping("/profile")
    fun profile(): String {
        var auth = SecurityContextHolder.getContext().authentication
        return "Hello ${auth.name}"
    }
}
