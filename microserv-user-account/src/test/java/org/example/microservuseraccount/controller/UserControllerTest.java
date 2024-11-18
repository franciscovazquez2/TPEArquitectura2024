package org.example.microservuseraccount.controller;
import org.example.microservuseraccount.dto.UserCreateDTO;
import org.example.microservuseraccount.entity.Authority;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.example.microservuseraccount.dto.UserDto;
import org.example.microservuseraccount.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        Authority authority = new Authority();
        authority.setName("admin");

        userDto = UserDto.builder()
                .id(1L)
                .nombre("nicolas")
                .apellido("papaleo")
                .telefono("123456789")
                .accounts(Collections.emptyList())
                .rol(Collections.singletonList(authority))
                .build();
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(userDto));

        mockMvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("nicolas"))
                .andExpect(jsonPath("$[0].apellido").value("papaleo"))
                .andExpect(jsonPath("$[0].rol[0].name").value("admin"));
    }

    /*
    // Test para crear un usuario
    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(UserCreateDTO.class))).thenReturn(userDto);
        String json = """
        {
            "nombre": "nicolas",
            "apellido": "papaleo",
            "email": "nicolas@mail.com",
            "telefono": "123456789",
            "user": "nicouser",
            "password": "password123",
            "rol": [
                {
                    "name": "admin"
                }
            ]
        }
        """;

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("nicolas"))
                .andExpect(jsonPath("$.apellido").value("papaleo"))
                .andExpect(jsonPath("$.rol[0].name").value("admin"));
    }
    */

    //Test para obtener un usuario
    @Test
    void testGetUser() throws Exception {
        when(userService.getUser(1L)).thenReturn(userDto);

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("nicolas"))
                .andExpect(jsonPath("$.apellido").value("papaleo"))
                .andExpect(jsonPath("$.rol[0].name").value("admin"));
    }

    //test para asociar una cuenta
    @Test
    void testAsociarCuenta() throws Exception {
        when(userService.asociarCuenta(1L, 1L)).thenReturn(userDto);

        mockMvc.perform(put("/api/user/asociarCuenta/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("nicolas"))
                .andExpect(jsonPath("$.apellido").value("papaleo"))
                .andExpect(jsonPath("$.rol[0].name").value("admin"));
    }

}
