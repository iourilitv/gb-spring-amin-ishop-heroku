package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource extends AbstractResource {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //FIXME
//    @GetMapping(path = "/{id}/id", produces = MediaType.APPLICATION_JSON_VALUE)
//    public OutUser findById(@PathVariable("id") long id) {
//        return userConverter.convert(userService.findByIdOptional(id));
//    }

}
