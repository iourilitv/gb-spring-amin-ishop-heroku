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
                .title("NO TITLE")
                .description("NO DESCRIPTION")
                .entityType("NO ENTITY TYPE")
                .build();
        return nullObject;
    }
}
