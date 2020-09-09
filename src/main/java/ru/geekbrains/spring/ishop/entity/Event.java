package ru.geekbrains.spring.ishop.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@Slf4j
public class Event {
    public static Event nullObject = initNullObject();

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
    private Long entityId; //TODO set default value=0L

    @Column(name = "created_at")
    @CreationTimestamp
    //TIMESTAMP NOT NULL DEFAULT NOW(),
    private LocalDateTime createdAt;

    @Column(name = "server_accepted_at")
    private LocalDateTime serverAcceptedAt;

//    @PostConstruct //TODO Why it does not work?!
    private static Event initNullObject() {
        nullObject = new Event();
        nullObject.setId(0L);
        nullObject.setEntityType("NO ENTITY TYPE");
        nullObject.setActionType("NO ACTION TYPE");
        nullObject.setTitle(nullObject.getEntityType() + " " + nullObject.getActionType());
        nullObject.setDescription("NO DESCRIPTION");
        nullObject.setEntityId(0L);
        nullObject.setCreatedAt(LocalDateTime.now());
        log.info("****** nullEvent: " + nullObject);
        return nullObject;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", actionType='" + actionType + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                ", createdAt=" + createdAt +
                ", serverAcceptedAt=" + serverAcceptedAt +
                '}';
    }
}
