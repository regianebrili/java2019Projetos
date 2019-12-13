package com.rbmf.mongodb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbmf.mongodb.domain.User;
import com.rbmf.mongodb.dto.UserDTO;
import com.rbmf.mongodb.repository.UserRepository;
import com.rbmf.mongodb.services.exception.ObjectNotFoundException;

@Service		// informar que é um serviço a ser utilizado em outras classes
public class UserService {

	@Autowired							// mecanismo de instanciação automática do Spring
	private UserRepository repo;		// instancia automaticamente o repositório
	
	public List<User> findAll() {		// método responsável por retornar todos os usuários do banco
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	// é melhor colocar no UserService e não no UserDTO, pq dependendo da situação, para instanciar um user, pode ser necessário acessar o banco de dados
	// e quem tem a dependência para o acesso é o UserService, sendo assim, prevendo um futuro, é melhor colocar aqui.
	public User fromDTO(UserDTO objDto) {		// vai pegar o DTO e instanciar em um obj (ao contrário da classe DTO)
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}

