// package: com.scm.backend.config

package com.scm.backend.config;

import com.scm.backend.entity.Role;
import com.scm.backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleSeederConfig {

    @Bean
    public CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role("ROLE_USER", "User role with limited access"));
                System.out.println("Seeded ROLE_USER");
            }

            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN", "Administrator role with full access"));
                System.out.println("Seeded ROLE_ADMIN");
            }
        };
    }
}
