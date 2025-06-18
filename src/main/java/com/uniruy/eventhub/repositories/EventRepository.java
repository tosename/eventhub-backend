package com.uniruy.eventhub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uniruy.eventhub.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByNameIgnoreCase(String name);

    List<Event> findByLocationId(Long locationId);

    List<Event> findByCategoryId(Long categoryId);

    // Optional<List<Event>> findByLocationNameIgnoreCase(String locationName);

    // Optional<List<Event>> findByCategoryNameIgnoreCase(String categoryName);

    // Optional<List<Event>> findByLocationNameIgnoreCaseAndCategoryNameIgnoreCase(
    //     String locationName, 
    //     String categoryName
    // );
    
    // // Event name, location name and category name filters applied (case insensitive)
    // Optional<List<Event>> findByNameIgnoreCaseAndLocationNameIgnoreCaseAndCategoryNameIgnoreCase(
    //     String name,
    //     String locationName,
    //     String categoryName
    // );

    // // Alias method for findByNameIgnoreCaseAndLocationNameIgnoreCaseAndCategoryNameIgnoreCase
    // default Optional<List<Event>> searchFilter(String name, String locationName, String CategoryName) {
    //     return findByNameIgnoreCaseAndLocationNameIgnoreCaseAndCategoryNameIgnoreCase(name, locationName, CategoryName);
    // }

    // Combination filter search for events
    @Query("""
        SELECT e FROM Event e
        WHERE (:name IS NULL OR e.name LIKE %:name%)
        AND (:locationName IS NULL OR e.location.name LIKE %:locationName%)
        AND (:categoryName IS NULL OR e.category.name LIKE %:categoryName%)
        """)
    List<Event> eventFilter(
        @Param("name") String name,
        @Param("locationName") String locationName,
        @Param("categoryName") String categoryName
    );

}
