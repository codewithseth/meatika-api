package com.codewithseth.api.entity;

import com.codewithseth.api.dto.enums.AccountType;
import com.codewithseth.api.dto.enums.BooleanFlag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String uuid;

    @Column(length = 50)
    private String username;

    @Column(length = 250)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active", nullable = false, insertable = false)
    private BooleanFlag isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "two_fa_enabled", nullable = false, insertable = false)
    private BooleanFlag twoFaEnabled;

    @Column(name = "two_fa_secret", length = 250)
    private String twoFaSecret;

    @Column(name = "two_fa_secret_temp", length = 250)
    private String twoFaSecretTemp;

}
