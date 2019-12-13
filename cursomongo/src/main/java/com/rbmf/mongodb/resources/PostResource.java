package com.rbmf.mongodb.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rbmf.mongodb.domain.Post;
import com.rbmf.mongodb.resources.util.URL;
import com.rbmf.mongodb.services.PostService;

@RestController		// informa que essa classe é um request
@RequestMapping(value="/posts")		// caminho do end point (geralmente o nome do recurso é usado no plural)
public class PostResource {
	
	@Autowired
	private PostService service;		// injetar o serviço, da mesma forma que foi colocado o resource no serviço

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Post> findById(@PathVariable String id) {		//@PathVariable usada para que esse id bata com o id do caminho
		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/titlesearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}
}
