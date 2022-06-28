package uz.sudev.rolespermissions.component;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.sudev.rolespermissions.entity.Role;
import uz.sudev.rolespermissions.entity.User;
import uz.sudev.rolespermissions.entity.enums.PermissionName;
import uz.sudev.rolespermissions.repository.AuthenticationRepository;
import uz.sudev.rolespermissions.repository.RoleRepository;
import uz.sudev.rolespermissions.utils.ProjectConstants;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    final AuthenticationRepository authenticationRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    public DataLoader(PasswordEncoder passwordEncoder,RoleRepository roleRepository, AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (ddlAuto.equals("create")) {
            PermissionName[] permissions = PermissionName.values();
            Role adminRole = roleRepository.save(new Role(ProjectConstants.ADMIN, List.of(permissions),"The system owner"));
            Role userRole = roleRepository.save(new Role(ProjectConstants.USER, List.of(PermissionName.ADD_COMMENT, PermissionName.EDIT_COMMENT, PermissionName.DELETE_MY_COMMENT),"The ordinary user"));
            authenticationRepository.save(new User("ADMIN", "ADMIN", "admin@gmail.com", passwordEncoder.encode("adminAdmin"), adminRole, true));
            authenticationRepository.save(new User("USER", "USER", "user@gmail.com", passwordEncoder.encode("userUser"), userRole, true));
        }
    }
}
