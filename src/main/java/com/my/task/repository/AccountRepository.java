package com.my.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.my.task.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    public Optional<Account> findByUsername(String username);

}
