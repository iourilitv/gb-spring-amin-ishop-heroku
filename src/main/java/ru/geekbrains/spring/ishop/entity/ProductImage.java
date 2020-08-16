package ru.geekbrains.spring.ishop.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "products_images")
@Data
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "path")
    private String path;

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", productId=" + product.getId() +
                ", path='" + path + '\'' +
                '}';
    }
}

