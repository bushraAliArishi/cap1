package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchantStocks")
public class MerchantStockController {
    final private MerchantStockService merchantStockService = new MerchantStockService();

    // Get all merchant stocks
    @GetMapping("/get")
    public ResponseEntity getMerchantStocks() {
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();
        return ResponseEntity.ok(merchantStocks);
    }

    // Add a new merchant stock
    @PostMapping("/add/{merchantID}/{productID}")
    public ResponseEntity addMerchantStock(@PathVariable String merchantID, @PathVariable String productID, @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        String response = merchantStockService.addMerchantStock(merchantStock, merchantID, productID);
        return ResponseEntity.ok(new ApiResponse(response));
    }

    // Update a merchant stock
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        boolean updated = merchantStockService.updateMerchantStock(id, merchantStock);
        if (updated) {
            return ResponseEntity.ok(new ApiResponse("Merchant stock updated successfully."));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Merchant stock not found."));
    }

    // Delete a merchant stock
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id) {
        boolean deleted = merchantStockService.deleteMerchantStock(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse("Merchant stock deleted successfully."));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Merchant stock not found."));
    }


    // Increase stock
    @PutMapping("/{merchantID}/{productID}/increaseStock/{quantity}")
    public ResponseEntity increaseStock(@PathVariable String merchantID, @PathVariable String productID, @PathVariable int quantity) {
        boolean success = merchantStockService.increaseStock(merchantID, productID, quantity);
        if (success) {
            return ResponseEntity.ok(new ApiResponse("Stock increased successfully."));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Merchant stock not found."));
    }

    // Decrease stock
    @PutMapping("/{merchantID}/{productID}/decreaseStock/{quantity}")
    public ResponseEntity decreaseStock(@PathVariable String merchantID, @PathVariable String productID, @PathVariable int quantity) {
        boolean success = merchantStockService.decreaseStock(merchantID, productID, quantity);
        if (success) {
            return ResponseEntity.ok(new ApiResponse("Stock decreased successfully."));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Merchant stock not found or insufficient stock."));
    }

    // Find low-stock items
    @GetMapping("/lowStock/{low}")
    public ResponseEntity findLowStock(@PathVariable int low) {
        ArrayList<MerchantStock> lowStock = merchantStockService.findLowStock(low);
        return ResponseEntity.ok(lowStock);
    }

    // Get stocks for a specific merchant
    @GetMapping("/merchant/{merchantID}")
    public ResponseEntity findMerchantStocksByMerchant(@PathVariable String merchantID) {
        ArrayList<MerchantStock> stocks = merchantStockService.findMerchantStocksByMerchant(merchantID);
        return ResponseEntity.ok(stocks);
    }

    // Count products for a specific merchant
    @GetMapping("/count/{merchantID}")
    public ResponseEntity countProductsForMerchant(@PathVariable String merchantID) {
        int count = merchantStockService.countProductsForMerchant(merchantID);
        return ResponseEntity.ok(new ApiResponse("Total products for merchant: " + count));
    }
}
