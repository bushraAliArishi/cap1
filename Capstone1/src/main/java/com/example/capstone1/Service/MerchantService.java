package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.User;

import java.util.ArrayList;

public class MerchantService {

    private final ArrayList<Merchant> merchants = new ArrayList<>();
    private final UserService userService = new UserService();

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public String addMerchant(Merchant merchant) {
        merchants.add(merchant);
        return "Merchant added successfully.";
    }

    public String updateMerchant(String id, Merchant updatedMerchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.set(i, updatedMerchant);
                return "Merchant updated successfully.";
            }
        }
        return "Merchant not found.";
    }

    public String deleteMerchant(String id, String userId) {
        User user = userService.getUserById(userId);
        if (user == null || !"Admin".equals(user.getRole())) {
            return "Access Denied: You must be an Admin to delete a merchant.";
        }
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.remove(i);
                return "Merchant deleted successfully.";
            }
        }
        return "Merchant not found.";
    }

    public String activateMerchant(String id, String userId) {
        User user = userService.getUserById(userId);
        if (user == null || !"Admin".equals(user.getRole())) {
            return "Access Denied: You must be an Admin to activate a merchant.";
        }
        for (Merchant merchant : merchants) {
            if (merchant.getId().equals(id)) {
                merchant.setActive(true);
                return "Merchant activated successfully.";
            }
        }
        return "Merchant not found.";
    }

    public String deactivateMerchant(String id, String userId) {
        User user = userService.getUserById(userId);
        if (user == null || !"Admin".equals(user.getRole())) {
            return "Access Denied: You must be an Admin to deactivate a merchant.";
        }
        for (Merchant merchant : merchants) {
            if (merchant.getId().equals(id)) {
                merchant.setActive(false);
                return "Merchant deactivated successfully.";
            }
        }
        return "Merchant not found.";
    }

    public ArrayList<Merchant> getActiveMerchants() {
        ArrayList<Merchant> activeMerchants = new ArrayList<>();
        for (Merchant merchant : merchants) {
            if (merchant.isActive()) {
                activeMerchants.add(merchant);
            }
        }
        return activeMerchants;
    }

    public String removeInactiveMerchants(String userId) {
        User user = userService.getUserById(userId);
        if (user == null || !"Admin".equals(user.getRole())) {
            return "Permission denied. Only admins can remove inactive merchants.";
        }

        ArrayList<Merchant> inactiveMerchants = new ArrayList<>();
        for (Merchant merchant : merchants) {
            if (!merchant.isActive()) {
                inactiveMerchants.add(merchant);
            }
        }

        boolean removed = merchants.removeAll(inactiveMerchants);
        if (removed) {
            return "Inactive merchants removed successfully.";
        } else {
            return "No inactive merchants found.";
        }
    }
}