package com.example.service;

import com.example.persist.BrandRepository;
import com.example.persist.CategoryRepository;
import com.example.persist.Product;
import com.example.persist.ProductRepository;
import com.example.service.dto.ProductDto;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ProductService {

    @EJB
    private ProductRepository productRepository;

    @EJB
    private CategoryRepository categoryRepository;

    @EJB
    private BrandRepository brandRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductService::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDto> findByCategoryId(long id) {
        return productRepository.findByCategoryId(id).stream()
                .map(ProductService::convert)
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> findById(long id) {
        return productRepository.findById(id)
                .map(ProductService::convert);
    }

    @TransactionAttribute
    public Product save(ProductDto productDto) {
        Product product = new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                brandRepository.getReference(productDto.getBrandId()),
                categoryRepository.getReference(productDto.getCategoryId())
        );
        return productRepository.save(product);
    }

    @TransactionAttribute
    public void delete(long id) {
        productRepository.delete(id);
    }

    public long count() {
        return productRepository.count();
    }

    private static ProductDto convert(Product prod) {
        return new ProductDto(
                prod.getId(),
                prod.getName(),
                prod.getPrice(),
                prod.getBrand() != null ? prod.getBrand().getId() : null,
                prod.getBrand() != null ? prod.getBrand().getName() : null,
                prod.getCategory() != null ? prod.getCategory().getId() : null,
                prod.getCategory() != null ? prod.getCategory().getName() : null
        );
    }
}
