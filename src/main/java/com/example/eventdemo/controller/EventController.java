package com.example.eventdemo.controller;

import com.example.eventdemo.model.Event;
import com.example.eventdemo.model.Registration;
import com.example.eventdemo.repository.EventRepository;
import com.example.eventdemo.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @PostMapping("/create")
    public String createEvent(@ModelAttribute Event event,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              Principal principal) {
        try {
            // your image upload logic...
            if (!imageFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String uploadDir = "uploads/";
                java.nio.file.Path uploadPath = java.nio.file.Paths.get("src/main/resources/static/" + uploadDir);
                java.nio.file.Files.createDirectories(uploadPath);
                imageFile.transferTo(uploadPath.resolve(fileName));
                event.setImageUrl("/" + uploadDir + fileName);
            }

            event.setCreatedBy(principal.getName());
            eventRepository.save(event);
            return "redirect:/admin/home";

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // or a specific error page
        }
    }




    @GetMapping("/create-form")
    public String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin-home";
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String editEventForm(@PathVariable String id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id: " + id));
        model.addAttribute("event", event);
        return "editEventForm"; // This must match the filename of your HTML (without `.html`)
    }

    // Process form submission
    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable String id, @ModelAttribute Event updatedEvent) {
        Event existingEvent = eventRepository.findById(id).orElseThrow();
        updatedEvent.setId(existingEvent.getId()); // retain ID
        updatedEvent.setCreatedBy(existingEvent.getCreatedBy()); // retain creator
        eventRepository.save(updatedEvent);
        return "redirect:/admin/home";
    }


    // Delete event
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable String id) {
        eventRepository.deleteById(id);
        return "redirect:/admin/home";
    }



    @GetMapping("/registrations/{eventId}")
    public String viewRegistrations(@PathVariable String eventId, Model model, Principal principal) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id: " + eventId));

        // Check if the logged-in admin is the creator
        if (!event.getCreatedBy().equals(principal.getName())) {
            return "redirect:/access-denied"; // or display error
        }

        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        model.addAttribute("event", event); // Optional: show event title in view
        model.addAttribute("registrations", registrations);
        return "event-registrations";
    }



}
