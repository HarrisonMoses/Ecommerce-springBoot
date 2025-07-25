package com.harrisonmoses.store.Mappers;


import com.harrisonmoses.store.Dtos.CreateProductRequest;
import com.harrisonmoses.store.Dtos.ProductDto;
import com.harrisonmoses.store.Entity.Category;
import com.harrisonmoses.store.Entity.Product;
import com.harrisonmoses.store.repositories.CategoryRepository;
import com.harrisonmoses.store.repositories.ProductRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source="category.id", target= "categoryId" )
    ProductDto toProductDto(Product product);

    Product toEntity(CreateProductRequest request);

    void updateProduct(CreateProductRequest request, @MappingTarget Product product);

}
