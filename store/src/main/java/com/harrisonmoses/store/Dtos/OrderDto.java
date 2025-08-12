package com.harrisonmoses.store.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harrisonmoses.store.Entity.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String status;
    @JsonFormat(pattern = "yyy-MM-dd")
    private LocalDate createdAt;
    private List<OrderItemDto> Items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
