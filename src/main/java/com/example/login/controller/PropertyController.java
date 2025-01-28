package com.example.login.controller;

import com.example.login.model.Property;
import com.example.login.service.PropertyService;
import com.example.login.util.JwtUtil;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;
   import jakarta.servlet.http.HttpServletRequest; // Adjust for updated package if needed.

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private JwtUtil jwtUtil;

    // Helper method to extract the token from the Authorization header
    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Extract token part
        }
        return null; // No token provided
    }






    @GetMapping
    public Page<Property> getProperties(@RequestParam int page, @RequestParam int size) {
        return propertyService.getProperties(page, size);
    }







    // Endpoint to create a new property (Requires authentication)
    @PostMapping("/add")
    public ResponseEntity<Property> addProperty(@RequestBody Property property, HttpServletRequest request) {
        String token = getTokenFromRequest(request);

        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Property newProperty = propertyService.addProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProperty);
    }









    // GET all properties
    @GetMapping("/all")
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    // GET property by ID
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id)
                .map(property -> ResponseEntity.ok(property))
                .orElseThrow(() -> new PropertyNotFoundException("Property not found with id: " + id));
    }

    // DELETE property by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
    // Endpoint to get all properties (Requires authenticatio

    // Endpoint to get a single property by ID (Requires authentication)
//    @GetMapping("/{id}")
//    public ResponseEntity<Object> getProperty(@PathVariable Long id, HttpServletRequest request) {
//        String token = getTokenFromRequest(request);
//
//        if (token == null || !jwtUtil.validateToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//
//        return propertyService.getPropertyById(id)
//                .<ResponseEntity<Object>>map(property -> ResponseEntity.ok(property))
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found"));
//    }


//    @GetMapping("/all")
//    public ResponseEntity<Object> getAllProperties(HttpServletRequest request) {
//        String token = getTokenFromRequest(request);
//
//        if (token == null || !jwtUtil.validateToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//
//        List<Property> properties = propertyService.getAllProperties();
//        if (properties.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No properties found");
//        }
//
//        return ResponseEntity.ok(properties);
//    }


//    @PutMapping("/{id}")
//    public ResponseEntity<Object> updateProperty(@PathVariable Long id, @RequestBody Property propertyDetails, HttpServletRequest request) {
//        String token = getTokenFromRequest(request);
//
//        if (token == null || !jwtUtil.validateToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//
//        Property updatedProperty = propertyService.updateProperty(id, propertyDetails);
//        if (updatedProperty == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found");
//        }
//
//        return ResponseEntity.ok(updatedProperty);
//    }
//    // Endpoint to delete a property (Requires authentication)
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteProperty(@PathVariable Long id, HttpServletRequest request) {
//        String token = getTokenFromRequest(request);
//
//        if (token == null || !jwtUtil.validateToken(token)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//
//        propertyService.deleteProperty(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Property deleted");
//    }
}
