package com.cinema.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_food_item")
@Data
@NoArgsConstructor
public class OrderFoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_food_id", nullable = false)
    private OrderFood orderFood;

    @ManyToOne
    @JoinColumn(name = "item_food_id", nullable = false)
    private ItemFood itemFood;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit-price")
    private Double unitPrice;
}
