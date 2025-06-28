package com.marketapp.controller;

import com.marketapp.model.Product;
import com.marketapp.service.ProductService;
import com.marketapp.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    private final ProductRepository productRepository;

    public ProductController(ProductService service, ProductRepository productRepository) {
        this.service = service;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Product add(@RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(updatedProduct.getName());
                product.setPrice(updatedProduct.getPrice());
                product.setStock(updatedProduct.getStock());
                productRepository.save(product);
                return ResponseEntity.ok(product);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("name") String name) {
        List<Product> results = service.searchByName(name);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/byCategory")
    public List<Product> getByCategory(@RequestParam String category) {
        return productRepository.findByCategory(category);
    }



    @PostMapping("/{id}/add-stock")
    public ResponseEntity<Product> addStock(@PathVariable Long id, @RequestParam int quantity) {
        Product updatedProduct = service.addStock(id, quantity);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/{id}/remove-stock")
    public ResponseEntity<Product> removeStock(@PathVariable Long id, @RequestParam int quantity) {
        Product updatedProduct = service.removeStock(id, quantity);
        return ResponseEntity.ok(updatedProduct);
    }


    @PostMapping("/{id}/sell")
    public ResponseEntity<Product> sellProduct(@PathVariable Long id, @RequestParam int quantity) {
        Product updatedProduct = service.sellProduct(id, quantity);
        return ResponseEntity.ok(updatedProduct);
    }


}
