package com.rbmf.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbmf.spring.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	// como o userRepository já está herdando do JpaRepository, então não precisa colocar a notação de @Repository
	
}
