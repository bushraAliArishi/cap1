package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")

public class ProductController {

    final private ProductService productService = new ProductService();

    @GetMapping("/get")
    public ResponseEntity getProducts() {
        productService.productaddService();
        return ResponseEntity.status(200).body(new ApiResponse("Products retrieved successfully."+ productService.getProducts()));
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String response = productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse(response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        boolean updated = productService.updateProduct(id, product);
        if (updated) {
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully."));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found."));
    }

    @GetMapping("/category/{categoryID}")
    public ResponseEntity getProductsByCategory(@PathVariable String categoryID) {
        ArrayList<Product> products = productService.getProductsByCategory(categoryID);
        return ResponseEntity.status(200).body(new ApiResponse("Products in category " + categoryID+" "+ products));
    }
    @PutMapping("/resetPrice/{id}")
    public ResponseEntity resetPrice(@PathVariable String id) {
        String response = productService.resetPrice(id);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/requestRestock/{customerId}/{productId}/{merchantId}")
    public String requestRestock(@PathVariable String customerId, @PathVariable String productId, @PathVariable String merchantId) {
        return productService.requestRestock(customerId, productId, merchantId);
    }

    @GetMapping("/cheapest")
    public ResponseEntity findCheapestProduct() {
        Product cheapest = productService.findCheapestProduct();
        if (cheapest != null) {
            return ResponseEntity.status(200).body(new ApiResponse("Cheapest product found."+ cheapest));
        }
        return ResponseEntity.status(400).body(new ApiResponse("No products available."));
    }

    @GetMapping("/count/{categoryID}")
    public ResponseEntity countProductsByCategory(@PathVariable String categoryID) {
        int count = productService.countProductsByCategory(categoryID);
        return ResponseEntity.status(200).body(new ApiResponse("Total products in category " + categoryID + ": " + count));
    }
    @PutMapping("/applyDiscount/{productId}/{merchantId}/{discountPercentage}")
    public ResponseEntity<String> applyDiscount(
            @PathVariable String productId,
            @PathVariable String merchantId,
            @PathVariable double discountPercentage) {
        String response = productService.applyDiscount(productId, merchantId, discountPercentage);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/setExpiryDate/{id}/{expiryDate}")
    public ResponseEntity setExpiryDate(
            @PathVariable String id,
            @PathVariable String expiryDate) {
        String response = productService.setExpiryDate(id, expiryDate);
        return ResponseEntity.ok(response);
    }


}
