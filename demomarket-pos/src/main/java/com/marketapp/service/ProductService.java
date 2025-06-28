package com.marketapp.service;

import com.marketapp.model.Product;
import com.marketapp.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return repository.findById(id)
            .map(product -> {
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                product.setStock(updatedProduct.getStock());
                return repository.save(product);
            })
            .orElse(null);
    }

    public boolean deleteProduct(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Product> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }


    public Product addStock(Long id, int quantity) {
        return repository.findById(id)
            .map(product -> {
                product.setStock(product.getStock() + quantity);
                return repository.save(product);
            })
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product removeStock(Long id, int quantity) {
        return repository.findById(id)
            .map(product -> {
                if (product.getStock() < quantity) {
                    throw new RuntimeException("Yetersiz stok!");
                }
                product.setStock(product.getStock() - quantity);
                return repository.save(product);
            })
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product sellProduct(Long id, int quantity) {
        return repository.findById(id)
            .map(product -> {
                if (product.getStock() < quantity) {
                    throw new RuntimeException("Yetersiz stok!");
                }
                product.setStock(product.getStock() - quantity);
                return repository.save(product);
            })
            .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));
    }

}
