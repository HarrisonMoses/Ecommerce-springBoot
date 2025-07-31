package com.harrisonmoses.store.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "datecreated", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart",fetch = FetchType.EAGER)
    private Set<Cartitem> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Cartitem item : cartItems) {
            BigDecimal calculatedTotalPrice = item.getProduct()
                            .getPrice().multiply(BigDecimal
                            .valueOf(item.getQuantity()));

            totalPrice = totalPrice.add(calculatedTotalPrice);
        }
        return totalPrice;
    }

}