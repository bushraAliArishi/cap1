package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UserService {
     private final ArrayList<User> users = new ArrayList<>();
     private  ProductService productService;

    public void adduserService() {
        // Adding default users
        users.add(new User("U001", "admin1", "Admin123", "admin1@example.com", "Admin", 1000.0, new ArrayList<>()));
        users.add(new User("U002", "customer1", "Cust123", "cust1@example.com", "Customer", 500.0, new ArrayList<>()));
        users.add(new User("U003", "customer2", "Cust456", "cust2@example.com", "Customer", 300.0, new ArrayList<>()));
        users.add(new User("U004", "admin2", "Admin456", "admin2@example.com", "Admin", 1200.0, new ArrayList<>()));
        users.add(new User("U005", "customer3", "Cust789", "cust3@example.com", "Customer", 800.0, new ArrayList<>()));
    }

    // CRUD Operations
    public ArrayList<User> getUsers() {
      return users;
    }

    public String addUser(User user) {
        users.add(user);
        return "User added successfully.";
    }

    public boolean updateUser(String id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equalsIgnoreCase(id)) {
                users.set(i, updatedUser);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equalsIgnoreCase(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    // Additional Endpoints
    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean updateUserBalance(String id, double newBalance) {
        for (User user : users) {
            if (user.getId().equalsIgnoreCase(id)) {
                user.setBalance(newBalance);
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> findUsersByRole(String role) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase(role)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }


    public int countUsersByRole(String role) {
        int count = 0;
        for (User user : users) {
            if (user.getRole().equalsIgnoreCase(role)) {
                count++;
            }
        }
        return count;
    }

    public User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    public boolean grantAdminRole(String adminId, String targetUserId) {
        User admin = getUserById(adminId);
        if (admin == null || !"Admin".equalsIgnoreCase(admin.getRole())) {
            return false;
        }

        for (User user : users) {
            if (user.getId().equalsIgnoreCase(targetUserId)) {
                user.setRole("Admin");
                return true;
            }
        }
        return false;
    }

    public String logProductVisit(String userId, String productID) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                user.getVisitHistory().add(productService.getProductById(productID));
                return "Product " +productService.getProductById(productID)+ " added to visit history for user " + userId + ".";
            }
        }
        return "User not found: " + userId;
    }

    public ArrayList<Product> getVisitHistory(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user.getVisitHistory();
            }
        }
        return null;
    }

}
