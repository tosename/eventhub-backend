package com.uniruy.eventhub.dto;

import java.time.LocalDateTime;

import com.uniruy.eventhub.entities.Event;


public class EventDTO {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private String description;
    private LocationDTO location;
    private CategoryDTO category;

    public EventDTO() {
    }
 
    public EventDTO(Event entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.dateTime = entity.getDateTime();
        this.description = entity.getDescription();
        this.location = new LocationDTO(entity.getLocation());
        this.category = new CategoryDTO(entity.getCategory());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    
}
