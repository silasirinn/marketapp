package com.marketapp.controller;

import com.marketapp.model.Sale;
import com.marketapp.repository.SaleRepository;
import com.marketapp.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;
    private final SaleRepository saleRepository;

    public SaleController(SaleService saleService, SaleRepository saleRepository) {
        this.saleService = saleService;
        this.saleRepository = saleRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sale>> getAllSales() {
        return ResponseEntity.ok(saleRepository.findAll());
    }
}