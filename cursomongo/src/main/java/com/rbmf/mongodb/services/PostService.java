package com.rbmf.mongodb.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbmf.mongodb.domain.Post;
import com.rbmf.mongodb.repository.PostRepository;
import com.rbmf.mongodb.services.exception.ObjectNotFoundException;

@Service		// informar que é um serviço a ser utilizado em outras classes
public class PostService {

	@Autowired							// mecanismo de instanciação automática do Spring
	private PostRepository repo;		// instancia automaticamente o repositório
	
	public Post findById(String id) {
		Optional<Post> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public List<Post> findByTitle(String text) {
		//return repo.findByTitleContainingIgnoreCase(text);
		return repo.searchTitle(text);		// consulta de MongoDB em Json
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);  // acrescenta um dia para a data máxima para poder pegar o dia todo (24horas)
		return repo.fullSearch(text, minDate, maxDate);
	}
}

