package com.rbmf.mongodb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbmf.mongodb.domain.User;
import com.rbmf.mongodb.repository.UserRepository;

@Service		// informar que é um serviço a ser utilizado em outras classes
public class UserService {

	@Autowired							// mecanismo de instanciação automática do Spring
	private UserRepository repo;		// instancia automaticamente o repositório
	
	public List<User> findAll() {		// método responsável por retornar todos os usuários do banco
		return repo.findAll();
	}
}
