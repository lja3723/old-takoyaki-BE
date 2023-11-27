package com.bestbenefits.takoyaki.repository;

import com.bestbenefits.takoyaki.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
}