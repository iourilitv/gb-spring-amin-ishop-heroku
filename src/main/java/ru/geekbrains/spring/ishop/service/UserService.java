package ru.geekbrains.spring.ishop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.service.interfaces.IUserService;
import ru.geekbrains.spring.ishop.utils.SystemUser;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.repository.RoleRepository;
import ru.geekbrains.spring.ishop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.utils.filters.UserFilter;
import ru.geekbrains.spring.ishop.utils.filters.UtilFilter;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private AddressService addressService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UtilFilter utilFilter;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    //TODO переделать: вводить первого юзера
    @Override
    @Transactional
    @PostConstruct
    public void initSuperAdmin() {
        if (findByUserName("superadmin") != null) {
            return;
        }
        User user = new User("superadmin", passwordEncoder.encode("superadmin"),
                "superadmin first_name", "superadmin last_name",
                "+79991234567", "superadmin@mail.com");
        user.setDeliveryAddress(addressService.findById(1L));
        user.setRoles(roleRepository.findAll());
        userRepository.save(user);
    }

    @Override
    public Page<User> findAll(UserFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return userRepository.findAll(filter.getSpec(), pageRequest);
    }

    @Override
    @Transactional
    public User findById(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepository.findOneByUserName(username);
    }

    @Override
    @Transactional
    public boolean save(SystemUser systemUser) {
        User user = new User();

        if (findByUserName(systemUser.getUserName()) != null) {
            return false;
        }

        user.setUserName(systemUser.getUserName());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setPhoneNumber(systemUser.getPhoneNumber());
        user.setEmail(systemUser.getEmail());
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public void updatePassword(String userName, String newPassword) {
        User user = userRepository.findOneByUserName(userName);
        if(user == null) {
            return;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateDeliveryAddress(User user, Address deliveryAddress) {
        User theUser = userRepository.getOne(user.getId());
        Address address = addressService.getAddressByUserDeliveryAddress(deliveryAddress);
        //если совпадают все поля, кроме id, адреса в БД и у пользователя
        if(address != null) {
            //выходим без изменений, ввиду их отсутствия
            return;
        }
        addressService.save(deliveryAddress);
        theUser.setDeliveryAddress(deliveryAddress);
        userRepository.save(theUser);
    }

    @Override
    @Transactional
    public void updateFirstName(User user, String first_name) {
        User theUser = userRepository.getOne(user.getId());
        if(theUser.getFirstName().equals(first_name)) {
            return;
        }
        theUser.setFirstName(first_name);
        userRepository.save(theUser);
    }

    @Override
    @Transactional
    public void updateLastName(User user, String last_name) {
        User theUser = userRepository.getOne(user.getId());
        if(theUser.getLastName().equals(last_name)) {
            return;
        }
        theUser.setLastName(last_name);
        userRepository.save(theUser);
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void addRoleToUser(Long user_id, Short role_id) {
        User theUser = userRepository.findById(user_id).orElseThrow(RuntimeException::new);
        theUser.getRoles().add(roleRepository.getOne(role_id));
        userRepository.save(theUser);
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long user_id, Short role_id) {
        User theUser = userRepository.findById(user_id).orElseThrow(RuntimeException::new);
        theUser.getRoles().remove(roleRepository.getOne(role_id));
        userRepository.save(theUser);
    }

    @Override
    @Transactional
    public List<Role> getRemainingAvailableRoles(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(RuntimeException::new);
        List<Role> allRoles = roleRepository.findAll();
        allRoles.removeAll(user.getRoles());
        allRoles.remove(roleRepository.findRoleByName("ROLE_SUPERADMIN"));
        return allRoles;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findOneByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

