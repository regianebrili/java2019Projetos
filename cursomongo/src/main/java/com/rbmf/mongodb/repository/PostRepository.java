package com.rbmf.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.rbmf.mongodb.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {		// herdar da MongoRepository<tipo da classe de dominio, tipo do id da classe>
// no MongoRepository já está embudido todas as operações necessárias.. salvar, apagar, consultar, alterar...
	
	@Query("{ 'title' : { $regex: ?0, $options: 'i' } }")		// dentro do query coloca o json de consulta do MongoDB
		 // { <field> : { $regex: 'expressão regular, no caso ?0 é o primeiro parâmetro passado no método', $options: '<options>'}   options: i case insensitivity
	List<Post> searchTitle(String text);
	
	List<Post> findByTitleContainingIgnoreCase(String text);		// findByCAMPO_DA_TABELAContaining (o spring data já monta a consulta automaticamente) IgnoreCase para ignorar maículas e minúsculas
}
