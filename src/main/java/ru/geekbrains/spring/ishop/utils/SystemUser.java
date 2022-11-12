package ru.geekbrains.spring.ishop.utils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.validation.FieldMatch;

@Data
@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
public class SystemUser {
    @NotNull(message = "not null check")
    @Size(min = 3, message = "username length must be greater than 3 symbols")
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastName;

    @NotNull(message = "is required")
    private String phoneNumber;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Email
    private String email;

    public SystemUser() {
    }

    public SystemUser(User user) {
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }
}
