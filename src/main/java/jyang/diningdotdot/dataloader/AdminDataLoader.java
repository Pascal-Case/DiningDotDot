package jyang.diningdotdot.dataloader;

import jyang.diningdotdot.entity.user.Admin;
import jyang.diningdotdot.entity.user.Role;
import jyang.diningdotdot.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDataLoader implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Admin admin = Admin.builder()
                .username("admin@admin.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ROLE_ADMIN)
                .build();

        adminRepository.findByUsername(admin.getUsername())
                .orElseGet(() -> adminRepository.save(admin));
    }
}
