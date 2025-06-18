package com.uniruy.eventhub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.uniruy.eventhub.dto.CategoryDTO;
import com.uniruy.eventhub.entities.Category;
import com.uniruy.eventhub.entities.Event;
import com.uniruy.eventhub.repositories.CategoryRepository;
import com.uniruy.eventhub.repositories.EventRepository;
import com.uniruy.eventhub.responses.ResponseMessage;

@Service
public class CategoryService {
    
    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> getALLCategory() throws ResponseStatusException {
        try {
            List<Category> listCategory = categoryRepository.findAll();
            List<CategoryDTO> dto = listCategory.stream().map(Category -> new CategoryDTO(Category)).toList();

            return dto;
            
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCategoryById(Long id) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            
            if(!categoryOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find category"));
            }

            CategoryDTO dto = new CategoryDTO(categoryOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(dto);

        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoryByName(String name) throws ResponseStatusException {
        try {
            List<Category> categoryList = categoryRepository.findByNameIgnoreCase(name);
            List<CategoryDTO> dto = categoryList.stream().map(category -> new CategoryDTO(category)).toList();

            return dto;

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
        }
    }

    @Transactional
    public ResponseEntity<?> postCategory(CategoryDTO body) {
        try {
            if(
                body.getName() == null || body.getName().isBlank() ||
                body.getDescription() == null || body.getDescription().isBlank()
            ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("All fields are required"));
            }

            categoryRepository.save(new Category(body));
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Category successfully created"));
        
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal Error"));
        }
    }

    @Transactional
    public ResponseEntity<?> putCategory(CategoryDTO body, Long id) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);

            if(!categoryOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find category"));
            }

            Category category = categoryOptional.get();

            category.setName(body.getName() == null ? category.getName() : body.getName());
            category.setDescription(body.getDescription() == null ? category.getDescription() : body.getDescription());

            // category.setName(body.getName());
            // category.setDescription(body.getDescription());

            categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Category updated succesfully"));

        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }

    @Transactional
    public ResponseEntity<?> deleteCategory(Long id) {
        try {
            List<Event> listEvent = eventRepository.findByCategoryId(id);

            if(!(listEvent.isEmpty())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseMessage("Cannot delete category: it is referenced by one or more events"));
            }

            Optional<Category> categoryOptional = categoryRepository.findById(id);

            if(!categoryOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Couldn't find category"));
            }

            categoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Category deleted successfully"));
        
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Internal error"));
        }
    }
}
