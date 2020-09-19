package ru.geekbrains.spring.ishop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", " +
                "name='" + name + '\'' +
                "description='" + description + '\'' +
                '}';
    }
}
