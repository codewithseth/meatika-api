package com.codewithseth.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithseth.api.dto.account.AccountDto;
import com.codewithseth.api.dto.account.AccountReqDto;
import com.codewithseth.api.service.IAccountService;
import com.codewithseth.api.system.ResultResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService iAccountService;

    @GetMapping
    public ResponseEntity<ResultResponse> findAllAccounts() {
        List<AccountDto> accounts = iAccountService.getAllAccounts();
        ResultResponse response = new ResultResponse(true, 200, "Accounts retrieved successfully", accounts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findAccountById(@PathVariable Integer id) {
        AccountDto account = iAccountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<Void> addAccount(@Valid @RequestBody AccountReqDto accountReqDto) {
        iAccountService.createAccount(accountReqDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editAccount(
        @PathVariable Integer id,
        @Valid @RequestBody AccountReqDto accountReqDto
    ) {
        iAccountService.updateAccount(id, accountReqDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAccount(@PathVariable Integer id) {
        iAccountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

}
