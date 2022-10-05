package com.my.task.model.mapper;

import com.my.task.entity.Account;
import com.my.task.model.dto.AccountDTORequest;
import com.my.task.model.dto.AccountDTOResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapperAccount {

    public static AccountDTOResponse toAccountDTOResponse(Account account) {
        return AccountDTOResponse.builder()
                .username(account.getUsername())

                .build();

    }

    public static Account toAccount(AccountDTORequest accountDTORequest) {
        return Account.builder()
                .username(accountDTORequest.getUsername())
                .password(accountDTORequest.getPassword())
                .role(accountDTORequest.getRole())
                .build();
    }
}
