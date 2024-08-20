package com.example.journalApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

	@Autowired
	private JournalEntryService journalEntryService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = userService.findByUsername(username);
		List<JournalEntry> allEntries = user.getJournalEntries();

		if (allEntries != null && !allEntries.isEmpty()) {
			return new ResponseEntity<>(allEntries, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			journalEntryService.saveNewEntry(myEntry, username);
			return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("id/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = userService.findByUsername(username);
		List<JournalEntry> entries = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(myId)).collect(Collectors.toList());
		
		if(!entries.isEmpty()) {
			Optional<JournalEntry> journal = journalEntryService.findById(myId);

			if (journal.isPresent()) {
				return new ResponseEntity<>(journal.get(), HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		boolean removed = journalEntryService.deleteById(myId, username);
		if(removed) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("id/{id}")
	public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = userService.findByUsername(username);
		List<JournalEntry> entries = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).collect(Collectors.toList());
		
		if(!entries.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
			if (journalEntry.isPresent()) {
				JournalEntry originalEntry = journalEntry.get();
				originalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
						: originalEntry.getTitle());
				originalEntry.setContent(
						newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
								: originalEntry.getContent());
				journalEntryService.saveEntry(originalEntry);
				return new ResponseEntity<>(originalEntry, HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
