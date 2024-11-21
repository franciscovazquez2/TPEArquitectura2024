package org.example.microservtravel.controller;
import org.example.microservtravel.dto.TravelDto;
import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.service.TravelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TravelController.class)
public class TravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelService travelService;

    @BeforeEach
    public void setup() {
        TravelDto travel = TravelDto.builder()
                .id_viaje(1L)
                .id_user(1L)
                .id_scooter(1L)
                .price(100L)
                .build();

        when(travelService.getTravel(1L)).thenReturn(travel);

        when(travelService.getAllTravels()).thenReturn(List.of(
                TravelDto.builder().id_viaje(1L).id_user(1L).id_scooter(1L).price(100L).build(),
                TravelDto.builder().id_viaje(2L).id_user(2L).id_scooter(2L).price(200L).build()
        ));
    }

    @Test
    public void testGetAllTravels() throws Exception {
        when(travelService.getAllTravels()).thenReturn(List.of(
                TravelDto.builder().id_viaje(1L).id_user(1L).id_scooter(1L).price(100L).build(),
                TravelDto.builder().id_viaje(2L).id_user(2L).id_scooter(2L).price(200L).build()
        ));

        mockMvc.perform(get("/api/travel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // si tengo 2 elemtnos
                .andExpect(jsonPath("$[0].id_viaje").value(1))
                .andExpect(jsonPath("$[1].id_viaje").value(2));
    }

    @Test
    public void testGetTravel() throws Exception {
        TravelDto travel = TravelDto.builder()
                .id_viaje(1L)
                .id_user(1L)
                .id_scooter(1L)
                .price(100L)
                .build();

        when(travelService.getTravel(1L)).thenReturn(travel);

        mockMvc.perform(get("/api/travel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_viaje").value(1))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    public void testCreateTravel() throws Exception {
        TravelDto createdTravel = TravelDto.builder()
                .id_viaje(1L)
                .id_user(1L)
                .id_scooter(1L)
                .price(100L)
                .build();

        Travel newTravel = Travel.builder()
                .id_user(1L)
                .id_scooter(1L)
                .price(100L)
                .build();

        when(travelService.createTravel(newTravel)).thenReturn(createdTravel);

        mockMvc.perform(post("/api/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id_user\": 1, \"id_scooter\": 1, \"price\": 100.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_viaje").value(1))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    public void testDeleteByID() throws Exception {
        doNothing().when(travelService).delete(1L);

        mockMvc.perform(delete("/api/travel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("El viaje fue eliminado correctamente"))
                .andExpect(jsonPath("$.details").value("id eliminado: 1"))
                .andExpect(jsonPath("$.status").value("OK"));
    }
}
