package com.example.capstone1.Controller;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchants")
public class MerchantController {

    private final MerchantService merchantService;


    @GetMapping("/get")
    public ResponseEntity<ArrayList<Merchant>> getAllMerchants() {
        merchantService.merchantaddService();
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMerchant(@RequestBody Merchant merchant) {
        return ResponseEntity.ok(merchantService.addMerchant(merchant));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMerchant(@PathVariable String id, @RequestBody Merchant updatedMerchant) {
        return ResponseEntity.ok(merchantService.updateMerchant(id, updatedMerchant));
    }

    @DeleteMapping("/delete/{id}/{userId}")
    public ResponseEntity<String> deleteMerchant(@PathVariable String id, @PathVariable String userId) {
        return ResponseEntity.ok(merchantService.deleteMerchant(id, userId));
    }

    @PutMapping("/activate/{id}/{userId}")
    public ResponseEntity<String> activateMerchant(@PathVariable String id, @PathVariable String userId) {
        return ResponseEntity.ok(merchantService.activateMerchant(id, userId));
    }

    @PutMapping("/deactivate/{id}/{userId}")
    public ResponseEntity<String> deactivateMerchant(@PathVariable String id, @PathVariable String userId) {
        return ResponseEntity.ok(merchantService.deactivateMerchant(id, userId));
    }
}
