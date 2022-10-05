package com.my.task.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountDTOResponse {
    private String username;
    private String token;
}
