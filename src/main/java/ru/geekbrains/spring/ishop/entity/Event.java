package ru.geekbrains.spring.ishop.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "events")
@Getter
@Setter
@Slf4j
public class Event extends AbstractEntity {
    public static Event emptyObject = initEmptyObject();
    public enum Fields {id, actionType, entityType, entityId, createdAt, serverAcceptedAt}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "action_type_id")
    @NotNull(message = "не может быть пустым")
    private ActionType actionType;

    @Column(name = "entity_type")
    @NotNull(message = "не может быть пустым")
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "server_accepted_at")
    private LocalDateTime serverAcceptedAt;

    @Tolerate
    public Event() {
    }

    private static Event initEmptyObject() {
        emptyObject = Event.builder()
                .id(0L)
                .actionType(ActionType.emptyObject)
                .entityType("")
                .entityId(0L)
                .createdAt(LocalDateTime.now())
                .serverAcceptedAt(LocalDateTime.now())
                .build();
        return emptyObject;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", actionType=" + actionType +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                ", createdAt=" + createdAt +
                ", serverAcceptedAt=" + serverAcceptedAt +
                '}';
    }

}
