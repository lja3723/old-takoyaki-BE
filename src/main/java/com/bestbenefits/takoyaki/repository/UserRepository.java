package com.bestbenefits.takoyaki.repository;

import com.bestbenefits.takoyaki.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndSocial(String Email, int social);
    Optional<User> findUserByNickname(String nickname);
}