package ru.geekbrains.spring.ishop.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "order_statuses")
@Data
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "title", unique = true)
    @NotNull(message = "Fill this field!")
    @Size(min = 1, max = 255, message = "Number of symbols should be between 5 to 255!")
    private String title;

    @Column(name = "description")
    @NotNull(message = "Fill this field!")
    @Size(max = 5000, message = "Maximum 5000 symbols is allowed!")
    private String description;

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
