package com.harrisonmoses.store.services;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class WebhookRequest {
    String payload;
    Map<String,String> signature;

}
