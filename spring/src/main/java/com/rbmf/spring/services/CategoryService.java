package com.rbmf.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbmf.spring.entities.Category;
import com.rbmf.spring.repositories.CategoryRepository;

//  @Component   // registra a classe como um componente do spring e poderá ser injetado automaticamente com o @Autowired
//  @Repository  // registra um repositório
@Service         // registra um serviço
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	// como o CategoryRepository já está herdando do JpaRepository, então não precisa colocar a notação de @Repository lá no CategoryRepository
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		return obj.get();
	}

}
