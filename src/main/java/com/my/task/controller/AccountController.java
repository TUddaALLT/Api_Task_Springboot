package com.my.task.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.my.task.model.ResponseObject;
import com.my.task.model.dto.AccountDTORequest;
import com.my.task.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody AccountDTORequest account) {

        return accountService.login(account);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody AccountDTORequest account) {
        return accountService.register(account);
    }

    @PostMapping("/account/update")
    public ResponseEntity<ResponseObject> updateAccount(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(6);
        return accountService.updateAccount(token);
    }
}
