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
	
	public void delete(String id) {
		findById(id);	// utiliza o método para confirmar a existência do id, se não existir, já retorna o erro
		repo.deleteById(id);
	}
	
	public User update(User obj) {
		User newObj = findById(obj.getId());	// busca o obj que foi digitado pelo usuário no banco de dados
		updateData(newObj, obj);				// responsável por copiar os dados digitados no banco de dados
		return repo.save(newObj);
	}
	
	public void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	
	// é melhor colocar no UserService e não no UserDTO, pq dependendo da situação, para instanciar um user, pode ser necessário acessar o banco de dados
	// e quem tem a dependência para o acesso é o UserService, sendo assim, prevendo um futuro, é melhor colocar aqui.
	public User fromDTO(UserDTO objDto) {		// vai pegar o DTO e instanciar em um obj (ao contrário da classe DTO)
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}

