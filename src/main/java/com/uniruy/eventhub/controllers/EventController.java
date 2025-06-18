package com.uniruy.eventhub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uniruy.eventhub.dto.EventDTO;
import com.uniruy.eventhub.services.EventService;

@RestController
@RequestMapping(value = "/event")
public class EventController {
    
    @Autowired
    public EventService eventService;
    
    @GetMapping
    public List<EventDTO> getAllEvent() {
        return eventService.getAllEvent();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @GetMapping(value = "/search")
    public List<EventDTO> getEventByName(@RequestParam String name) {
        return eventService.getEventByName(name);
    }

    @GetMapping(value = "/filter")
    public List<EventDTO> getEventFilter(@RequestParam String name, @RequestParam String locationName, @RequestParam String categoryName) {
        return eventService.getEventFilter(name, locationName, categoryName);
    }

    @PostMapping
    public ResponseEntity<?> postEvent(@RequestBody EventDTO body ) {
        return eventService.postEvent(body);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> putEvent(@PathVariable Long id, @RequestBody EventDTO body) {
        return eventService.putEvent(body, id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }
}
