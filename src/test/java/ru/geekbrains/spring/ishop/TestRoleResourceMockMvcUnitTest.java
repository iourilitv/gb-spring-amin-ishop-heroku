package ru.geekbrains.spring.ishop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.repository.RoleRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class TestRoleResourceMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void givenName_whenGetExistingRole_thenStatus200andRoleReturned() throws Exception {
        Role role = new Role("NEW_ROLE", "Description of new role");

        Mockito.when(roleRepository.findRoleByName(Mockito.any())).thenReturn(Optional.of(role));

        mockMvc.perform(
                get("/role/NEW_ROLE/roleName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NEW_ROLE"))
                .andExpect(jsonPath("$.description").value("Description of new role"));
    }

}
// TODO после запуска выдает эту ошибку - не понятно причем здесь AddressService!!!
//Parameter 0 of method setRepository in ru.geekbrains.spring.ishop.service.AddressService required a bean of type 'ru.geekbrains.spring.ishop.repository.AddressRepository' that could not be found.