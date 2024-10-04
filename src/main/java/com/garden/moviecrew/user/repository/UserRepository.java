package com.garden.moviecrew.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
