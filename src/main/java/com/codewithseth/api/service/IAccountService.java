package com.codewithseth.api.service;

import java.util.List;

import com.codewithseth.api.dto.account.AccountDto;
import com.codewithseth.api.dto.account.AccountReqDto;

public interface IAccountService {
    List<AccountDto> getAllAccounts();
    AccountDto getAccountById(Integer id);
    void createAccount(AccountReqDto accountReqDto);
    void updateAccount(Integer id, AccountReqDto accountReqDto);
    void deleteAccount(Integer id);
}
