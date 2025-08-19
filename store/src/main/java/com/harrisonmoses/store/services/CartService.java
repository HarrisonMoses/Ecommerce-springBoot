package com.harrisonmoses.store.services;

import com.harrisonmoses.store.Dtos.AddCartItemRequest;
import com.harrisonmoses.store.Dtos.CartDto;
import com.harrisonmoses.store.Dtos.CartItemDto;
import com.harrisonmoses.store.Dtos.UpdateCartItemRequest;
import com.harrisonmoses.store.Entity.Cart;
import com.harrisonmoses.store.Entity.CartItem;

import com.harrisonmoses.store.Exceptions.CartItemNotFoundException;
import com.harrisonmoses.store.Exceptions.CartNotFoundException;
import com.harrisonmoses.store.Exceptions.ProductNotFoundException;

import com.harrisonmoses.store.Mappers.CartItemMapper;
import com.harrisonmoses.store.Mappers.CartMapper;
import com.harrisonmoses.store.repositories.CartItemRepository;
import com.harrisonmoses.store.repositories.CartRepository;
import com.harrisonmoses.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private  CartMapper cartMapper;
    private  CartRepository cartRepository;
    private  CartItemRepository cartItemRepository;
    private  CartItemMapper cartItemMapper;
    private  ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.createCart(cart);

    }


    public ResponseEntity<?> getCart(UUID id){
        var cart = cartRepository.findById(id).orElse(null);
        if(cart == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //Fetch the CartItems related to that Cart
        var items = cart.getCartItems().stream()
                .map(cartItemMapper::toDto)
                .toList();

        //Get the cart items list and add it to the cart List returned
        var cartDto = cartMapper.createCart(cart);
        cartDto.setItems(items);
        return ResponseEntity.ok(cartDto);
    }

    public CartItemDto addToCart(UUID cartId, AddCartItemRequest request){

        var cart = cartRepository.findById(cartId).orElse(null);
        if( cart == null){
           throw new CartNotFoundException();
        }
        var product = productRepository.findById(request.getProductId()).orElse(null);
        if(product == null){
            throw new ProductNotFoundException();
        }

        //Check if the product being added is already in the cart
        var cartItem = cart.getCartItems().stream()
                .filter(cartItemDto -> cartItemDto.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if( cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItemRepository.save(cartItem);
        }else{
            //create a cart Item
            cartItem = new CartItem();
            //set the productId and Cart on the CartItem
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(request.getQuantity()==0? 1:request.getQuantity());
        }
        //save the cartItem
        cartItemRepository.save(cartItem);

        //Serialize the CartItem
        return cartItemMapper.toDto(cartItem);
    }

    public CartItemDto updateCartItem(UUID cartId, long productId, UpdateCartItemRequest request){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        var cartItem = cart.getCartItems().stream()
                .filter(cartItemDto -> cartItemDto.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if( cartItem == null){
            throw new CartItemNotFoundException();
        }
        cartItem.setQuantity(request.getQuantity()==0? 1:request.getQuantity());
        cartItemRepository.save(cartItem);

        return cartItemMapper.toDto(cartItem);
    }


    public ResponseEntity<?> clearCart(UUID id){
        var cart = cartRepository.findById(id).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        cart.clear();
        cartRepository.save(cart);
        return ResponseEntity.noContent().build();
    }


    public ResponseEntity<?> removeCartItem(UUID id,long productId){
        var mycart =  cartRepository.findById(id).orElse(null);
        if(mycart == null){
            throw new CartNotFoundException();
        }
        var item =  mycart.getCartItems().stream().filter(cartItemDto -> cartItemDto.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if( item == null){
            throw new CartItemNotFoundException();
        }

        mycart.getCartItems().remove(item);
        cartItemRepository.delete(item);

        return ResponseEntity.noContent().build();
    }
}
