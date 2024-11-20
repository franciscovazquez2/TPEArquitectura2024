package org.example.microservmaintenance.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microservmaintenance.dto.MaintenanceDTO;
import org.example.microservmaintenance.dto.scooter.ScooterDTO;
import org.example.microservmaintenance.entity.Maintenance;
import org.example.microservmaintenance.http.response.MaintenanceScooterResponse;
import org.example.microservmaintenance.services.MaintenanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaintenanceController.class)
public class MaintenanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaintenanceService maintenanceService;

    private MaintenanceDTO maintenanceDTO;

    @BeforeEach
    void setUp() {
        maintenanceDTO = MaintenanceDTO.builder()
                .id(1L).
                id_scooter(1L).
                fecha_mantenimiento(new Date(2024,01,10)).
                finalizado(true).
                build();
    }

    @Test
    void testGetAllMaintenances() throws Exception {
        List<Maintenance> maintenanceList = Collections.singletonList(
                Maintenance.builder()
                        .id(1L)
                        .idScooter(1L)
                        .fecha_inicio(new Date(2024, 1, 10))
                        .finalizado(true)
                        .build()
        );
        when(maintenanceService.getAllMaintenances()).thenReturn(maintenanceList);
        mockMvc.perform(get("/api/maintenance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].idScooter").value(1L))
                .andExpect(jsonPath("$[0].finalizado").value(true));
    }


    @Test
    void testCreateMaintenance() throws Exception {
        MaintenanceDTO createdMaintenance = MaintenanceDTO.builder()
                .id(1L).
                id_scooter(1L).
                fecha_mantenimiento(new Date(2024,01,10)).
                finalizado(true).
                build();

        when(maintenanceService.createMaintenance(any())).thenReturn(createdMaintenance);

        mockMvc.perform(post("/api/maintenance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createdMaintenance)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.id_scooter").value(1L))
                .andExpect(jsonPath("$.finalizado").value(true));
    }

    @Test
    void testGetMaintenanceAndScooter() throws Exception {
        ScooterDTO scooterDTO = ScooterDTO.builder()
                .id_scooter(1L)
                .latitude(10.0)
                .longitude(20.0)
                .kilometers(100L)
                .usageTime(120)
                .maintenence(true)
                .build();

        MaintenanceScooterResponse response = MaintenanceScooterResponse.builder()
                .id(1L)
                .scooter(scooterDTO)
                .build();
        when(maintenanceService.getMaintenance(1L)).thenReturn(response);

        mockMvc.perform(get("/api/maintenance/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.scooter.id_scooter").value(1L))
                .andExpect(jsonPath("$.scooter.latitude").value(10.0))
                .andExpect(jsonPath("$.scooter.longitude").value(20.0))
                .andExpect(jsonPath("$.scooter.kilometers").value(100L))
                .andExpect(jsonPath("$.scooter.usageTime").value(120))
                .andExpect(jsonPath("$.scooter.maintenence").value(true));
    }

    @Test
    void testDeleteMaintenanceSuccess() throws Exception {
        doNothing().when(maintenanceService).deleteMaintenance(1L);

        mockMvc.perform(delete("/api/maintenance/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Mantenimiento eliminado id: 1"));
    }

}
