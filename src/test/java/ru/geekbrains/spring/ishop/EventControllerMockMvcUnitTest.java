package ru.geekbrains.spring.ishop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.spring.ishop.GbSpringIShopLysApplication;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.repository.ActionTypeRepository;
import ru.geekbrains.spring.ishop.repository.EventRepository;
import ru.geekbrains.spring.ishop.service.EventService;
import ru.geekbrains.spring.ishop.utils.EntityTypes;


import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class EventControllerMockMvcUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EventService eventService;
//    @MockBean
//    private EventRepository eventRepository;
//    @MockBean
//    private ActionTypeRepository actionTypeRepository;

    @Test
    public void givenEvent_whenAdd_thenStatusCreatedAndEventReturned() throws Exception {

        Event event = Event.builder()
                .actionType(ActionType.builder()
                        .title(ActionType.Titles.CREATED.name())
                        .description(
                                eventService.findActionTypeByTitle(ActionType.Titles.CREATED.name())
                                        .getDescription())
                        .entityType(EntityTypes.Order.name())
                        .build())
                .entityType(EntityTypes.Order.name())
                .entityId(1L)
                .createdAt(LocalDateTime.now())
                .serverAcceptedAt(LocalDateTime.now())
                .build();
        Mockito.when(eventService.save(Mockito.any())).thenReturn(event);

        mockMvc.perform(
                post("/api/v1/event/save/incoming/string")
                        .content(objectMapper.writeValueAsString(event))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(event)));
    }
//    @Test
//    public void givenEvent_whenAdd_thenStatusCreatedAndEventReturned() throws Exception {
//
//        Event event = Event.builder()
//                .actionType(ActionType.builder()
//                        .title(ActionType.Titles.CREATED.name())
//                        .description(
//                                actionTypeRepository.getActionTypeByTitleEquals(ActionType.Titles.CREATED.name())
//                                        .orElseThrow(() ->
//                                                new NotFoundException("ActionType with title=" +
//                                                        ActionType.Titles.CREATED.name() + " is not found!"))
//                                        .getDescription())
//                        .entityType(EntityTypes.Order.name())
//                        .build())
//                .entityType(EntityTypes.Order.name())
//                .entityId(1L)
//                .createdAt(LocalDateTime.now())
//                .serverAcceptedAt(LocalDateTime.now())
//                .build();
//        Mockito.when(eventRepository.save(Mockito.any())).thenReturn(event);
//
//        mockMvc.perform(
//                post("/api/v1/event/save/incoming/string")
//                        .content(objectMapper.writeValueAsString(event))
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(event)));
//    }

}