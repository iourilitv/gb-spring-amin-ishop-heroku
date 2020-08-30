package ru.geekbrains.spring.ishop.rest.outentities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutUser extends AbstractOutEntity {
    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String outDeliveryAddress;

    //TODO add to the parent entity
//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;

}
