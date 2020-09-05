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
        nullObject.setEntityType("NullObject");
        nullObject.setActionType("Created");
        nullObject.setTitle(nullObject.getEntityType() + " " + nullObject.getActionType());
        nullObject.setDescription("");
        nullObject.setEntityId(0L);
        nullObject.setCreatedAt(LocalDateTime.now());
        nullObject.setServerAcceptedAt(LocalDateTime.MIN);
        log.info("****** nullEvent: " + nullObject);
        return nullObject;
    }

}
