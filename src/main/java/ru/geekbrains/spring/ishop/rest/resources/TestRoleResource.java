package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.RoleRepository;

@RestController
@RequestMapping("/role")
public class TestRoleResource {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/{roleName}/roleName")
    public ResponseEntity<Role> getRoleById(@PathVariable("roleName") String roleName) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
                roleRepository.findRoleByName(roleName).orElseThrow(() -> new NotFoundException("Role with name=" + roleName + " is not found!")));
    }
}
