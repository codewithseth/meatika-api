package com.codewithseth.api.system;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.codewithseth.api.entity.Permission;
import com.codewithseth.api.entity.Role;
import com.codewithseth.api.repository.PermissionRepository;
import com.codewithseth.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@Profile("dev") // Only run this initializer in the 'dev' profile to avoid affecting production data
@RequiredArgsConstructor
public class DBDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create Super Admin Role and Permissions
        Role superAdmin = new Role();
        superAdmin.setUuid(UUID.randomUUID().toString());
        superAdmin.setAlias("super_admin");
        superAdmin.setNameEn("Super Admin");
        superAdmin.setNameKm("អ្នកគ្រប់គ្រងជាន់ខ្ពស់");
        superAdmin.setDescriptionEn("Super admin with all permissions");
        superAdmin.setDescriptionKm("អ្នកគ្រប់គ្រងជាន់ខ្ពស់ដែលមានសិទ្ធិទាំងអស់");

        Permission createUser = new Permission();
        createUser.setUuid(UUID.randomUUID().toString());
        createUser.setAlias("user:create");
        createUser.setNameEn("Create User");
        createUser.setNameKm("បង្កើតអ្នកប្រើប្រាស់");
        createUser.setGroupNameEn("User Management");
        createUser.setGroupNameKm("ការគ្រប់គ្រងអ្នកប្រើប្រាស់");
    
        Permission readUser = new Permission();
        readUser.setUuid(UUID.randomUUID().toString());
        readUser.setAlias("user:read");
        readUser.setNameEn("Read User");
        readUser.setNameKm("មើលអ្នកប្រើប្រាស់");
        readUser.setGroupNameEn("User Management");
        readUser.setGroupNameKm("ការគ្រប់គ្រងអ្នកប្រើប្រាស់");

        Permission updateUser = new Permission();
        updateUser.setUuid(UUID.randomUUID().toString());
        updateUser.setAlias("user:update");
        updateUser.setNameEn("Update User");
        updateUser.setNameKm("កែប្រែអ្នកប្រើប្រាស់");
        updateUser.setGroupNameEn("User Management");
        updateUser.setGroupNameKm("ការគ្រប់គ្រងអ្នកប្រើប្រាស់");

        Permission deleteUser = new Permission();
        deleteUser.setUuid(UUID.randomUUID().toString());
        deleteUser.setAlias("user:delete");
        deleteUser.setNameEn("Delete User");
        deleteUser.setNameKm("លុបអ្នកប្រើប្រាស់");
        deleteUser.setGroupNameEn("User Management");
        deleteUser.setGroupNameKm("ការគ្រប់គ្រងអ្នកប្រើប្រាស់");

        permissionRepository.saveAll(Set.of(createUser, readUser, updateUser, deleteUser));
        
        superAdmin.setPermissions(new LinkedHashSet<>(Set.of(createUser, readUser, updateUser, deleteUser)));
        roleRepository.save(superAdmin);
    }

}
