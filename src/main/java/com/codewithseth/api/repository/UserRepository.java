package com.codewithseth.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithseth.api.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
