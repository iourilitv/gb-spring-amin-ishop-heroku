package ru.geekbrains.spring.ishop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category extends AbstractEntity {
    //TODO добавить в таблицу и AbstractEntity поля createdAt, updatedAt
    public enum Fields {id, title, products}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "title", unique = true)
    private String title;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", products=" + products +
                '}';
    }


}
