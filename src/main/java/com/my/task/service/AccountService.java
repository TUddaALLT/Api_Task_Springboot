package com.my.task.service;

import org.springframework.http.ResponseEntity;

import com.my.task.model.ResponseObject;
import com.my.task.model.dto.AccountDTORequest;

public interface AccountService {

    public ResponseEntity<ResponseObject> register(AccountDTORequest account);

    public ResponseEntity<ResponseObject> login(AccountDTORequest account);

    public ResponseEntity<ResponseObject> updateAccount(String token);

}
