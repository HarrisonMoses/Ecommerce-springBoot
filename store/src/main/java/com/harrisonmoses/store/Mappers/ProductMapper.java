package com.harrisonmoses.store.Mappers;


import com.harrisonmoses.store.Dtos.ProductDto;
import com.harrisonmoses.store.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source="category.id", target= "categoryId" )
    ProductDto toProductDto(Product product);

}
