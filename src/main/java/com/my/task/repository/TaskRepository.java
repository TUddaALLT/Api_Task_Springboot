package com.my.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.task.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    // Task findAllByUsername(String string);

}
