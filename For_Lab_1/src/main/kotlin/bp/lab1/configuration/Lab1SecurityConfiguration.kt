package bp.lab1.configuration

import bp.lab1.filters.JwtFilter
import bp.lab1.services.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableWebSecurity
open class Lab1SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var myUserDetailsService: MyUserDetailsService

    @Autowired
    lateinit var jwtFilter: JwtFilter
//    override fun configure(http: HttpSecurity) {
//
//        http
//            .csrf().disable()
//            .authorizeRequests()
//            .antMatchers("/action/*").hasAuthority("ROLE_REGISTERED")
//            .antMatchers("/show/*").hasAuthority("ROLE_REGISTERED")
//            .antMatchers("/**").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginProcessingUrl("/try_login")
//            .defaultSuccessUrl("/profile")
//            .failureUrl("/fail")
//            .and()
//            .logout()
//            .logoutUrl("/logout")
//            .deleteCookies("JSESSIONID")
//            .and().sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//    }

    override fun configure(http: HttpSecurity) {

        http
            .csrf().disable()
            .authorizeRequests()
//            .antMatchers("/receipts").hasAnyAuthority()
//            .antMatchers("/receipts/*").hasAnyAuthority()
            .antMatchers("/company").hasAuthority("ROLE_USER")
            .antMatchers("/receipts*").authenticated()
            .antMatchers("/shop*").hasAnyAuthority("ROLE_COMPANY_OWNER","ROLE_MODERATOR","ROLE_ADMIN")
            .antMatchers("/orders*").hasAnyAuthority("ROLE_USER","ROLE_COMPANY_OWNER")
            .antMatchers("/action/*").hasAuthority("ROLE_REGISTERED")
            .antMatchers("/show/*").hasAuthority("ROLE_REGISTERED")
            .antMatchers("/**").permitAll()
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(myUserDetailsService)
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }
}

@Configuration
@EnableSwagger2
class SpringFoxConfig {
    private val API_VERSION = "0.0.1"

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("bp.lab1.controllers"))
            .paths(PathSelectors.any())
            .build()
    }
}