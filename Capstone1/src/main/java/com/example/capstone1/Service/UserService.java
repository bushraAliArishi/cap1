package com.example.capstone1.Service;

import com.example.capstone1.Model.User;

import java.util.ArrayList;

public class UserService {
    final private ArrayList<User> users = new ArrayList<>();

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
        return null;  // Return null if user is not found
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

}
