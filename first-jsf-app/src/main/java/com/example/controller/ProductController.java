package com.example.controller;

import com.example.persist.Brand;
import com.example.persist.BrandRepository;
import com.example.persist.Category;
import com.example.persist.CategoryRepository;
import com.example.service.ProductService;
import com.example.service.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class ProductController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @EJB
    private ProductService productService;

    @EJB
    private CategoryRepository categoryRepository;

    @EJB
    private BrandRepository brandRepository;

    @Inject
    private HttpServletRequest request;

    private List<ProductDto> products;

    private List<Category> categories;

    private List<Brand> brands;

    private ProductDto product;

    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        logger.info("categoryId param: {}", getCategoryIdFilter());
        if (getCategoryIdFilter() != null ) {
            this.products = productService.findByCategoryId(Long.parseLong(getCategoryIdFilter()));
        } else {
            this.products = productService.findAll();
        }
        this.categories = categoryRepository.findAll();
        this.brands = brandRepository.findAll();
    }

    public String getCategoryIdFilter() {
        return request.getParameter("categoryId");
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public List<ProductDto> findAll() {
        return products;
    }

    public List<ProductDto> findByCategoryId(Long id) {
        return productService.findByCategoryId(id);
    }

    public String editProduct(ProductDto product) {
        this.product = product;
        return "/product_form.xhtml?faces-redirect=true";
    }

    public String addProduct() {
        this.product = new ProductDto();
        return "/product_form.xhtml?faces-redirect=true";
    }

    public String saveProduct() {
        productService.save(product);
        return "/product.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductDto product) {
        productService.delete(product.getId());
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }
}
