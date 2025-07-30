package com.harrisonmoses.store.controllers;


import com.harrisonmoses.store.Dtos.CreateProductRequest;
import com.harrisonmoses.store.Dtos.ProductDto;
import com.harrisonmoses.store.Entity.Product;
import com.harrisonmoses.store.Mappers.ProductMapper;
import com.harrisonmoses.store.repositories.CategoryRepository;
import com.harrisonmoses.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final List<Product> productList;

    @GetMapping("")
    public Iterable<ProductDto> getProducts(
            @RequestParam(required = false,
                    defaultValue="",
                    name="categoryId"
            )
            Byte categoryId) {

        if(categoryId != null){
           var product= productRepository.findByCategoryId(categoryId);
        }else{
            var product = productRepository.findAll();
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


    @PostMapping("")
    public  ResponseEntity<ProductDto> createProduct( @Valid @RequestBody CreateProductRequest request,
                                                     UriComponentsBuilder uriBuilder) {
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        var product =  productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);

        var dtoProduct = productMapper.toProductDto(product);
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(dtoProduct);
    }


    @PutMapping("/{id}")
    public  ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                     @Valid @RequestBody CreateProductRequest request) {

        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        var product = productRepository.findById(id).orElse(null);
        if (product == null || category == null) {
            return ResponseEntity.notFound().build();
        }else{
            productMapper.updateProduct(request,product);
            product.setCategory(category);
            productRepository.save(product);
            return ResponseEntity.ok(productMapper.toProductDto(product));
        }

    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
       return  ResponseEntity.noContent().build();

    }




}
