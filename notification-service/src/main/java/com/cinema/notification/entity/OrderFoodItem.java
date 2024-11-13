package com.cinema.notification.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
}
