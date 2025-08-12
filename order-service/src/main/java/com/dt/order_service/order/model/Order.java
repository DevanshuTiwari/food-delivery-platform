package com.dt.order_service.order.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private Long restaurantId;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    private LocalDateTime orderDate;
    private String status; // e.g., PENDING, CONFIRMED, DELIVERED, CANCELLED

    public void addOrderItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
