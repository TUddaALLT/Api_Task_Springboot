package com.my.task.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.my.task.model.ResponseObject;
import com.my.task.model.dto.TaskDTOCreate;
import com.my.task.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor

public class TaskController {
    private final TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<ResponseObject> createTask(HttpServletRequest request,
            @RequestBody TaskDTOCreate taskDTOCreate) {

        return taskService.createTask(request, taskDTOCreate);
    }

    @GetMapping("/task")
    public ResponseEntity<ResponseObject> getAllTask(HttpServletRequest request) {
        System.out.println("haha");
        return taskService.getAllTask(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteTask(HttpServletRequest request,
            @PathVariable int id) {
        System.out.println("delete");
        return taskService.deleteTask(request, id);
    }
}
