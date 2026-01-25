package org.santiago.lms.app.config;

import org.santiago.lms.app.models.Role;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.repository.RoleRepository;
import org.santiago.lms.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(Role.RoleName.ROLE_ADMIN);
                        return roleRepository.save(role);
                    });

            User admin = new User();
            admin.setUsername("santi");
            admin.setEmail("santi@admin.com");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setEnabled(true);
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);

            System.out.println("Usuario admin creado: santi");
        }
    }
}
