package com.example.eventdemo.controller;

import com.example.eventdemo.model.Event;
import com.example.eventdemo.model.Registration;
import com.example.eventdemo.repository.EventRepository;
import com.example.eventdemo.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/register")
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/{eventId}")
    public String registerForEvent(
            @PathVariable String eventId,
            @ModelAttribute Registration registration,
            Principal principal
    ) {
        registration.setUsername(principal.getName());
        registration.setEventId(eventId);
        registrationRepository.save(registration);
        return "redirect:/user/home";
    }


    @GetMapping("/{eventId}")
    public List<Registration> getRegistrations(@PathVariable String eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    @GetMapping("/form/{eventId}")
    public String showForm(@PathVariable String eventId, Model model) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        model.addAttribute("event", event);
        return "registration-form";
    }

}
