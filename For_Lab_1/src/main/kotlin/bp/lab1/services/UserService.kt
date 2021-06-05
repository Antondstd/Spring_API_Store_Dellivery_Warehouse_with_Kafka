package bp.lab1.services

import bp.lab1.models.AuthenticationRequest
import bp.lab1.models.MyUserDetails
import bp.lab1.models.User
import bp.lab1.models.Wallet
import bp.lab1.reposetory.UserRepository
import bp.lab1.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
@Primary
class UserService(
    private val userRepository: UserRepository
) : UserRepository by userRepository {

    @Autowired
    private lateinit var walletService: WalletService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtUtil: JwtUtil

    fun getCurrentUser(): User {
        return findByEmail((SecurityContextHolder.getContext().authentication.principal as MyUserDetails).username)!!
    }

    fun registerUser(authenticationRequest: AuthenticationRequest): String? {
        try {
            if (authenticationRequest.username == null || authenticationRequest.password == null || authenticationRequest.phoneNumber == null)
                return null
            val user = User(
                email = authenticationRequest.username!!,
                password = authenticationRequest.password!!,
                phoneNumber = authenticationRequest.phoneNumber!!
            )
            walletService.save(Wallet(user = save(user)))
            return authorization(authenticationRequest)
        } catch (e: Exception) {
            return null
        }
    }

    fun authorization(authenticationRequest: AuthenticationRequest): String? {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authenticationRequest.username,
                    authenticationRequest.password
                )
            )
        } catch (e: Exception) {
            return null
        }
        val user = findByEmail(authenticationRequest.username!!)
        if (user != null) {
            val jwtToken = jwtUtil.generateToken(user.email)
            return jwtUtil.generateToken(user.email)
        }
        return null
    }
}
