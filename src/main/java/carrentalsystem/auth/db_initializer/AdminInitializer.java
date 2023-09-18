package carrentalsystem.auth.db_initializer;

import carrentalsystem.entities.Role;
import carrentalsystem.entities.User;
import carrentalsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        initializeRolesAndAdminUser();
    }

    private void initializeRolesAndAdminUser() {
        if (userRepository.findAllByRole(Role.ADMIN).isEmpty()) {
            User adminUser = new User();
            adminUser.setFirstName("admin");
            String ADMIN_EMAIL = "admin@admin.com";
            adminUser.setEmail(ADMIN_EMAIL);
            adminUser.setPassword(passwordEncoder.encode("admin_password"));
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);
        }
    }
}
