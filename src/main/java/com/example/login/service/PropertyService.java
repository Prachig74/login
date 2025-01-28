package com.example.login.service;

import com.example.login.model.Property;
import com.example.login.repository.PropertyRepository;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {





    @Autowired
    private PropertyRepository propertyRepository;

    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }


    public Page<Property> getProperties(int page, int size) {
        return propertyRepository.findAll(PageRequest.of(page, size));
    }



    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

//    public void deleteProperty(Long id) {
//        propertyRepository.deleteById(id);
//    }




    // Update property by ID
    public Property updateProperty(Long id, Property propertyDetails) {
        Property property = propertyRepository.findById(id).orElse(null);

        if (property != null) {
            property.setTitle(propertyDetails.getTitle());
            property.setDescription(propertyDetails.getDescription());
            property.setAddress(propertyDetails.getAddress());
            property.setPrice(propertyDetails.getPrice());
            return propertyRepository.save(property);
        }
        return null;
    }

    // Delete property by ID
    public void deleteProperty(Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
        } else {
            throw new PropertyNotFoundException("Property not found with id: " + id);
        }
    }


}
