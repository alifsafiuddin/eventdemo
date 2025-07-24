package com.example.eventdemo.repository;

import com.example.eventdemo.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByCreatedBy(String username);
}
