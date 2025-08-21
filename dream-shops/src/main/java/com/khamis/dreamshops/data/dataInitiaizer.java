package com.khamis.dreamshops.data;

import com.khamis.dreamshops.model.Role;
import com.khamis.dreamshops.model.User;
import com.khamis.dreamshops.repository.roleRepository.roleRepository;
import com.khamis.dreamshops.repository.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
@Transactional
@Component
@RequiredArgsConstructor
public class dataInitiaizer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final roleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String>defaultRoles=Set.of("ROLE_ADMIN","ROLE_USER");
        createDefualtUserIfNoExists();
        createDefaultRoleNotExists(defaultRoles);
       createDefualtAdminIfNoExists();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
    public void createDefualtUserIfNoExists(){
        Role userRole=roleRepository.findByName("ROLE_USER");
        for (int i =1;i<=5;i++){
            String defaultEmail="user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user=new User();
            user.setFirstName("The User");
            user.setLastName("User"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user"+i+"created successfully");
        }

    }
    public void createDefualtAdminIfNoExists(){
        Role adminRole=roleRepository.findByName("ROLE_ADMIN");
        for (int i =1;i<=2;i++){
            String defaultEmail="admin"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user=new User();
            user.setFirstName("The Admin");
            user.setLastName("Admin"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default vet Admin"+i+"created successfully");
        }

    }

    private void createDefaultRoleNotExists(Set<String> roles) {
        roles.stream()
                .filter(roleName -> roleRepository.findByName(roleName) == null) // role not found
                .map(Role::new) // create new Role(name)
                .forEach(roleRepository::save);
    }

}
