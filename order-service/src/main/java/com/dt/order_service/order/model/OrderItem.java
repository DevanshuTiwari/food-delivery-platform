package com.dt.order_service.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menuItemId;
    private Integer quantity;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY) // Many order items belong to one order.
    @JoinColumn(name = "order_id")
    private Order order;
}
