package org.example.microservbilling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microservbilling.dto.BillingDto;
import org.example.microservbilling.dto.TotalFacturadoDto;
import org.example.microservbilling.services.BillingService;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillingController.class)
public class BillingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillingService billingService;

    private BillingDto billingDto;

    @BeforeEach
    void setUp() {
        billingDto = BillingDto.builder()
                .id(1L)
                .fechaEmision(java.time.LocalDate.of(2024, 1, 10))
                .idReserva(1L)
                .idUsuario(1L)
                .montoTotal(100.00)
                .build();
    }


    @Test
    void testGetAllBillings() throws Exception {
        when(billingService.getAllBillings()).thenReturn(Collections.singletonList(billingDto));

        mockMvc.perform(get("/api/billing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].idReserva").value(1L))
                .andExpect(jsonPath("$[0].idUsuario").value(1L))
                .andExpect(jsonPath("$[0].montoTotal").value(100.00));
    }

    @Test
    void testGetBillingById() throws Exception {
        when(billingService.getBilling(1L)).thenReturn(billingDto);
        mockMvc.perform(get("/api/billing/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.idReserva").value(1L))
                .andExpect(jsonPath("$.idUsuario").value(1L))
                .andExpect(jsonPath("$.montoTotal").value(100.00));

        verify(billingService, times(1)).getBilling(1L);
    }

    @Test
    void testCreateBilling() throws Exception {
        BillingDto createdBilling = BillingDto.builder()
                .id(1L)
                .fechaEmision(java.time.LocalDate.of(2024, 1, 10))
                .idReserva(1L)
                .idUsuario(1L)
                .montoTotal(100.00)
                .build();

        when(billingService.createBilling(any())).thenReturn(createdBilling);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mockMvc.perform(post("/api/billing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdBilling)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.idReserva").value(1L))
                .andExpect(jsonPath("$.idUsuario").value(1L))
                .andExpect(jsonPath("$.montoTotal").value(100.00));
    }

    @Test
    void testDeleteBilling() throws Exception {
        BillingDto billingDto1 = BillingDto.builder()
                .id(1L)
                .fechaEmision(java.time.LocalDate.of(2024, 1, 10))
                .idReserva(1L)
                .idUsuario(1L)
                .montoTotal(100.00)
                .build();

        when(billingService.deleteBilling(1L)).thenReturn(billingDto1);
        mockMvc.perform(delete("/api/billing/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.idUsuario").value(1L))
                .andExpect(jsonPath("$.idReserva").value(1L))
                .andExpect(jsonPath("$.montoTotal").value(100.00));

        verify(billingService, times(1)).deleteBilling(1L);
    }

    @Test
    void testReporteTotalFacturadoEnFecha() throws Exception {
        TotalFacturadoDto totalFacturadoDto = new TotalFacturadoDto(500.00, 2024, 1, 12);
        when(billingService.reporteTotalFacturadoEnFecha(2024, 1, 12)).thenReturn(totalFacturadoDto);

        mockMvc.perform(get("/api/billing/totalFacturado/2024/1/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalFacturado").value(500.00))
                .andExpect(jsonPath("$.year").value(2024))
                .andExpect(jsonPath("$.startMonth").value(1))
                .andExpect(jsonPath("$.endMonth").value(12));

        verify(billingService, times(1)).reporteTotalFacturadoEnFecha(2024, 1, 12);
    }
}
