package com.my.task.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.my.task.model.ResponseObject;
import com.my.task.model.dto.GroupTaskDTOCreate;

public interface GroupTaskService {

    public ResponseEntity<ResponseObject> createGroupTask(HttpServletRequest request,
            GroupTaskDTOCreate grouptaskDTOCreate);

}
