package com.messageboard.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.messageboard.backend.exceptions.ResourceNotFoundException;
import com.messageboard.backend.models.Message;
import com.messageboard.backend.models.User;
//import com.messageboard.backend.models.User;
import com.messageboard.backend.repositories.MessageRepository;
import com.messageboard.backend.repositories.UserRepository;
//import com.messageboard.backend.repositories.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class MessageController {
	
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("allmessages")
	public List<Message> getAllMessages() {
		return messageRepo.findAll();
	}
	
	@GetMapping("message/{id}")
	public ResponseEntity<Message> getMessageById(@PathVariable int id) {
		Message messages = messageRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Message not found."));
				return ResponseEntity.ok(messages);
	}
	
	@PostMapping("addmessage/{username}")
	public Message newMessage(@RequestBody Message messages, @PathVariable String username) {
		List<User> userList = userRepo.findByNickname(username);
		User user = userList.get(0);
		messages.setUser(user);
		messageRepo.save(messages);
					
			System.out.println("GGGGGG" + user.toString());
		
		return messageRepo.save(messages);
	}
	
	@PutMapping("message/{id}")
	public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message newMessageInfo) {
		Message foundMessage = messageRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Message not found."));
		
		foundMessage.setPost(newMessageInfo.getPost());
		
		Message updatedMessage = messageRepo.save(foundMessage);
		
		return ResponseEntity.ok(updatedMessage);
	}
	
	@DeleteMapping("message/{id}")
	public ResponseEntity<String> deleteMessage(@PathVariable int id) {
		messageRepo.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Message not found."));
		
		String message =  "Post has been deleted.";
		
		messageRepo.deleteById(id);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
