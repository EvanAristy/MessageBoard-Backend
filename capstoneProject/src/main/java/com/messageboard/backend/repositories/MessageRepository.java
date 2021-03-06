package com.messageboard.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.messageboard.backend.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	List<Message> findById(String id);
	public Message save(Message message);

}
