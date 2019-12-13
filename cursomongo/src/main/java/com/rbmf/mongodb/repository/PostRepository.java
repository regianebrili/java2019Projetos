package com.rbmf.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rbmf.mongodb.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {		// herdar da MongoRepository<tipo da classe de dominio, tipo do id da classe>
// no MongoRepository já está embudido todas as operações necessárias.. salvar, apagar, consultar, alterar...
	
	List<Post> findByTitleContainingIgnoreCase(String text);		// findByCAMPO_DA_TABELAContaining (o spring data já monta a consulta automaticamente) IgnoreCase para ignorar maículas e minúsculas
}
