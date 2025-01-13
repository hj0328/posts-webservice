package com.hj.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // 애플리케이션 전역에서 JPA Auditing 활성화
public class JpaConfig {
}
