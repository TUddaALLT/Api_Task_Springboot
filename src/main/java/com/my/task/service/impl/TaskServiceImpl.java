package com.my.task.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.my.task.entity.Account;
import com.my.task.entity.Task;
import com.my.task.model.ResponseObject;
import com.my.task.model.dto.AccountDTORequest;
import com.my.task.model.dto.TaskDTOCreate;
import com.my.task.repository.AccountRepository;
import com.my.task.service.TaskService;
import com.my.task.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class TaskServiceImpl implements TaskService {

        // private final TaskRepository taskRepository;
        private final AccountRepository accountRepository;
        private final JwtTokenUtil jwtTokenUtil;

        @Override
        public ResponseEntity<ResponseObject> createTask(HttpServletRequest request, TaskDTOCreate taskDTOCreate) {
                Task task = Task.builder()
                                .img(taskDTOCreate.getImg())
                                .describe(taskDTOCreate.getDescribe())
                                .status(taskDTOCreate.getStatus())
                                .groupID(taskDTOCreate.getGroupID())
                                .build();

                String token = request.getHeader("Authorization").substring(6);
                Account acc = jwtTokenUtil.getAccountLogin(token);
                if (acc == null) {
                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("404").message("failed").data(null)
                                                        .build());
                } else {
                        task.setAccount(acc);
                        Set<Task> tasks = new HashSet<>();
                        tasks.add(task);
                        acc.setTasks(tasks);
                        acc = accountRepository.save(acc);
                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("500").message("created")
                                                        .data(task.toString())
                                                        .build());
                }
        }

        @Override
        public ResponseEntity<ResponseObject> getAllTask(AccountDTORequest username) {

                Optional<Account> account = accountRepository.findByUsername(username.getUsername());
                if (account.isPresent()) {
                        Set<Task> tasks = account.get().getTasks();
                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("500").message("created")
                                                        .data(tasks.toString())
                                                        .build());
                }

                return ResponseEntity.ok()
                                .body(ResponseObject.builder().status("404").message("failed").data(null)
                                                .build());

        }

}
