package com.harrisonmoses.store.controllers;


import com.harrisonmoses.store.Dtos.ProductDto;
import com.harrisonmoses.store.Entity.Product;
import com.harrisonmoses.store.Mappers.ProductMapper;
import com.harrisonmoses.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private List<Product> productList;

    @GetMapping("")
    public Iterable<ProductDto> getProducts(
            @RequestParam(required = false,
                    defaultValue="",
                    name="categoryId"
            )
            Byte categoryId) {

        if(categoryId != null){
            productList = productRepository.findByCategoryId(categoryId);
        }else{
            productList = productRepository.findAll();
        }

        return productList
                .stream()
                .map(productMapper::toProductDto)
                .toList();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductDetail(@PathVariable Long id) {
      var product = productRepository.findById(id).orElse(null);
      if (product == null) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(productMapper.toProductDto(product));
    }
}
