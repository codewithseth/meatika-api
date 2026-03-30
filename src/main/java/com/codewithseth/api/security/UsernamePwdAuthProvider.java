package com.codewithseth.api.security;

import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.codewithseth.api.dto.auth.LoginReqDto;
import com.codewithseth.api.dto.enums.AccountType;
import com.codewithseth.api.entity.Account;
import com.codewithseth.api.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsernamePwdAuthProvider implements AuthenticationProvider {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication auth) throws AuthenticationException {
        LoginReqDto loginReqDto = (LoginReqDto) auth.getPrincipal();
        AccountType type = loginReqDto.type();
        String username = loginReqDto.username();
        String password = auth.getCredentials().toString();

        // Check if account with the given username and type exists
        Account account = accountRepository.findByUsernameAndType(username, type).orElseThrow(
            () -> new UsernameNotFoundException(
                String.format("Resource '%s' not found with %s : '%s'", "Account", "username and type", username + " and " + type)
            )
        );

        // Check if account is active
        if (account.getIsActive().name().equals("F")) {
            throw new BadCredentialsException("Account is not active");
        }

        // Check if the provided password matches the stored password
        if (passwordEncoder.matches(password, account.getPassword())) {
            return new UsernamePasswordAuthenticationToken(account, null, Collections.emptyList());
        } else {
            throw new BadCredentialsException("Account username or password is incorrect");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
