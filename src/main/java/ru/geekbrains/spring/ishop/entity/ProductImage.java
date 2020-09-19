package ru.geekbrains.spring.ishop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products_images")
@Getter
@Setter
public class ProductImage extends AbstractEntity {
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

