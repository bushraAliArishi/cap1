package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class MerchantStockService {
    final private ArrayList<MerchantStock> merchantStocks = new ArrayList<>();
    final private ProductService productService = new ProductService();
    final private MerchantService merchantService = new MerchantService();

    public void merchantStockaddService() {
        // Adding default merchant stocks
        merchantStocks.add(new MerchantStock("MS001", "P001", "M001", 50));
        merchantStocks.add(new MerchantStock("MS002", "P002", "M002", 30));
        merchantStocks.add(new MerchantStock("MS003", "P003", "M003", 15));
        merchantStocks.add(new MerchantStock("MS004", "P004", "M004", 70));
        merchantStocks.add(new MerchantStock("MS005", "P005", "M005", 100));
    }

    // CRUD
    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }
    public boolean checkMerchantOwnsProduct(String merchantId, String productId) {
        // Loop through the list of MerchantStock
        for (MerchantStock stock : merchantStocks) {
            // Check if both merchant ID and product ID match
            if (stock.getMerchantID().equals(merchantId) && stock.getProductID().equals(productId)) {
                return true; // Merchant owns the product
            }
        }
        return false; // Merchant does not own the product
    }
    public String addMerchantStock(MerchantStock merchantStock) {
        boolean productExists = false;
        for (Product product : productService.getProducts()) {
            if (product.getId().equalsIgnoreCase(merchantStock.getProductID())) {
                productExists = true;
                break;
            }
        }

        boolean merchantExists = false;
        for (Merchant merchant : merchantService.getAllMerchants()) {
            if (merchant.getId().equalsIgnoreCase(merchantStock.getMerchantID())) {
                merchantExists = true;
                break;
            }
        }

        if (!productExists) {
            return "Invalid product ID.";
        }
        if (!merchantExists) {
            return "Invalid merchant ID.";
        }

        merchantStocks.add(merchantStock);
        return "Merchant stock added successfully.";
    }

    public boolean updateMerchantStock(String id, MerchantStock updatedStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.set(i, updatedStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }


    public MerchantStock findMerchantStock(String merchantID, String productID) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equalsIgnoreCase(merchantID) &&
                    stock.getProductID().equalsIgnoreCase(productID)) {
                return stock;
            }
        }
        return null;
    }

    public ArrayList<MerchantStock> findMerchantStocksByMerchant(String merchantID) {
        ArrayList<MerchantStock> merchantStocksList = new ArrayList<>();
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equalsIgnoreCase(merchantID)) {
                merchantStocksList.add(stock);
            }
        }
        return merchantStocksList;
    }

    public int countProductsForMerchant(String merchantID) {
        int count = 0;
        for (MerchantStock stock : merchantStocks) {
            if (stock.getMerchantID().equalsIgnoreCase(merchantID)) {
                count++;
            }
        }
        return count;
    }
}
