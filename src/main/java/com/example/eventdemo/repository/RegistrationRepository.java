package com.example.eventdemo.repository;

import com.example.eventdemo.model.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
    List<Registration> findByEventId(String eventId);
}
