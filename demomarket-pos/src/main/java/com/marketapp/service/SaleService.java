package com.marketapp.service;

import com.marketapp.model.CartItem;
import com.marketapp.model.Product;
import com.marketapp.model.Sale;
import com.marketapp.repository.ProductRepository;
import com.marketapp.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public List<Sale> recordBulkSales(List<CartItem> cartItems) {
        List<Sale> sales = new ArrayList<>();

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (product.getStock() < quantity) {
                throw new RuntimeException("Yetersiz stok: " + product.getName());
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            Sale sale = new Sale();
            sale.setProduct(product);
            sale.setQuantity(quantity);
            sale.setTotalPrice(item.getTotalPrice());
            sale.setSaleDate(LocalDateTime.now());

            sales.add(saleRepository.save(sale));
        }

        return sales;
    }
}
