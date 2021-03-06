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
import com.messageboard.backend.models.User;
import com.messageboard.backend.repositories.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("allusers")
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	@GetMapping("user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		User users = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found."));
				return ResponseEntity.ok(users);
	}
	
	@GetMapping("allusers/{nickname}")
	public List<User> getUsersByNickname(@PathVariable String nickname) {
		List<User> users = userRepo.findByNickname(nickname);
		if(users.isEmpty()) {
			System.out.println(new ResourceNotFoundException("User(s) with the name " + nickname + " not found."));
		}
		return userRepo.findByNickname(nickname);
	}
	
	@PostMapping("adduser")
	public User newUser(@RequestBody User users) {
		return userRepo.save(users);
	}
	
	@PutMapping("user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User newUserInfo) {
		User foundUser = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found."));
		
		foundUser.setFirstname(newUserInfo.getFirstname());
		foundUser.setLastname(newUserInfo.getLastname());
		
		User updatedUser = userRepo.save(foundUser);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id) {
		userRepo.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found."));
		
		String message =  "User has been deleted.";
		
		userRepo.deleteById(id);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	
	
}
