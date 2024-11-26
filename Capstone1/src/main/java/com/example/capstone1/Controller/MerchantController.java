package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchants")
public class MerchantController {

    private MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getMerchants() {
        return ResponseEntity.status(200).body(new ApiResponse("Merchants retrieved successfully. "+ merchantService.getMerchants()));
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String response = merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant updatedMerchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String response = merchantService.updateMerchant(id, updatedMerchant);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }

    @DeleteMapping("/delete/{id}/{userId}")
    public ResponseEntity deleteMerchant(@PathVariable String id, @PathVariable String userId) {
        String response = merchantService.deleteMerchant(id, userId);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }

    @PutMapping("/activate/{id}/{userId}")
    public ResponseEntity<ApiResponse> activateMerchant(@PathVariable String id, @PathVariable String userId) {
        String response = merchantService.activateMerchant(id, userId);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }

    @PutMapping("/deactivate/{id}/{userId}")
    public ResponseEntity deactivateMerchant(@PathVariable String id, @PathVariable String userId) {
        String response = merchantService.deactivateMerchant(id, userId);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }

    @GetMapping("/active")
    public ResponseEntity getActiveMerchants() {
        return ResponseEntity.status(200).body(new ApiResponse("Active merchants retrieved."+merchantService.getActiveMerchants()));
    }

    @DeleteMapping("/remove-inactive/{userId}")
    public ResponseEntity removeInactiveMerchants(@PathVariable String userId) {
        String response = merchantService.removeInactiveMerchants(userId);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }
}