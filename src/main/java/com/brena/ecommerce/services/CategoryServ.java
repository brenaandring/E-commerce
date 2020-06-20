package com.brena.ecommerce.services;

import com.brena.ecommerce.models.Category;
import com.brena.ecommerce.repositories.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServ {
    private final CategoryRepo categoryRepo;

    public CategoryServ(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> allCategories() {
        return categoryRepo.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepo.save(category);
    }

    public Category findCategory(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        return category.orElse(null);
    }

    public Category findCategoryByName(Category category) {
        return categoryRepo.findCategoryByName(category);
    }

    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }
}
