package ru.geekbrains.spring.ishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.ishop.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {
    Optional<Role> findRoleByName(String name);
}
