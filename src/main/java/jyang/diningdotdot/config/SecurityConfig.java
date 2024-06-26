package jyang.diningdotdot.config;

import jyang.diningdotdot.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login",
                                "/users/join", "/users/joinProc",
                                "/partners/join", "/partners/joinProc").permitAll()
                        .requestMatchers("/js/**", "/css/**", "/img/**").permitAll() // 정적 파일
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/partners/reservations/**", "/partners/stores/**", "/partners/reviews/**").hasRole("MANAGER")
                        .anyRequest().authenticated() // 그외 경로는 로그인 사용자만 허용
                );
        http.
                formLogin(auth -> auth
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );
        http
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

        http.
                httpBasic((AbstractHttpConfigurer::disable));

//        http.
//                csrf(AbstractHttpConfigurer::disable);

        http.
                sessionManagement(auth -> auth
                        .maximumSessions(3) // 최대 동시 로그인 수
                        .maxSessionsPreventsLogin(false)); // true -> 새 로그인 차단, false -> 기존 세션 하나 삭제

        http.
                sessionManagement(auth -> auth
                        .sessionFixation().changeSessionId()); // 로그인 시 동일한 세션에 대한 id 변경

        http.
                logout(auth -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}
