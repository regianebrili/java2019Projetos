package com.rbmf.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rbmf.spring.entities.User;
import com.rbmf.spring.repositories.UserRepository;
import com.rbmf.spring.services.exceptions.DatabaseException;
import com.rbmf.spring.services.exceptions.ResourceNotFoundException;

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
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	// salvar o usuário no banco
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	// apagar o usuário no banco
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	// atualizar o usuário
	public User update(Long id, User obj) {
		User entity = repository.getOne(id);
		updateData(entity, obj);   // chama a funçao que vai fazer a atualização de cada campo
		return repository.save(entity);
	}
	
	// método que atualiza cada um dos dados com o obj enviado
	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
		
	}

}
