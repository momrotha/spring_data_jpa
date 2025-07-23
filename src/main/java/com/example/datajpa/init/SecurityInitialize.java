package com.example.datajpa.init;

import com.example.datajpa.domain.Role;
import com.example.datajpa.domain.User;
import com.example.datajpa.repository.RoleRepository;
import com.example.datajpa.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SecurityInitialize {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        if (roleRepository.count() == 0) {
            // Save roles
            Role roleUser = new Role();
            roleUser.setName("USER");

            Role roleAdmin = new Role();
            roleAdmin.setName("ADMIN");

            Role roleStaff = new Role();
            roleStaff.setName("STAFF");

            Role roleCustomer = new Role();
            roleCustomer.setName("CUSTOMER");

            roleRepository.saveAll(List.of(roleUser, roleAdmin, roleStaff, roleCustomer));


            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123$"));
            admin.setIsEnabled(true);
            admin.setRoles(Set.of(roleUser, roleAdmin));

            User staff = new User();
            staff.setUsername("staff");
            staff.setPassword(passwordEncoder.encode("admin123$"));
            staff.setIsEnabled(true);
            staff.setRoles(Set.of(roleUser, roleStaff));

            User customer = new User();
            customer.setUsername("customer");
            customer.setPassword(passwordEncoder.encode("admin123$"));
            customer.setIsEnabled(true);
            customer.setRoles(Set.of(roleUser, roleCustomer));

            userRepository.saveAll(List.of(admin, staff, customer));
        }


    }

}
