package com.rbmf.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbmf.spring.entities.User;
import com.rbmf.spring.repositories.UserRepository;

//  @Component   // registra a classe como um componente do spring e poderá ser injetado automaticamente com o @Autowired
//  @Repository  // registra um repositório
@Service         // registra um serviço
public class UserService {
	
	@Autowired
	private UserRepository repository;
	// como o UserRepository já está herdando do JpaRepository, então não precisa colocar a notação de @Repository lá no UserRepository
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.get();
	}
	
	// salvar o usuário no banco
	public User insert(User obj) {
		return repository.save(obj);
	}

}
