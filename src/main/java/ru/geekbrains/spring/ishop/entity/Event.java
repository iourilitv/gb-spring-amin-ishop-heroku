package ru.geekbrains.spring.ishop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //TODO сделать сущностью ActionType
    @Column(name = "action_type")
    @NotNull(message = "не может быть пустым")
    private String actionType;

    @Column(name = "title")
    @NotNull(message = "не может быть пустым")
    private String title;

    @Column(name = "description")
    @NotNull(message = "не может быть пустым")
    private String description;

    //TODO сделать сущностью EntityType
    @Column(name = "entity_type", unique = true)
    @NotNull(message = "не может быть пустым")
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "created_at")
    @CreationTimestamp
    //TIMESTAMP NOT NULL DEFAULT NOW(),
    private LocalDateTime createdAt;

    @Column(name = "server_accepted_at")
    private LocalDateTime serverAcceptedAt;

}
