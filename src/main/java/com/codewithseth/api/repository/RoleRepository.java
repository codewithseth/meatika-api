package com.codewithseth.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithseth.api.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {}
