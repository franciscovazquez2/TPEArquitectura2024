package org.example.microservbilling.controller;

import jakarta.ws.rs.core.MediaType;
import org.example.microservbilling.dto.FeeDto;
import org.example.microservbilling.entity.Fee;
import org.example.microservbilling.services.FeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeeController.class)
public class FeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeeService feeService;

    private FeeDto feeDto;

    @BeforeEach
    void setUp() {
        feeDto = FeeDto.builder()
                .id(1L)
                .monto(100.00)
                .fechaInicio(LocalDate.of(2024, 1, 10))
                .tipo("normal")
                .build();
    }

    @Test
    void testGetAllFees() throws Exception {
        List<FeeDto> fees = Arrays.asList(new FeeDto(1, 100.0, LocalDate.now(), "normal"));
        when(feeService.getAllFees()).thenReturn(fees);

        mockMvc.perform(get("/api/fee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].monto").value(100.0))
                .andExpect(jsonPath("$[0].tipo").value("normal"));
    }

    @Test
    void testGetFeeById() throws Exception {
        FeeDto feeDto = new FeeDto(1, 100.0, LocalDate.now(), "normal");
        when(feeService.getFee(1L)).thenReturn(feeDto);
        mockMvc.perform(get("/api/fee/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(100.0))
                .andExpect(jsonPath("$.tipo").value("normal"));
    }


    @Test
    void testCreateNormalFee() throws Exception {
        Fee newFee = new Fee(1, 100.0, LocalDate.now(), "normal");
        FeeDto feeDto = new FeeDto(1, 100.0, LocalDate.now(), "normal");
        when(feeService.createFee(any(Fee.class))).thenReturn(feeDto);

        mockMvc.perform(post("/api/fee/normalFee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"monto\": 100.0, \"fechaInicio\": \"2024-01-01\", \"tipo\": \"normal\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.monto").value(100.0))
                .andExpect(jsonPath("$.tipo").value("normal"));
    }

    @Test
    void testCreateExtraFee() throws Exception {
        FeeDto feeDto = new FeeDto(2, 150.0, LocalDate.now(), "extra");
        when(feeService.createFee(any(Fee.class))).thenReturn(feeDto);

        mockMvc.perform(post("/api/fee/extraFee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"monto\": 150.0, \"fechaInicio\": \"2024-01-01\", \"tipo\": \"extra\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.monto").value(150.0))
                .andExpect(jsonPath("$.tipo").value("extra"));
    }
    @Test
    void testDeleteFee() throws Exception {
        feeDto = FeeDto.builder()
                .id(1L)
                .monto(100.00)
                .fechaInicio(LocalDate.of(2024, 1, 10))
                .tipo("normal")
                .build();
        when(feeService.delteFee(1L)).thenReturn(feeDto);
        mockMvc.perform(delete("/api/fee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Tarifa eliminada: 1"));
        verify(feeService, times(1)).delteFee(1L);
    }

}
