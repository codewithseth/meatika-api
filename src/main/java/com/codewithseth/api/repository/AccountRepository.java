package com.codewithseth.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithseth.api.dto.enums.AccountType;
import com.codewithseth.api.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByUsernameAndType(String username, AccountType type);
}
