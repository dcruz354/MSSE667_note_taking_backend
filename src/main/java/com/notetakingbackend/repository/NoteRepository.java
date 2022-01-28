/**
 * 
 */
package com.notetakingbackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.notetakingbackend.domain.Note;

/**
 * @author Dcruz
 *
 */
@RepositoryRestResource(collectionResourceRel = "notes", path = "notes")
public interface NoteRepository extends MongoRepository<Note, String> {
	//http://localhost:8080/notes/search/findByCategory?name=Test
	List<Note> findByCategory(String name);
	
	//http://localhost:8080/notes/search/findByTitle?name=Note 
	Note findByTitle(String name);
}
