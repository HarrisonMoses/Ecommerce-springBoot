package com.harrisonmoses.store.controllers;
import com.harrisonmoses.store.Dtos.AddCartItemRequest;
import com.harrisonmoses.store.Dtos.CartDto;
import com.harrisonmoses.store.Dtos.CartItemDto;
import com.harrisonmoses.store.Dtos.UpdateCartItemRequest;
import com.harrisonmoses.store.Entity.Cart;
import com.harrisonmoses.store.Entity.Cartitem;
import com.harrisonmoses.store.Mappers.CartItemMapper;
import com.harrisonmoses.store.Mappers.CartMapper;
import com.harrisonmoses.store.repositories.CartItemRepository;
import com.harrisonmoses.store.repositories.CartRepository;
import com.harrisonmoses.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ProductRepository productRepository;

    @PostMapping("")
    public ResponseEntity<CartDto> creatingCart(UriComponentsBuilder uriBuilder){
        var cart = new Cart();
        cartRepository.save(cart);

        var mappedCart = cartMapper.createCart(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(mappedCart.getId()).toUri();

       return ResponseEntity.created(uri).body(mappedCart);
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getCart(@PathVariable UUID id){
        //Check for the validity of the cart
        var cart = cartRepository.findById(id).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        //Fetch the CartItems related to that Cart
        var productlist = cartItemRepository.findAllByCart(cart);
        List<CartItemDto> items = productlist.stream()
                .map(cartItemMapper::toDto)
                .toList();

        //Get the cart items list and add it to the cart List returned
        var mappedCart = cartMapper.createCart(cart);
        mappedCart.setItems(items);

        return ResponseEntity.ok(mappedCart);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<?> addProductToCart(@PathVariable UUID cartId,
                                            @Valid @RequestBody AddCartItemRequest request,
                                            UriComponentsBuilder uriBuilder){

        var cart = cartRepository.findById(cartId).orElse(null);
        var product = productRepository.findById(request.getProductId()).orElse(null);
        if( cart == null || product == null ){
            return ResponseEntity.notFound().build();
        }

        //Check if the product being added is already in the cart
        var ExistingItem = cartItemRepository.findByCartAndProduct(cart, product);
        if( ExistingItem != null ){
            ExistingItem.setQuantity(ExistingItem.getQuantity() + 1);
            cartItemRepository.save(ExistingItem);
            var mappedCartItem = cartItemMapper.toDto(ExistingItem);
            return ResponseEntity.ok(mappedCartItem);
        }

        //create a cart Item
        var cartItem = new Cartitem();

        //set the productId and Cart on the CartItem
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(request.getQuantity()==0? 1:request.getQuantity());

        //save the cartItem
        cartItemRepository.save(cartItem);

        //Serialize the CartItem
        var mappedCartItem = cartItemMapper.toDto(cartItem);
        var item_uri = uriBuilder.path("/carts/{cartId}/items/{itemId}")
                .buildAndExpand(cart.getId(), cartItem.getId())
                .toUri();

        return ResponseEntity.created(item_uri).body(mappedCartItem);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable long itemId, @RequestBody UpdateCartItemRequest request){
        var cartItem = cartItemRepository.findById(itemId).orElse(null);
        if(cartItem == null){
            return ResponseEntity.notFound().build();
        }
        cartItem.setQuantity(request.getQuantity()==0? 1:request.getQuantity());
        cartItemRepository.save(cartItem);

        var mappedCartItem = cartItemMapper.toDto(cartItem);

        return ResponseEntity.ok(mappedCartItem);
    }

    @DeleteMapping("{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable  long productId){
        var cartItem =  cartItemRepository.findById(productId).orElse(null);
        if(cartItem == null){
            return ResponseEntity.notFound().build();
        }
        cartItemRepository.delete(cartItem);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        cartRepository.delete(cart);
        return ResponseEntity.noContent().build();
    }


}
