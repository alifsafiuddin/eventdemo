package com.example.eventdemo.controller;

import com.example.eventdemo.model.Event;
import com.example.eventdemo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UserPageController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/user/home")
    public String userHome(Model model) {
        List<Event> events = eventRepository.findAll();
        model.addAttribute("events", events);
        return "user-home";
    }

    @GetMapping("/admin/home")
    public String adminHome(Model model, Principal principal) {
        String username = principal.getName();
        List<Event> myEvents = eventRepository.findByCreatedBy(username);
        model.addAttribute("events", myEvents);
        model.addAttribute("event", new Event()); // Ensure the form works
        return "admin-home";
    }


}
