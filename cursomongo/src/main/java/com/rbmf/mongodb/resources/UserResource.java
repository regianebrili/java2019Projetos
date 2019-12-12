package com.rbmf.mongodb.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rbmf.mongodb.domain.User;

@RestController		// informa que essa classe é um request
@RequestMapping(value="/users")		// caminho do end point (geralmente o nome do recurso é usado no plural)
public class UserResource {

	@RequestMapping(method = RequestMethod.GET)			// indicar que esse método é um end point desse recurso
	// @GetMapping			 tb pode ser usado, fica a critério
	public ResponseEntity<List<User>> findAll() {		// o ResponseEntity encapsula toda a estrutura necessária para retornar respostas http com cabeçalhos, erros, ...
		User maria = new User("1", "Maria Brown", "maria@gmail.com");
		User alex = new User("2", "Alex Green", "alex@gmail.com");
		List<User> list = new ArrayList<>();
		list.addAll(Arrays.asList(maria, alex));
		return ResponseEntity.ok().body(list);		// ok é um método que vai instanciar o ResponseEntity com o retorno de sucesso
	}
}
