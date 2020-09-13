package ru.geekbrains.spring.ishop.entity;

import lombok.*;
import lombok.experimental.Tolerate;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "events")
@Getter
@Setter
@Slf4j
public class Event {
    public static Event nullObject = initNullObject();
    public enum Issuers {STORE, SYSTEM}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //TODO сделать сущностью ActionType
//    @Column(name = "action_type")
//    @NotNull(message = "не может быть пустым")
//    private String actionType;
    @ManyToOne
    @JoinColumn(name = "action_type_id")
    @NotNull(message = "не может быть пустым")
    private ActionType actionType;

//    @Column(name = "title")
//    @NotNull(message = "не может быть пустым")
//    private String title;

//    @Column(name = "description")
//    @NotNull(message = "не может быть пустым")
//    private String description;

    @Column(name = "issuer")
    @NotNull(message = "не может быть пустым")
    private String issuer; //TODO заменить на сущность. Значения: "STORE"(default); "SYSTEM".

    @Column(name = "issuer_event_id")
    private Long issuerEventId; //TODO set default value=0L

    @Column(name = "entity_type")
    @NotNull(message = "не может быть пустым")
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId; //TODO set default value=0L

//    @Column(name = "created_at")
//    @CreationTimestamp
//    //TIMESTAMP NOT NULL DEFAULT NOW(),
//    private LocalDateTime createdAt;
//
//    @Column(name = "server_accepted_at")
//    private LocalDateTime serverAcceptedAt;
    @Column(name = "issuer_created_at")
    @CreationTimestamp
    //TIMESTAMP NOT NULL DEFAULT NOW(),
    private LocalDateTime issuerCreatedAt;

    @Column(name = "recipient_accepted_at")
    private LocalDateTime recipientAcceptedAt;

    @Tolerate
    public Event() {
    }

//    private static Event initNullObject() {
//        nullObject = Event.builder()
//            .id(0L)
//            .actionType("NO ACTION TYPE")
//            .title("NO ENTITY TYPE. NO ACTION TYPE")
//            .description("NO DESCRIPTION")
//            .entityType("NO ENTITY TYPE")
//            .entityId(0L)
//            .createdAt(LocalDateTime.now())
//            .build();
//        return nullObject;
//    }
    private static Event initNullObject() {
        nullObject = Event.builder()
                .id(0L)
                .actionType(ActionType.nullObject)
                .issuer("NO ISSUER")
                .issuerEventId(0L)
                .entityType("NO ENTITY TYPE")
                .entityId(0L)
                .issuerCreatedAt(LocalDateTime.now())
                .recipientAcceptedAt(LocalDateTime.now())
                .build();
        return nullObject;
    }

//    @Override
//    public String toString() {
//        return "Event{" +
//                "id=" + id +
//                ", actionType='" + actionType + '\'' +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", entityType='" + entityType + '\'' +
//                ", entityId=" + entityId +
//                ", createdAt=" + createdAt +
//                ", serverAcceptedAt=" + serverAcceptedAt +
//                '}';
//    }
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", actionType=" + actionType +
                ", issuer='" + issuer + '\'' +
                ", issuerEventId=" + issuerEventId +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                ", issuerCreatedAt=" + issuerCreatedAt +
                ", recipientAcceptedAt=" + recipientAcceptedAt +
                '}';
    }
}
