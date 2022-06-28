package uz.sudev.rolespermissions.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {
    @Bean
    AuditorAware<Long> auditorAware() {
        return new SpringSecurityAuditAwareImpl();
    }
}
