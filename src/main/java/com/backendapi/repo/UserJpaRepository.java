package com.backendapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendapi.model.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}