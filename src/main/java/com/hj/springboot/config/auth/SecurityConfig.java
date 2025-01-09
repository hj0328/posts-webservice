package com.hj.springboot.config.auth;


import com.hj.springboot.web.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @SuppressWarnings("removal")    // frameOptions removal 경고 제거
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        // h2 console 정상동작을 위해(POST) 경로에 CSRF 비활성화, 실무에서는 보안을 위해 차단필요
                        .ignoringRequestMatchers("/**"))
                .headers(headers -> headers
                        .frameOptions().disable())  // h2 console 사용하기 위해 차단안함. 실무에서는 보안을 위해 차단필요
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())    // USER 권한 가능
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutSuccessUrl("/")          // 로그아웃 후 리다이렉션 url 설정
                        .invalidateHttpSession(true)    // 세션 무효화
                        .deleteCookies("JSESSIONID"))
                .oauth2Login(oauth -> oauth             // OAuth 로그인 기능에 대한 설정 진입
                        .userInfoEndpoint(userInfo -> userInfo  // Access Token을 이용해 사용자 정보 요청
                                .userService(customOAuth2UserService)));    // 사용자 데이터 처리

        return http.build();
    }
}
