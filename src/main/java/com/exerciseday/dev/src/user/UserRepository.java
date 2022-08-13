package com.exerciseday.dev.src.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exerciseday.dev.src.user.model.User;

public interface UserRepository<Stirng> extends JpaRepository<User, Integer> {

    Object findByUsernickname(Stirng nickname);

}
