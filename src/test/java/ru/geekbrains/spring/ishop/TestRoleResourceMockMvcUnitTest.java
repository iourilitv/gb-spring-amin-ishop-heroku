package ru.geekbrains.spring.ishop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.repository.RoleRepository;
import ru.geekbrains.spring.ishop.rest.resources.TestRoleResource;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class TestRoleResourceMockMvcUnitTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private TestRoleResource testRoleResource;
    @MockBean
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        objectMapper.registerModule(new JavaTimeModule());
        testRoleResource = new TestRoleResource(roleRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(testRoleResource).alwaysDo(print()).build();
    }

    @Test
    public void givenName_thenStatus200andRoleReturned() throws Exception {
        mockMvc.perform(
                get("/role/NEW_ROLE/roleName/new"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NEW_ROLE"))
                .andExpect(jsonPath("$.description").value("Description of new role"));
    }

    @Test
    public void givenName_whenGetExistingRole_thenStatus200andRoleReturned() throws Exception {
        String name = "ROLE_ADMIN";
        String description = "Администратор интернет-магазина. Доступ ко всем разделам магазина. Нет прав на создание и изменение администраторов";
        Role role = new Role(name, description);
        //иммитируем объект в репозитории - без него NotFoundException
        Mockito.when(roleRepository.findRoleByName(Mockito.any())).thenReturn(Optional.of(role));

        mockMvc.perform(
                get("/role/" + name + "/roleName/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.description").value(description));
    }

}