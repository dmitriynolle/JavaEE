package com.example.controller;

import com.example.persist.Category;
import com.example.persist.CategoryRepository;
import com.example.persist.Product;
import com.example.persist.ProductRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CategoryController implements Serializable {

    @Inject
    private CategoryRepository categoryRepository;

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/category_form.xhtml?faces-redirect=true";
    }

    public String addCategory() {
        this.category = new Category();
        return "/category_form.xhtml?faces-redirect=true";
    }

    public String saveCategory() {
        categoryRepository.save(category);
        return "/category.xhtml?faces-redirect=true";
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category.getId());
    }
}
