package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
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
        userService.adduserService();
        return ResponseEntity.ok(userService.getUsers());
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
    @PutMapping("/visit/{userId}")
    public ResponseEntity<ApiResponse> visitProduct(@PathVariable String userId, @RequestBody String productId) {
        if (userId == null || userId.isEmpty() || productId == null || productId.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("User ID and Product ID cannot be null or empty."));
        }

        String responseMessage = userService.logProductVisit(userId, productId.trim());
        if (responseMessage.contains("added to visit history")) {
            return ResponseEntity.status(200).body(new ApiResponse(responseMessage));
        }
        return ResponseEntity.status(404).body(new ApiResponse(responseMessage));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getVisitHistory(@PathVariable String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("User ID cannot be null or empty."));
        }

        ArrayList<Product> history = userService.getVisitHistory(userId);
        if (history == null) {
            return ResponseEntity.status(404).body(new ApiResponse("User not found: " + userId));
        }
        return ResponseEntity.status(200).body(history);
    }

}
