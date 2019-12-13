package com.rbmf.mongodb.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rbmf.mongodb.domain.User;
import com.rbmf.mongodb.dto.UserDTO;
import com.rbmf.mongodb.services.UserService;

@RestController		// informa que essa classe é um request
@RequestMapping(value="/users")		// caminho do end point (geralmente o nome do recurso é usado no plural)
public class UserResource {
	
	@Autowired
	private UserService service;		// injetar o serviço, da mesma forma que foi colocado o resource no serviço

	@RequestMapping(method = RequestMethod.GET)			// indicar que esse método é um end point desse recurso
	// @GetMapping			 							tb pode ser usado, fica a critério
	public ResponseEntity<List<UserDTO>> findAll() {	// o ResponseEntity encapsula toda a estrutura necessária para retornar respostas http com cabeçalhos, erros, ...
		List<User> list = service.findAll();			// busca no banco os usuário e guarda na lista
		
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		// converte uma lista User para UserDTO / .stream (coleção compatível à lambda)
		// .map (pega cada objeto x da lista original, para cada objeto retorna um new UserDTO passando o x como argumento) 
		// .collect volta o strem para uma lista
		
		return ResponseEntity.ok().body(listDto);			// ok é um método que vai instanciar o ResponseEntity com o retorno de sucesso
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {		//@PathVariable usada para que esse id bata com o id do caminho
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));		//converte o obj para UserDTO
	}
}
