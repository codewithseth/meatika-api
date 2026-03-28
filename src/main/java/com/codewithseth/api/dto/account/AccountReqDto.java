package com.codewithseth.api.dto.account;

import com.codewithseth.api.dto.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountReqDto (
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    String username,
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 250, message = "Password must be between 8 and 250 characters")
    String password,
    
    @NotNull(message = "Account type is required")
    AccountType type

) {}
