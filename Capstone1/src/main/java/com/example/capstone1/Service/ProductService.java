package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
@Service
public class ProductService {
    final private ArrayList<Product> products = new ArrayList<>();
    private MerchantStockService merchantStockService;
    private UserService userService;
    public void productaddService() {
        // Adding default products
        products.add(new Product("P001", "Laptop", 2000.0, "C001", false, 0.0, 0, 2000.0, 0, null));
        products.add(new Product("P002", "Book", 50.0, "C002", true, 25.0, 7, 50.0, 0, null));
        products.add(new Product("P003", "Smartphone", 1500.0, "C001", true, 1300.0, 5, 1500.0, 0, null));
        products.add(new Product("P004", "Jacket", 300.0, "C003", false, 0.0, 0, 300.0, 0, null));
        products.add(new Product("P005", "Shirt", 100.0, "C003", true, 90.0, 10, 100.0, 0, null));
    }


    // CRUD Operations
    public ArrayList<Product> getProducts() {
        return products;
    }

    public String addProduct(Product product) {
        products.add(product);
        return "Product added successfully.";
    }

    public boolean updateProduct(String id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    // Additional Endpoints
    public ArrayList<Product> getProductsByCategory(String categoryID) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategoryID().equalsIgnoreCase(categoryID)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public Product findCheapestProduct() {
        if (products.isEmpty()) return null;
        Product cheapest = products.get(0);
        for (Product product : products) {
            if (product.getPrice() < cheapest.getPrice()) {
                cheapest = product;
            }
        }
        return cheapest;
    }
    public String requestRestock(String customerId, String productId, String merchantId) {
        // Check if the user exists
        User user = userService.getUserById(customerId);
        if (user == null) {
            return "User not found: " + customerId;
        }

        for (MerchantStock stock : merchantStockService.getMerchantStocks()) {
            if (stock.getProductID().equals(productId) && stock.getMerchantID().equals(merchantId)) {
                // Check if the stock is low
                if (stock.getStock() >= 10) {
                    return "Stock for product " + productId + " under merchant " + merchantId +
                            " is sufficient, restock request not needed.";
                }
                return "Request to restock product " + productId + " under merchant " + merchantId +
                        " noted. Current stock: " + stock.getStock();
            }
        }

        return "Product " + productId + " not found under merchant " + merchantId + ".";
    }

    public int countProductsByCategory(String categoryID) {
        int count = 0;
        for (Product product : products) {
            if (product.getCategoryID().equalsIgnoreCase(categoryID)) {
                count++;
            }
        }
        return count;
    }


    public String applyDiscount(String productId, String merchantId, double discountPercentage) {
        if (discountPercentage <= 0 || discountPercentage > 100) {
            return "Invalid discount percentage. It must be between 1 and 100.";
        }

        boolean merchantOwnsProduct = merchantStockService.checkMerchantOwnsProduct(merchantId, productId);
        if (!merchantOwnsProduct) {
            return "This merchant does not own the specified product.";
        }

        for (Product product : products) {
            if (product.getId().equals(productId)) {
                double newPrice = product.getPrice() - (product.getPrice() * discountPercentage / 100);
                product.setPrice(newPrice);
                return "Discount applied successfully. New price: " + newPrice;
            }
        }
        return "Product not found.";
    }
    public String setProductExpiryDate(String id, String expiryDate) {

        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setExpiryDate(LocalDate.parse(expiryDate));
                return "Product expiry date updated.";
            }
        }
        return "Product not found.";
    }
    public String resetPrice(String productId) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                if (product.getOriginalPrice() == 0) {
                    return "The product has no discount to reset.";
                }
                product.setPrice(product.getOriginalPrice());
                return "Price reset to the original price: " + product.getOriginalPrice();
            }
        }
        return "Product not found.";
    }
    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null; // Return null if product is not found
    }
    public String setExpiryDate(String productId, String expiryDate) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                product.setExpiryDate(LocalDate.parse(expiryDate));
                return "Expiry date updated successfully for product ID: " + productId;
            }
        }
        return "Product not found.";
    }
}
