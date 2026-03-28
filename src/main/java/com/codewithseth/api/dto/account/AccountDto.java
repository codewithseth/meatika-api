package com.codewithseth.api.dto.account;

public record AccountDto (
    Integer id,
    String uuid,
    String username,
    String type,
    String isActive,
    String twoFaEnabled
) {}
