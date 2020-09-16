package ru.geekbrains.spring.ishop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@Entity
@Table(name = "action_types")
public class ActionType {
    public static ActionType nullObject = initNullObject();
    public enum Fields {id, title, description, entityType}
    public enum Titles {CREATED, STATUS_CHANGED, LOGGED_ID, LOGGED_OUT, ADDED_TO_CART, REMOVED_FROM_CART}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "title", unique = true)
    @NotNull(message = "не может быть пустым")
    private String title;

    @Column(name = "description")
    @NotNull(message = "не может быть пустым")
    private String description;

    @Column(name = "entity_type")
    @NotNull(message = "не может быть пустым")
    private String entityType;

    @Tolerate
    public ActionType() {
    }

    private static ActionType initNullObject() {
        nullObject = ActionType.builder()
                .id((short)0)
                .title("")
                .description("")
                .entityType("")
                .build();
        return nullObject;
    }

    @Override
    public String toString() {
        return "ActionType{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", entityType='" + entityType + '\'' +
                '}';
    }
}
