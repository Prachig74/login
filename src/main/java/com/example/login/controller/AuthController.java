package com.example.login.controller;

//package com.example.authapp.controller;

import com.example.login.model.Users;
import com.example.login.service.UserService;
import com.example.login.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody Users user) {
//        Optional<Users> existingUser = userService.findByUsername(user.getUsername());
//        if (existingUser.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
//        }
//        userService.registerUser(user.getUsername(), user.getPassword());
//        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Users user) {
//        Optional<Users> existingUser = userService.findByUsername(user.getUsername());
//        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
//            String token = jwtUtil.generateToken(user.getUsername());
//            return ResponseEntity.ok(token);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
//    }
//
//    @GetMapping("/validate-token")
//    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // Remove "Bearer "
//            if (jwtUtil.validateToken(token) != null) {
//                return ResponseEntity.ok("Token is valid!");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!");
//    }
//}
//


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Users user) {
        Optional<Users> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
        }

        // Hash the password before saving it
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        Optional<Users> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token); // Return the token if credentials match
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!"); // Invalid credentials
    }


//    @GetMapping("/validate-token")
//    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // Remove "Bearer " prefix
//            if (jwtUtil.validateToken(token) != null) {
//                return ResponseEntity.ok("Token is valid!");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!"); // Token invalid
//    }


    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer "

            if (jwtUtil.validateToken(token)) {
                return ResponseEntity.ok("Token is valid!");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token!");
    }

}
