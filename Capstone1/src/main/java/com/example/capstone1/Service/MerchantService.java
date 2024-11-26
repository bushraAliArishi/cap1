package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    private ArrayList<Merchant> merchants = new ArrayList<>();
    private UserService userService;


    public ArrayList<Merchant> getAllMerchants() {
        return merchants;
    }

    public String addMerchant(Merchant merchant) {
        for (Merchant m : merchants) {
            if (m.getId().equals(merchant.getId())) {
                return "Merchant with the same ID already exists.";
            }
        }
        merchants.add(merchant);
        return "Merchant added successfully.";
    }

    public String updateMerchant(String id, Merchant updatedMerchant) {
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                m.setName(updatedMerchant.getName());
                m.setActive(updatedMerchant.isActive());
                m.setCity(updatedMerchant.getCity());
                m.setCountry(updatedMerchant.getCountry());
                return "Merchant updated successfully.";
            }
        }
        return "Merchant not found.";
    }

    public String deleteMerchant(String id, String userId) {
        User user = userService.getUserById(userId);
        if (user == null || !user.getRole().equals("Admin")) {
            return "Access Denied: Only Admins can delete a merchant.";
        }
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                merchants.remove(m);
                return "Merchant deleted successfully.";
            }
        }
        return "Merchant not found.";
    }

    public String activateMerchant(String id, String userId) {
        User user = userService.getUserById(userId);
        if (user == null || !user.getRole().equals("Admin")) {
            return "Access Denied: Only Admins can activate a merchant.";
        }
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                m.setActive(true);
                return "Merchant activated successfully.";
            }
        }
        return "Merchant not found.";
    }

    public String deactivateMerchant(String id, String userId) {
        User user = userService.getUserById(userId);
        if (user == null || !user.getRole().equals("Admin")) {
            return "Access Denied: Only Admins can deactivate a merchant.";
        }
        for (Merchant m : merchants) {
            if (m.getId().equals(id)) {
                m.setActive(false);
                return "Merchant deactivated successfully.";
            }
        }
        return "Merchant not found.";
    }
}
