package com.my.task.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.my.task.entity.Account;
import com.my.task.entity.GroupTask;
import com.my.task.model.ResponseObject;
import com.my.task.model.dto.GroupTaskDTOCreate;
import com.my.task.repository.AccountRepository;
import com.my.task.service.GroupTaskService;
import com.my.task.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupTaskServiceImpl implements GroupTaskService {
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<ResponseObject> createGroupTask(HttpServletRequest request,
            GroupTaskDTOCreate grouptaskDTOCreate) {
        String token = request.getHeader("Authorization");
        token = token.substring(6);
        Account acc = jwtTokenUtil.getAccountLogin(token);
        GroupTask grouptask = GroupTask.builder().groupName(grouptaskDTOCreate.getGroupName()).build();
        grouptask.setAccountMakeGroup(acc);
        Set<GroupTask> grouptasks = new HashSet<>();
        grouptasks.add(grouptask);
        acc.setGroup_maked(grouptasks);
        acc = accountRepository.save(acc);
        return ResponseEntity.ok()
                .body(ResponseObject.builder().status("500").message("oke").data(grouptask.toString())
                        .build());
    }

}
