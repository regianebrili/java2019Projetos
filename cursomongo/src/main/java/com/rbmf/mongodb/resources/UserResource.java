package com.rbmf.mongodb.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rbmf.mongodb.domain.User;
import com.rbmf.mongodb.services.UserService;

@RestController		// informa que essa classe é um request
@RequestMapping(value="/users")		// caminho do end point (geralmente o nome do recurso é usado no plural)
public class UserResource {
	
	@Autowired
	private UserService service;		// injetar o serviço, da mesma forma que foi colocado o resource no serviço

	@RequestMapping(method = RequestMethod.GET)			// indicar que esse método é um end point desse recurso
	// @GetMapping			 							tb pode ser usado, fica a critério
	public ResponseEntity<List<User>> findAll() {		// o ResponseEntity encapsula toda a estrutura necessária para retornar respostas http com cabeçalhos, erros, ...
		List<User> list = service.findAll();			// busca no banco os usuário e guarda na lista
		return ResponseEntity.ok().body(list);			// ok é um método que vai instanciar o ResponseEntity com o retorno de sucesso
	}
}
