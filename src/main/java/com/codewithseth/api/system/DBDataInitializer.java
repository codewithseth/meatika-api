package com.codewithseth.api.system;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.codewithseth.api.dto.enums.AccountType;
import com.codewithseth.api.dto.enums.BooleanFlag;
import com.codewithseth.api.dto.enums.Gender;
import com.codewithseth.api.entity.Account;
import com.codewithseth.api.entity.Permission;
import com.codewithseth.api.entity.Role;
import com.codewithseth.api.entity.User;
import com.codewithseth.api.repository.AccountRepository;
import com.codewithseth.api.repository.PermissionRepository;
import com.codewithseth.api.repository.RoleRepository;
import com.codewithseth.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@Profile("dev") // Only run this initializer in the 'dev' profile to avoid affecting production data
@RequiredArgsConstructor
public class DBDataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create Root Account
        Account rootAccount = new Account();
        rootAccount.setUuid(UUID.randomUUID().toString());
        rootAccount.setUsername("root");
        rootAccount.setPassword("rootpassword");
        rootAccount.setType(AccountType.KH_USER);
        rootAccount.setIsActive(BooleanFlag.T);
        accountRepository.save(rootAccount);

        // Create Root Role
        Role rootRole = new Role();
        rootRole.setUuid(UUID.randomUUID().toString());
        rootRole.setAlias("root_user");
        rootRole.setNameEn("Root User");
        rootRole.setNameKm("អ្នកប្រើប្រាស់សិទ្ធិអធិបតី");
        rootRole.setDescriptionEn("Root user with all permissions");
        rootRole.setDescriptionKm("អ្នកប្រើប្រាស់សិទ្ធិអធិបតីដែលមានសិទ្ធិទាំងអស់");

        // Create Root Permissions
        Permission rootPermission = new Permission();
        rootPermission.setUuid(UUID.randomUUID().toString());
        rootPermission.setAlias("root_user");
        rootPermission.setNameEn("Root User");
        rootPermission.setNameKm("អ្នកប្រើប្រាស់សិទ្ធិអធិបតី");
        rootPermission.setGroupNameEn("User");
        rootPermission.setGroupNameKm("អ្នកប្រើប្រាស់");
        permissionRepository.save(rootPermission);
 
        rootRole.setPermissions(new LinkedHashSet<>(Set.of(rootPermission)));
        roleRepository.save(rootRole);

        // Create Root User
        User rootUser = new User();
        rootUser.setUuid(UUID.randomUUID().toString());
        rootUser.setLastNameEn("Root");
        rootUser.setLastNameKm("សិទ្ធិអធិបតី");
        rootUser.setFirstNameEn("User");
        rootUser.setFirstNameKm("អ្នកប្រើប្រាស់");
        rootUser.setGender(Gender.MALE);
        rootUser.setDob(LocalDate.now());
        rootUser.setAccount(rootAccount);
        rootUser.setRole(rootRole);
        userRepository.save(rootUser);

        // Create Super Admin Role and Permissions
        /* Role superAdmin = new Role();
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
        roleRepository.save(superAdmin); */
    }

}
