package com.backendapi.repo;

public interface RoleJpaRepository extends JpaRepository<UserEntity, Long> {
    RoleEntity findByUsername(String username);
}