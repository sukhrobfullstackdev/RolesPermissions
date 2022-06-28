package uz.sudev.rolespermissions.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.sudev.rolespermissions.entity.User;

import java.util.Optional;


public class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) { // authentication.isAuthenticated()- authenticate bo'ganmi shuni tekshirvoti
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getId());
        } else {
            return Optional.empty();
        }
    }
}
