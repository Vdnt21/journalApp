package com.example.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

	@Autowired
	private  JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public void saveNewEntry(JournalEntry journalEntry, String username) {
		try {
			User user = userService.findByUsername(username);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry saved = journalEntryRepository.save(journalEntry);
			user.getJournalEntries().add(saved);
			userService.saveUser(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void saveEntry(JournalEntry journalEntry) {
		journalEntry.setDate(LocalDateTime.now());
		journalEntryRepository.save(journalEntry);
	}
	
	public List<JournalEntry> getAll() {
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> findById(ObjectId id ) {
		return journalEntryRepository.findById(id);
	}
	
	@Transactional
	public boolean deleteById(ObjectId id, String username) {
		boolean removed = false;
		try {
			User user = userService.findByUsername(username);
			removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
			if(removed) {
				userService.saveUser(user);
				journalEntryRepository.deleteById(id);
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occurred while deleting the entry", e);
		}
		return removed;
	}
}
