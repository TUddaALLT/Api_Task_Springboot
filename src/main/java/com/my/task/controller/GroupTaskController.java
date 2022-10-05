package com.my.task.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.my.task.model.ResponseObject;
import com.my.task.model.dto.GroupTaskDTOCreate;
import com.my.task.service.GroupTaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GroupTaskController {
    private final GroupTaskService groupTaskService;

    @PostMapping("/grouptask")
    public ResponseEntity<ResponseObject> createGroupTask(HttpServletRequest request,
            @RequestBody GroupTaskDTOCreate grouptaskDTOCreate) {

        return groupTaskService.createGroupTask(request, grouptaskDTOCreate);
    }
}
