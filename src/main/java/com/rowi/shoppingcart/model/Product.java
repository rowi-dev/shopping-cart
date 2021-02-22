package com.rowi.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="carton_size")
    private Integer cartonSize;
    @Column(name="carton_price")
    private BigDecimal cartonPrice;
    @Column(name="variable_cost_ratio")
    private BigDecimal variableCostRatio;
    @Column(name="discount_level")
    private Integer discountLevel;
    @Column(name="discount")
    private BigDecimal discount;
}
