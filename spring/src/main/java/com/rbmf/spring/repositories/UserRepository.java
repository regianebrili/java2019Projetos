package com.rbmf.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbmf.spring.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	

}
