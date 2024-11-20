package org.example.microservscooter.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.service.ScooterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ScooterController.class)
public class ScooterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScooterService scooterService;

    private ScooterDTO scooterDto;

    @BeforeEach
    void setUp() {
        scooterDto = ScooterDTO.builder()
                .id_scooter(1L).
                latitude(10.0).
                longitude(20.0).
                kilometers(100L).
                usageTime(30).
                available(true).
                maintenance(false).
                id_parking(1L).
                build();
        ScooterDTO scooter2 = new ScooterDTO(2L, 15.0, 25.0, 200L, 40, true, true, 2L);
    }

    @Test
    void testGetAllScooter() throws Exception {
        when(scooterService.getAllScooter()).thenReturn(Collections.singletonList(scooterDto));

        mockMvc.perform(get("/api/scooter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_scooter").value(1L))
                .andExpect(jsonPath("$[0].latitude").value(10.0))
                .andExpect(jsonPath("$[0].longitude").value(20.0))
                .andExpect(jsonPath("$[0].kilometers").value(100L))
                .andExpect(jsonPath("$[0].usageTime").value(30));
    }

    @Test
    void testCreateScooter() throws Exception {
        ScooterDTO createdScooter =  ScooterDTO.builder()
                .id_scooter(2L).
                latitude(20.0).
                longitude(50.0).
                kilometers(100L).
                usageTime(30).
                available(true).
                maintenance(false).
                id_parking(1L).
                build();

        when(scooterService.createScooter(any())).thenReturn(createdScooter);

        mockMvc.perform(post("/api/scooter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createdScooter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_scooter").value(2L))
                .andExpect(jsonPath("$.latitude").value(20.0))
                .andExpect(jsonPath("$.longitude").value(50.0))
                .andExpect(jsonPath("$.kilometers").value(100L))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void testGetScooter() throws Exception {
        when(scooterService.getScooter(1L)).thenReturn(scooterDto);
        mockMvc.perform(get("/api/scooter/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_scooter").value(1L))
                .andExpect(jsonPath("$.latitude").value(10.0))
                .andExpect(jsonPath("$.longitude").value(20.0))
                .andExpect(jsonPath("$.kilometers").value(100L))
                .andExpect(jsonPath("$.usageTime").value(30));
    }

    @Test
    void testDeleteScooter() throws Exception {
        ScooterDTO scooterDto = ScooterDTO.builder()
                .id_scooter(1L)
                .latitude(10.0)
                .longitude(20.0)
                .kilometers(100L)
                .usageTime(30)
                .available(true)
                .maintenance(false)
                .id_parking(1L)
                .build();
        when(scooterService.delete(1L)).thenReturn(scooterDto);
        mockMvc.perform(delete("/api/scooter/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_scooter").value(1L))
                .andExpect(jsonPath("$.latitude").value(10.0))
                .andExpect(jsonPath("$.longitude").value(20.0));
        verify(scooterService, times(1)).delete(1L);
    }

    @Test
    void testGetScooterMaintenance() throws Exception {
        ScooterDTO scooterDTO = ScooterDTO.builder()
                .id_scooter(1L)
                .latitude(10.0)
                .longitude(20.0)
                .kilometers(100L)
                .usageTime(30)
                .available(true)
                .maintenance(false)
                .id_parking(1L)
                .build();

        when(scooterService.getScooterByMaintenance(1L)).thenReturn(Optional.of(scooterDTO));

        mockMvc.perform(get("/api/scooter/search-maintenance/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_scooter").value(1L));

        verify(scooterService, times(1)).getScooterByMaintenance(1L);
    }

    @Test
    void testStartMaintenance() throws Exception {
        ScooterDTO scooterDTO = ScooterDTO.builder()
                .id_scooter(1L)
                .maintenance(true)
                .build();

        when(scooterService.startMaintenance(1L)).thenReturn(scooterDTO);

        mockMvc.perform(put("/api/scooter/inicio-mantenimiento/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_scooter").value(1L))
                .andExpect(jsonPath("$.maintenance").value(true));

        verify(scooterService, times(1)).startMaintenance(1L);
    }

    @Test
    void testFinishMaintenance() throws Exception {
        ScooterDTO scooterDTO = ScooterDTO.builder()
                .id_scooter(1L)
                .maintenance(false)
                .build();

        when(scooterService.finishMaintenance(1L)).thenReturn(scooterDTO);

        mockMvc.perform(put("/api/scooter/fin-mantenimiento/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_scooter").value(1L))
                .andExpect(jsonPath("$.maintenance").value(false));

        verify(scooterService, times(1)).finishMaintenance(1L);
    }

    @Test
    void testUbicarScooterEnParada() throws Exception {
        ScooterDTO scooterDTO = ScooterDTO.builder()
                .id_scooter(1L)
                .id_parking(100L)
                .build();

        when(scooterService.ubicarScooterEnParada(1L, 100L)).thenReturn(scooterDTO);

        mockMvc.perform(put("/api/scooter/1/ubicar/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_scooter").value(1L))
                .andExpect(jsonPath("$.id_parking").value(100L));

        verify(scooterService, times(1)).ubicarScooterEnParada(1L, 100L);
    }

    @Test
    void testGetNearlyScooters() throws Exception {
        List<ScooterDTO> scooters = Arrays.asList(
                new ScooterDTO(1L, 10.0, 20.0, 100L, 30, true, false, 1L),
                new ScooterDTO(2L, 11.0, 21.0, 150L, 40, true, false, 2L)
        );
        when(scooterService.getNearlyScooters(10.0, 20.0, 5.0)).thenReturn(scooters);

        mockMvc.perform(get("/api/scooter/nearlyScooters/10.0/20.0/5.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_scooter").value(1L))
                .andExpect(jsonPath("$[1].id_scooter").value(2L));
    }

}
