package ru.geekbrains.spring.ishop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;

//@Entity
//@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type", unique = true)
    @NotNull(message = "не может быть пустым")
    private String type;

    @Column(name = "title")
    @NotNull(message = "не может быть пустым")
    private String title;

    @Column(name = "description")
    @NotNull(message = "не может быть пустым")
    private String description;

    @Column(name = "entity")
    private Object entity;

    @Column(name = "delivery_status")
    @NotNull(message = "не может быть пустым")
    private String deliveryStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    //TIMESTAMP NOT NULL DEFAULT NOW(),
    private LocalDateTime createdAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

}
