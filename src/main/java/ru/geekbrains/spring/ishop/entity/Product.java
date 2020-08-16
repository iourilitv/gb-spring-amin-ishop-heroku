package ru.geekbrains.spring.ishop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @NotNull(message = "category: категория не выбрана")
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "vendor_code")
    @NotNull(message = "vendorCode: не может быть пустым")
    @Pattern(regexp = "([0-9]+)", message = "vendorCode: недопустимый символ")
    @Size(min = 8, max = 8, message = "vendorCode: требуется 8 числовых символов")
    private String vendorCode;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "product")
    private List<ProductImage> images;

    @Column(name = "title", unique = true)
    @NotNull(message = "title: не может быть пустым")
    @Size(min = 5, max = 250, message = "title: требуется минимум 5 символов")
    private String title;

    @Column(name = "price")
    @NotNull(message = "price: не может быть пустым")
    @DecimalMin(value = "0.01", message = "price: минимальное значение 0")
    @Digits(integer = 19, fraction = 2)
    private BigDecimal price;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryId=" + category.getId() +
                ", vendorCode='" + vendorCode + '\'' +
                ", images=" + images +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
