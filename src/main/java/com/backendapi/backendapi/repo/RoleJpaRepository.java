package com.backendapi.backendapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendapi.backendapi.model.entity.RoleEntity;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}