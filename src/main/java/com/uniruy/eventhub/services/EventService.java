package com.uniruy.eventhub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.uniruy.eventhub.dto.EventDTO;
import com.uniruy.eventhub.entities.Category;
import com.uniruy.eventhub.entities.Event;
import com.uniruy.eventhub.entities.Location;
import com.uniruy.eventhub.repositories.EventRepository;
import com.uniruy.eventhub.responses.ResponseMessage;

@Service
public class EventService {
    
    @Autowired
    public EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<EventDTO> getAllEvent() throws ResponseStatusException {
        try {
            List<Event> listEvent = eventRepository.findAll();
            List<EventDTO> dto = listEvent.stream().map(event -> new EventDTO(event)).toList();

            return dto;
            
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");

        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getEventById(Long id) {
        try {
            Optional<Event> eventOptional = eventRepository.findById(id);
            
            if(!eventOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find event"));
            }

            EventDTO dto = new EventDTO(eventOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(dto);

        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }

    @Transactional(readOnly = true)
    public List<EventDTO> getEventByName(String name) throws ResponseStatusException {
        try {
            List<Event> eventList = eventRepository.findByNameIgnoreCase(name);
            List<EventDTO> dto = eventList.stream().map(event -> new EventDTO(event)).toList();

            return dto;

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
        }
    }

    @Transactional(readOnly = true)
    public List<EventDTO> getEventFilter(String name, String locationName, String categoryName) throws ResponseStatusException{
        try {
            List<Event> eventList = eventRepository.eventFilter(name, locationName, categoryName);
            List<EventDTO> dto = eventList.stream().map(event -> new EventDTO(event)).toList();

            return dto;
        
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
        }
    }

    @Transactional
    public ResponseEntity<?> postEvent(EventDTO body) {
        try {
            if(
                body.getName() == null || body.getName().isBlank() ||
                body.getDateTime() == null ||
                body.getDescription() == null || body.getDescription().isBlank() ||
                body.getLocation() == null ||
                body.getCategory() == null
            ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("All fields are required"));
            }

            eventRepository.save(new Event(body));
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Event sucessfully created"));
        
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }

    @Transactional
    public ResponseEntity<?> putEvent(EventDTO body, Long id) {
        try {
            Optional<Event> eventOptional = eventRepository.findById(id);

            if(!eventOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find location"));
            }

            Event event = eventOptional.get();

            event.setName(body.getName() == null ? event.getName() : body.getName());
            event.setDateTime(body.getDateTime() == null ? event.getDateTime() : body.getDateTime());
            event.setDescription(body.getDescription() == null ? event.getDescription() : body.getDescription());
            event.setLocation(body.getLocation() == null ? event.getLocation() : new Location(body.getLocation()));
            event.setCategory(body.getCategory() == null ? event.getCategory() : new Category(body.getCategory()));

            // event.setName(body.getName());
            // event.setDateTime(body.getDateTime());
            // event.setDescription(body.getDescription());
            // event.setLocation(new Location(body.getLocation()));
            // event.setCategory(new Category(body.getCategory()));

            eventRepository.save(event);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Event updated succesfully"));

        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }

    @Transactional
    public ResponseEntity<?> deleteEvent(Long id) {
        try {
            Optional<Event> eventOptional = eventRepository.findById(id);

            if(!eventOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find event"));
            }

            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Event deleted successfully"));
        
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }
}
