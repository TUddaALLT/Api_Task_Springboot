package com.my.task.repository;

import org.springframework.stereotype.Repository;

import com.my.task.entity.GroupTask;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface GroupTaskRepository extends JpaRepository<GroupTask, Integer> {

}
