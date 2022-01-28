/**
 * 
 */
package com.notetakingbackend.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notetakingbackend.domain.Note;
import com.notetakingbackend.exception.ResourceNotFoundException;
import com.notetakingbackend.repository.NoteRepository;

/**
 * @author Dcruz
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/notes/v1")
public class NotesController {
	@Autowired
	NoteRepository noteRepository;
	
	// Retrieve all Notes
	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}
	
	// Retrieve note by id
	@GetMapping("notes/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable(value = "id") String id)throws ResourceNotFoundException {
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note not found for this id :: " + id));
		return ResponseEntity.ok().body(note);
	}
	
	//Create a Note
	@PostMapping("/notes/create")
	public Note createNote(@RequestBody Note note) {
		note.setLocalDate(LocalDate.now());
		return noteRepository.save(note);
	}
	
	// Update a note by id
	@PutMapping("/notes/update/{id}")
	public ResponseEntity <Note> updateNote(@PathVariable(value = "id") String id, @RequestBody Note noteDetails) throws ResourceNotFoundException {
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Listing not found for this id :: " + id));
		
		note.setCategory(noteDetails.getCategory());
		note.setDescription(noteDetails.getDescription());
		note.setLocalDate(LocalDate.now());
		note.setTitle(noteDetails.getTitle());
		
		final Note updatedNote = noteRepository.save(note);
		return ResponseEntity.ok().body(updatedNote);
		
	}
	
	// Delete a note by id
	@DeleteMapping("/notes/delete/{id}")
	public Map < String, Boolean > deleteNote(@PathVariable(value = "id") String id) throws ResourceNotFoundException {
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Listing not found for this id :: " + id));
		
		noteRepository.delete(note);
		Map< String, Boolean> response = new HashMap <> ();
		response.put("deleted", Boolean.TRUE);
		return response;
		
	}

}
