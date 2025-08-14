package com.harrisonmoses.store.Dtos;

import lombok.Data;

@Data
public class CheckoutResponse {
    private Long id;
    private String checkoutUrl;

    public CheckoutResponse(Long id, String checkoutUrl) {
        this.id = id;
        this.checkoutUrl = checkoutUrl;
    }

}
