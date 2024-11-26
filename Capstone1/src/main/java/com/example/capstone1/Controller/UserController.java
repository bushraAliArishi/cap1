package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    final private UserService userService = new UserService();

    // Get all users
    @GetMapping("/get")
    public ResponseEntity getUsers() {
        ArrayList<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    // Add a new user
    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        String response = userService.addUser(user);
        return ResponseEntity.ok(new ApiResponse(response));
    }

    // Update an existing user
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        boolean updated = userService.updateUser(id, user);
        if (updated) {
            return ResponseEntity.ok(new ApiResponse("User updated successfully."));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User not found."));
    }

    // Delete a user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse("User deleted successfully."));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User not found."));
    }

    // Additional Endpoints

    // Find user by email
    @GetMapping("/email/{email}")
    public ResponseEntity findUserByEmail(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User not found."));
    }

    // Update user balance
    @PutMapping("/{id}/balance/{newBalance}")
    public ResponseEntity updateUserBalance(@PathVariable String id, @PathVariable double newBalance) {
        boolean updated = userService.updateUserBalance(id, newBalance);
        if (updated) {
            return ResponseEntity.ok(new ApiResponse("User balance updated successfully."));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User not found."));
    }

    // Find users by role
    @GetMapping("/role/{role}")
    public ResponseEntity findUsersByRole(@PathVariable String role) {
        ArrayList<User> users = userService.findUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    // Activate user


    // Count users by role
    @GetMapping("/count/{role}")
    public ResponseEntity countUsersByRole(@PathVariable String role) {
        int count = userService.countUsersByRole(role);
        return ResponseEntity.ok(new ApiResponse("Total users with role " + role + ": " + count));
    }
    @PutMapping("/grant-admin/{adminId}/{targetUserId}")
    public ResponseEntity grantAdminRole(@PathVariable String adminId, @PathVariable String targetUserId) {
        if (adminId == null || adminId.isEmpty() || targetUserId == null || targetUserId.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("Admin ID and Target User ID cannot be null or empty."));
        }

        boolean result = userService.grantAdminRole(adminId, targetUserId);
        if (result) {
            return ResponseEntity.status(200).body(new ApiResponse("User granted admin role successfully."));
        }
        return ResponseEntity.status(403).body(new ApiResponse("Action forbidden or user not found."));
    }

}
