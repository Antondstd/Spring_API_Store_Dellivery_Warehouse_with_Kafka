package bp.lab1.services

import bp.lab1.models.MyUserDetails
import bp.lab1.reposetory.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Override
    override fun loadUserByUsername(email: String): UserDetails? {
        // val encoder = passwordEncoder()
        var user = userRepository.findByEmail(email)
        // user.password = encoder.encode(user.password)
        if (user != null)
            return MyUserDetails(user)
        return null
    }
}
