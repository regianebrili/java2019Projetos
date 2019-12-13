package com.rbmf.mongodb.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.rbmf.mongodb.domain.Post;
import com.rbmf.mongodb.domain.User;
import com.rbmf.mongodb.dto.AuthorDTO;
import com.rbmf.mongodb.repository.PostRepository;
import com.rbmf.mongodb.repository.UserRepository;

@Configuration		// para o spring entender que é uma configuração
public class Instantiation implements CommandLineRunner {		// implementa a classe CommandLineRunner

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... arg0) throws Exception {		// implementação da interface CommandLineRunner
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();		// limpa a coleção no MongoDB
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));		// salva os objetos na coleção users
		// precisa salvar os usuários primeiro para que possam ser copiados para o AuthorDTO
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
	}
}
