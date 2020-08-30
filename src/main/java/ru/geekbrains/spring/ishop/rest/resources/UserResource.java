package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.rest.converters.UserToOutUserConverter;
import ru.geekbrains.spring.ishop.rest.outentities.OutUser;
import ru.geekbrains.spring.ishop.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource extends AbstractResource {

    private UserService userService;

    private UserToOutUserConverter userConverter;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserConverter(UserToOutUserConverter userConverter) {
        this.userConverter = userConverter;
    }

    /**
     * Без него по умолчанию вернет код ошибки 500 - ошибка на сервере
     * @param id - user id
     * @return - json объект OutUser или если нет, объект исключения NotFoundException "404, "The User with id=99 is not found!""
     */
    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutUser findById(@PathVariable("id") long id) {
        return userConverter.convert(userService.findByIdOptional(id));
    }

}
