package com.uniruy.eventhub.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.uniruy.eventhub.dto.EventDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_event")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    public Event() {
    }

    public Event(EventDTO eventDTO) {
        this.id = eventDTO.getId();
        this.name = eventDTO.getName();
        this.dateTime = eventDTO.getDateTime();
        this.description = eventDTO.getDescription();
        this.location = new Location(eventDTO.getLocation());
        this.category = new Category(eventDTO.getCategory());
    }

    public Event(Long id, String name, LocalDateTime dateTime, String description, Location location,
            Category category) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.description = description;
        this.location = location;
        this.category = category;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", dateTime=" + dateTime + ", description=" + description
                + ", location=" + location + ", category=" + category + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        return Objects.equals(id, other.id);
    }

    

}
