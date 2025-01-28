package com.example.login.repository;

import com.example.login.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Find properties by title
    List<Property> findByTitle(String title);

    // Find properties within a price range
    List<Property> findByPriceBetween(Double minPrice, Double maxPrice);

    // Find properties by address (case-insensitive search)
    List<Property> findByAddressContaining(String address);

    // Custom query to find properties by price greater than a specific value
    @Query("SELECT p FROM Property p WHERE p.price > ?1")
    List<Property> findPropertiesByPriceGreaterThan(Double price);

    // Pagination support
    Page<Property> findAll(Pageable pageable);

    // Sorting support
    List<Property> findAll(Sort sort);
}

