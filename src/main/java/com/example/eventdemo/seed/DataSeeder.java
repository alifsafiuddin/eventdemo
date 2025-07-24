package com.example.eventdemo.seed;

import com.example.eventdemo.model.AppUser;
import com.example.eventdemo.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (!userRepository.findByUsername("admin").isPresent()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // hashed password
            admin.setRole("ROLE_ADMIN");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@example.com");
            admin.setPhoneNumber("0123456789");
            admin.setBirthDate(LocalDate.of(1990, 1, 1));

            userRepository.save(admin);
            System.out.println("✅ Admin user seeded.");
        }
        if (!userRepository.findByUsername("user").isPresent()) {
            AppUser user01 = new AppUser();
            user01.setUsername("user");
            user01.setPassword(passwordEncoder.encode("user123")); // hashed password
            user01.setRole("ROLE_USER");
            user01.setFirstName("Basic");
            user01.setLastName("User");
            user01.setEmail("user@example.com");
            user01.setPhoneNumber("0123456789");
            user01.setBirthDate(LocalDate.of(2001, 4, 30));

            userRepository.save(user01);
            System.out.println("✅ User seeded.");
        }
    }
}
