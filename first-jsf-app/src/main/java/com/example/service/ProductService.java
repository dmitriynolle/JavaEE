package com.example.service;

import com.example.persist.BrandRepository;
import com.example.persist.CategoryRepository;
import com.example.persist.Product;
import com.example.persist.ProductRepository;
import com.example.service.dto.ProductDto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Named
public class ProductService {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private BrandRepository brandRepository;

    @PostConstruct
    public void init() {
        if (productRepository.count() == 0) {
            productRepository.save(new Product(null, "Product 1", new BigDecimal(100), null, null));
            productRepository.save(new Product(null, "Product 2", new BigDecimal(200), null, null));
            productRepository.save(new Product(null, "Product 3", new BigDecimal(300), null, null));
            productRepository.save(new Product(null, "Продукт 4", new BigDecimal(300), null, null));
        }
    }

    @Transactional
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

    @Transactional
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

    @Transactional
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
