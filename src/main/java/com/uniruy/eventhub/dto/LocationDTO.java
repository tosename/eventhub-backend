package com.uniruy.eventhub.dto;

import org.springframework.beans.BeanUtils;

import com.uniruy.eventhub.entities.Location;

public class LocationDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;

    public LocationDTO() {
    }

    public LocationDTO(Location entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
