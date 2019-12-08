package com.rbmf.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbmf.spring.entities.Product;
import com.rbmf.spring.repositories.ProductRepository;

//  @Component   // registra a classe como um componente do spring e poderá ser injetado automaticamente com o @Autowired
//  @Repository  // registra um repositório
@Service         // registra um serviço
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	// como o ProductRepository já está herdando do JpaRepository, então não precisa colocar a notação de @Repository lá no ProductRepository
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.get();
	}

}
