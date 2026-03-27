package com.codewithseth.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithseth.api.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {}
