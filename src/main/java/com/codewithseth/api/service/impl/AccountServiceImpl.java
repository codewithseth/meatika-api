package com.codewithseth.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.codewithseth.api.dto.account.AccountDto;
import com.codewithseth.api.dto.account.AccountReqDto;
import com.codewithseth.api.entity.Account;
import com.codewithseth.api.exception.ResourceNotFoundException;
import com.codewithseth.api.repository.AccountRepository;
import com.codewithseth.api.service.IAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountsDto = new ArrayList<AccountDto>();

        for (Account account : accounts) {
            AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getUuid(),
                account.getUsername(),
                account.getType().name(),
                account.getIsActive().name(),
                account.getTwoFaEnabled().name()
            );
            accountsDto.add(accountDto);
        }

        return accountsDto;
    }

    @Override
    public AccountDto getAccountById(Integer id) {
        Account account = getAccountEntityById(id);

        return new AccountDto(
            account.getId(),
            account.getUuid(),
            account.getUsername(),
            account.getType().name(),
            account.getIsActive().name(),
            account.getTwoFaEnabled().name()
        );
    }

    @Override
    public void createAccount(AccountReqDto accountReqDto) {
        Account account = new Account();
        account.setUuid(UUID.randomUUID().toString());
        account.setUsername(accountReqDto.username());
        account.setPassword(accountReqDto.password());
        account.setType(accountReqDto.type());

        accountRepository.save(account);
    }

    @Override
    public void updateAccount(Integer id, AccountReqDto accountReqDto) {
        Account account = getAccountEntityById(id);

        account.setUsername(accountReqDto.username());
        account.setPassword(accountReqDto.password());
        account.setType(accountReqDto.type());

        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Integer id) {
        Account account = getAccountEntityById(id);
        accountRepository.delete(account);
    }

    private Account getAccountEntityById(Integer id) {
        Account account = accountRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("ACCOUNT", "ID", id.toString())
        );
        return account;
    }

}
