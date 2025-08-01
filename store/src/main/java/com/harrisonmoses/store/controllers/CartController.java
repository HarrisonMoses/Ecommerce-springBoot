package com.harrisonmoses.store.controllers;
import com.harrisonmoses.store.Dtos.AddCartItemRequest;
import com.harrisonmoses.store.Dtos.CartDto;
import com.harrisonmoses.store.Dtos.CartItemDto;
import com.harrisonmoses.store.Dtos.UpdateCartItemRequest;
import com.harrisonmoses.store.Exceptions.CartNotFoundException;
import com.harrisonmoses.store.Exceptions.ProductNotFoundException;
import com.harrisonmoses.store.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {


    private final CartService cartService;

    @PostMapping("")
    public ResponseEntity<CartDto> creatingCart(UriComponentsBuilder uriBuilder){
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
       return ResponseEntity.created(uri).body(cartDto);
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getCart(@PathVariable UUID id){
        return cartService.getCart(id);
    }


    @PostMapping("/{cartId}/items")
    public ResponseEntity<?> addToCart(@PathVariable UUID cartId,
                                            @Valid @RequestBody AddCartItemRequest request,
                                            UriComponentsBuilder uriBuilder){
        var item = cartService.addToCart(cartId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable UUID cartId,@PathVariable long itemId,
                                                      @RequestBody UpdateCartItemRequest request){
        return ResponseEntity.ok(cartService.updateCartItem(cartId,itemId,request));
    }

    @DeleteMapping("{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable UUID cartId,@PathVariable  long productId){
        return cartService.removeCartItem(cartId,productId);
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable UUID cartId){
        return cartService.deleteCart(cartId);
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<Map<String,String>> ProductNotFoundException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Product Not Found"));
    }

    @ExceptionHandler({CartNotFoundException.class})
    public ResponseEntity<Map<String,String>> CartNotFoundException(){
     return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart Not Found"));
    }


}
