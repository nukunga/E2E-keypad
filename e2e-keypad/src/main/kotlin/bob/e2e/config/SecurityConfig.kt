package bob.e2e.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() } // CSRF 비활성화
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/keypad/**").permitAll() // 특정 경로 허용
                    .requestMatchers("/swagger-ui/**").permitAll() // Swagger UI 접근 허용
                    .requestMatchers("/v3/api-docs/**").permitAll() // OpenAPI 3 문서 접근 허용
                    .anyRequest().authenticated() // 나머지 요청은 인증 필요
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
