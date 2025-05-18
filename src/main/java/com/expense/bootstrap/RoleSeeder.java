package com.expense.bootstrap;

import com.expense.entities.Role;
import com.expense.entities.RoleEnum;
import com.expense.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }

    private void loadRoles() {
        List<RoleEnum> rolesName = List.of(RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN);
        Map<RoleEnum, String> rolesDescription = new HashMap<>();
        rolesDescription.put(RoleEnum.USER, "Default user role");
        rolesDescription.put(RoleEnum.ADMIN, "Administrator role");
        rolesDescription.put(RoleEnum.SUPER_ADMIN, "Super Administrator role");

        rolesName.forEach(roleName -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);


            optionalRole.ifPresentOrElse(
                    role -> {
                        System.out.println("Role Found: "+role.getName());
                    },
                    () -> {
                        Role roleEntity = new Role();
                        roleEntity.setName(roleName);
                        roleEntity.setDescription(rolesDescription.get(roleName));

                        roleRepository.save(roleEntity);
                    });
        });

    }
}
