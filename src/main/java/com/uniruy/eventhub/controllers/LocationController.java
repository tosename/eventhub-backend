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

import com.uniruy.eventhub.dto.LocationDTO;
import com.uniruy.eventhub.services.LocationService;

@RestController
@RequestMapping(value = "/location")
public class LocationController {
    
    @Autowired
    public LocationService locationService;

    @GetMapping
    public List<LocationDTO> getAllLocation() {
        return locationService.getALLLocation();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping(value = "/search")
    public List<LocationDTO> getLocationByName(@RequestParam String name) {
        return locationService.getLocationByName(name);
    }

    @PostMapping
    public ResponseEntity<?> postLocation(@RequestBody LocationDTO body) {
        return locationService.postLocation(body);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> putLocation(@PathVariable Long id, @RequestBody LocationDTO body) {
        return locationService.putLocation(body, id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        return locationService.deleteLocation(id);
    }

}