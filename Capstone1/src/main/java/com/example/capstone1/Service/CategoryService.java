package com.example.capstone1.Service;
import org.springframework.stereotype.Service;
import com.example.capstone1.Model.Category;
import java.util.ArrayList;

@Service
public class CategoryService {

    private final ArrayList<Category> categories = new ArrayList<>();

    // CRUD operations
    public ArrayList<Category> getCategories() {
        return categories;
    }

    public String addCategory(Category category) {
        categories.add(category);
        return "Category added successfully.";
    }

    public boolean updateCategory(String id, Category updatedCategory) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.set(i, updatedCategory);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    public int countCategories() {
        return categories.size();
    }
    public String addBulkCategories(ArrayList<Category> categories) {
        categories.addAll(categories);
        return "Bulk categories added successfully.";
    }
}
