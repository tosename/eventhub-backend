package com.uniruy.eventhub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.uniruy.eventhub.dto.LocationDTO;
import com.uniruy.eventhub.entities.Event;
import com.uniruy.eventhub.entities.Location;
import com.uniruy.eventhub.repositories.EventRepository;
import com.uniruy.eventhub.repositories.LocationRepository;
import com.uniruy.eventhub.responses.ResponseMessage;


@Service
public class LocationService {
    
    @Autowired
    public LocationRepository locationRepository;

    @Autowired
    public EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<LocationDTO> getALLLocation() throws ResponseStatusException {
        try {
            List<Location> listLocation = locationRepository.findAll();
            List<LocationDTO> dto = listLocation.stream().map(Location -> new LocationDTO(Location)).toList();

            return dto;
            
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");

        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getLocationById(Long id) {
        try {
            Optional<Location> locationOptional = locationRepository.findById(id);
            
            if(!locationOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find location"));
            }

            LocationDTO dto = new LocationDTO(locationOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(dto);

        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }

    @Transactional(readOnly = true)
    public List<LocationDTO> getLocationByName(String name) throws ResponseStatusException {
        try {
            List<Location> locationList = locationRepository.findByNameIgnoreCase(name);
            List<LocationDTO> dto = locationList.stream().map(location -> new LocationDTO(location)).toList();

            return dto;

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
        }
    }

    @Transactional
    public ResponseEntity<?> postLocation(LocationDTO body) {
        try {
            if(
                body.getName() == null || body.getName().isBlank() ||
                body.getAddress() == null || body.getAddress().isBlank() ||
                body.getCity() == null || body.getCity().isBlank() ||
                body.getCountry() == null || body.getCountry().isBlank()
            ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("All fields are required"));
            }

            locationRepository.save(new Location(body));
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Location sucessfully created"));
        
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }

    @Transactional
    public ResponseEntity<?> putLocation(LocationDTO body, Long id) {
        try {
            Optional<Location> locationOptional = locationRepository.findById(id);

            if(!locationOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find location"));
            }

            Location location = locationOptional.get();

            location.setName(body.getName() == null ? location.getName() : body.getName());
            location.setAddress(body.getAddress() == null ? location.getAddress() : body.getAddress());
            location.setCity(body.getCity() == null ? location.getCity() : body.getCity());
            location.setCountry(body.getCountry() == null ? location.getCountry() : body.getCountry());

            // location.setName(body.getName());
            // location.setAdress(body.getAdress());
            // location.setCity(body.getCity());
            // location.setCountry(body.getCountry());

            locationRepository.save(location);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Location updated succesfully"));

        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal Error"));
        }
    }

    @Transactional
    public ResponseEntity<?> deleteLocation(Long id) {
        try {
            List<Event> listEvent = eventRepository.findByLocationId(id);

            if(!(listEvent.isEmpty())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage("Cannot delete location: it is referenced by one or more events"));
            }

            Optional<Location> locationOptional = locationRepository.findById(id);

            if(!locationOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find location"));
            }

            locationRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Location deleted successfully"));
        
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }
}