package com.uniruy.eventhub.dto;

import org.springframework.beans.BeanUtils;

import com.uniruy.eventhub.entities.Category;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;

    public CategoryDTO() {
    }

    public CategoryDTO(Category entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

}
