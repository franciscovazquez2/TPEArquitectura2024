package org.example.microservparking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microservparking.dto.ParkingDto;
import org.example.microservparking.services.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParkingController.class)
public class ParkingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingService parkingService;

    private ParkingDto parkingDto;

    @BeforeEach
    void setUp() {
        parkingDto = ParkingDto.builder()
                .id(1L)
                .latitude(10)
                .longitude(20)
                .capacity(10)
                .actualCapacity(4)
                .available(true)
                .build();
    }


    @Test
    void testGetAllParking() throws Exception {
        when(parkingService.getAllParkings()).thenReturn(Collections.singletonList(parkingDto));

        mockMvc.perform(get("/api/parking")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].latitude").value(10))
                .andExpect(jsonPath("$[0].longitude").value(20))
                .andExpect(jsonPath("$[0].capacity").value(10))
                .andExpect(jsonPath("$[0].actualCapacity").value(4))
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    void testCreateParking() throws Exception {
        ParkingDto createdParking =  ParkingDto.builder()
                .id(1L)
                .latitude(10)
                .longitude(20)
                .capacity(10)
                .actualCapacity(4)
                .available(true)
                .build();

        when(parkingService.createParking(any())).thenReturn(createdParking);

        mockMvc.perform(post("/api/parking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createdParking)))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10))
                .andExpect(jsonPath("$.longitude").value(20))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.actualCapacity").value(4))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void testDeleteParking() throws Exception {
        when(parkingService.deleteParking(1L)).thenReturn(parkingDto);
        mockMvc.perform(delete("/api/parking/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10))
                .andExpect(jsonPath("$.longitude").value(20))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.actualCapacity").value(4))
                .andExpect(jsonPath("$.available").value(true));
        verify(parkingService, times(1)).deleteParking(1L);
    }

    @Test
    void testGetParkingByScooter() throws Exception {
        when(parkingService.getParking(1L)).thenReturn(parkingDto);

        mockMvc.perform(get("/api/parking/{id}/estacionar", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10))
                .andExpect(jsonPath("$.longitude").value(20))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.actualCapacity").value(4))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void testOcuparParking() throws Exception {
        parkingDto.setAvailable(false);
        when(parkingService.ocuparEstacionamiento(1L)).thenReturn(parkingDto);

        mockMvc.perform(put("/api/parking/{id}/ocupada", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10))
                .andExpect(jsonPath("$.longitude").value(20))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.actualCapacity").value(4))
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void testLiberarEstacionamiento() throws Exception {
        parkingDto.setActualCapacity(3);
        when(parkingService.liberarEstacionamiento(1L)).thenReturn(parkingDto);

        mockMvc.perform(put("/api/parking/{id}/liberarEstacionamiento", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10))
                .andExpect(jsonPath("$.longitude").value(20))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.actualCapacity").value(3))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void testGetParkingById() throws Exception {
        when(parkingService.getParking(1L)).thenReturn(parkingDto);

        mockMvc.perform(get("/api/parking/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.latitude").value(10))
                .andExpect(jsonPath("$.longitude").value(20))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.actualCapacity").value(4))
                .andExpect(jsonPath("$.available").value(true));
    }
}
