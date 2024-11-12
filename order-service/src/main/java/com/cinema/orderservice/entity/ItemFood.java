package com.cinema.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "item_food")
@Data
@NoArgsConstructor
public class ItemFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "itemFood")
    private List<CinemaItemFood> cinemaItemFoods;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "ncc_id", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "itemFood")
    private List<OrderFoodItem> orderFoodItems;
}
