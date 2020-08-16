package ru.geekbrains.spring.ishop.service.interfaces;

import org.springframework.data.domain.Page;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.utils.SystemUser;
import ru.geekbrains.spring.ishop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.spring.ishop.utils.filters.UserFilter;

import javax.annotation.PostConstruct;
import java.util.List;

public interface IUserService extends UserDetailsService {
    @PostConstruct
    void initSuperAdmin();

    Page<User> findAll(UserFilter userFilter, String id);

    User findById(Long user_id);

    User findByUserName(String username);

    boolean save(SystemUser systemUser);

    void updatePassword(String userName, String newPassword);

    void updateDeliveryAddress(User user, Address deliveryAddress);

    void updateFirstName(User user, String first_name);

    void updateLastName(User user, String last_name);

    void delete(User user);

    List<Role> getAllRoles();

    void addRoleToUser(Long user_id, Short role_id);

    void removeRoleFromUser(Long user_id, Short role_id);

    List<Role> getRemainingAvailableRoles(Long user_id);

}
