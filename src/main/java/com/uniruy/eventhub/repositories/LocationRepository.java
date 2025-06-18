package com.uniruy.eventhub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniruy.eventhub.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByNameIgnoreCase(String name);
    
}
