package com.rbmf.mongodb.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.rbmf.mongodb.domain.User;
import com.rbmf.mongodb.repository.UserRepository;

@Configuration		// para o spring entender que é uma configuração
public class Instantiation implements CommandLineRunner {		// implementa a classe CommandLineRunner

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... arg0) throws Exception {		// implementação da interface CommandLineRunner
		
		userRepository.deleteAll();		// limpa a coleção no MongoDB
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));		// salva os objetos na coleção users
	}
}
