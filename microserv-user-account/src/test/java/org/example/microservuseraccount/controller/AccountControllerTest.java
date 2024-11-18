package org.example.microservuseraccount.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microservuseraccount.dto.AccountDto;
import org.example.microservuseraccount.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        accountDto = AccountDto.builder()
                .id(1L)
                .cuentaMP(12345L)
                .fechaAlta(new Date(2024 - 1900, 0, 10)) // El año en Date se ajusta con -1900
                .saldo(1000.0)
                .active(true)
                .users(Collections.emptyList())
                .build();
    }

    @Test
    void testGetAllAccounts() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(Collections.singletonList(accountDto));

        mockMvc.perform(get("/api/account")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].cuentaMP").value(12345L))
                .andExpect(jsonPath("$[0].saldo").value(1000.0))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].users").isEmpty());
    }

    @Test
    void testCreateAccount() throws Exception {
        AccountDto createdAccount = AccountDto.builder()
                .id(2L)
                .cuentaMP(67890L)
                .fechaAlta(new Date())
                .saldo(500.0)
                .active(true)
                .users(Collections.emptyList())
                .build();

        when(accountService.createAccount(any())).thenReturn(createdAccount);

        mockMvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createdAccount)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.cuentaMP").value(67890L))
                .andExpect(jsonPath("$.saldo").value(500.0))
                .andExpect(jsonPath("$.active").value(true));
    }

    // Test para anular una cuenta
    @Test
    void testAnularCuenta() throws Exception {
        AccountDto updatedAccount = AccountDto.builder()
                .id(1L)
                .cuentaMP(12345L)
                .fechaAlta(new Date())
                .saldo(1000.0)
                .active(false)
                .users(Collections.emptyList())
                .build();

        when(accountService.anularCuenta(1L)).thenReturn(updatedAccount);

        mockMvc.perform(put("/api/account/anular/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.active").value(false));
    }

    // Test para eliminar una cuenta
    @Test
    void testDeleteAccount() throws Exception {
        doNothing().when(accountService).deleteAccount(1L);

        mockMvc.perform(delete("/api/account/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Cuenta eliminada id: 1"));
    }
}
