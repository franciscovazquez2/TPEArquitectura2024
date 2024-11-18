package org.example.microservuseraccount.controller;

import org.example.microservuseraccount.dto.AccountDto;
import org.example.microservuseraccount.dto.UserDto;
import org.example.microservuseraccount.entity.Authority;
import org.example.microservuseraccount.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .cuentaMP(1L)
                .fechaAlta(2024,01,10)
                .saldo(1000)
                .active(true)
                .users(Collections.emptyList())
                .build();
    }

    @Test
    void testGetAllAccount() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(Arrays.asList(accountDto));

        mockMvc.perform(get("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].fechaAlta").value("nicolas"))
                .andExpect(jsonPath("$[0].saldo").value("papaleo"))
                .andExpect(jsonPath("$[0].active").value("admin"))
                .andExpect(jsonPath("$[0].users[0].id").value("admin"));
    }
}
