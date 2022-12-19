package com.my.task.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.my.task.model.ResponseObject;
import com.my.task.model.dto.TaskDTOCreate;

public interface TaskService {

    public ResponseEntity<ResponseObject> createTask(HttpServletRequest request, TaskDTOCreate taskDTOCreate);

    public ResponseEntity<ResponseObject> getAllTask(HttpServletRequest request);

    public ResponseEntity<ResponseObject> deleteTask(HttpServletRequest request, int id);

}
