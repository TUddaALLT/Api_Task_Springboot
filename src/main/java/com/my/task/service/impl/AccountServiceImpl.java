package com.my.task.service.impl;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.my.task.entity.Account;
import com.my.task.model.ResponseObject;
import com.my.task.model.dto.AccountDTORequest;
import com.my.task.model.dto.AccountDTOResponse;
import com.my.task.model.mapper.MapperAccount;
import com.my.task.repository.AccountRepository;
import com.my.task.service.AccountService;
import com.my.task.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AccountServiceImpl implements AccountService {
        private final AccountRepository accountRepository;
        private final JwtTokenUtil jwtTokenUtil;
        private final PasswordEncoder passwordEncoder;

        @Override
        public ResponseEntity<ResponseObject> register(AccountDTORequest accountDTORequest) {
                Account account = MapperAccount.toAccount(accountDTORequest);
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                Optional<Account> accountDB = accountRepository.findByUsername(account.getUsername());
                if (accountDB.isPresent()) {
                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("400").message("username is existed ")
                                                        .data(null)
                                                        .build());
                }
                accountRepository.save(account);

                AccountDTOResponse accountDTOResponse = AccountDTOResponse.builder()
                                .username(account.getUsername())
                                .token(jwtTokenUtil.generateToken(account, 24 * 60 * 60))
                                .build();
                return ResponseEntity.ok()
                                .body(ResponseObject.builder().status("500").message("register successfully")
                                                .data(accountDTOResponse)
                                                .build());
        }

        @Override
        public ResponseEntity<ResponseObject> login(AccountDTORequest account) {
                Optional<Account> accountDB = accountRepository.findByUsername(account.getUsername());
                if (accountDB.isPresent()) {
                        AccountDTOResponse accountDTOResponse = AccountDTOResponse.builder()
                                        .username(account.getUsername())
                                        .token(jwtTokenUtil.generateToken(accountDB.get(), 24 * 60 * 60))
                                        .build();

                        if (passwordEncoder.matches(account.getPassword(), accountDB.get().getPassword())) {
                                return ResponseEntity.ok()
                                                .body(ResponseObject.builder().status("500")
                                                                .message("login successfully")
                                                                .data(accountDTOResponse)
                                                                .build());
                        }
                }
                return ResponseEntity.ok()
                                .body(ResponseObject.builder().status("400")
                                                .message("login failed")
                                                .data(null)
                                                .build());
        }

        @Override
        public ResponseEntity<ResponseObject> updateAccount(String token) {

                Account acc = jwtTokenUtil.getAccountLogin(token);

                acc = accountRepository.save(acc);
                AccountDTOResponse response = MapperAccount.toAccountDTOResponse(acc);
                response.setToken(token);
                return ResponseEntity.ok()
                                .body(ResponseObject.builder().status("500")
                                                .message("update acc successfull")
                                                .data(response)
                                                .build());
        }

}
