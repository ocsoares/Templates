package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.UserAlreadyExistsByEmailException;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Para NÃO dar CONFLITO de Portas
@AutoConfigureMockMvc() // "addFilters = false" dentro dos () = vai DESABILITAR Filtros como Autenticação, por ex.
@ActiveProfiles("test")
class CreateUserControllerTest {
    private static final String URI = "/auth/register";
    private static final UserDomainEntity userDTO = TestUtils.createUser();
    private String JSONUserDTO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepositoryGateway userRepositoryGateway;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // Transforma o Objeto para o formato JSON!!!
        this.JSONUserDTO = TestUtils.JSONUserDTO();
    }

    @AfterEach
    void down() {
        this.userRepositoryGateway.deleteAll();
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to create a user if it already exists by email")
    void handle_Fail_WhenUserAlreadyExistsByEmail() throws Exception {
        this.userRepositoryGateway.createUser(CreateUserControllerTest.userDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post(CreateUserControllerTest.URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.JSONUserDTO))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value(UserAlreadyExistsByEmailException.EXCEPTION_MESSAGE));
    }

    @Test
    @DisplayName("It should be possible to create a user")
    void handle() throws Exception {
        String expectedUserResponse = TestUtils.expectedUserResponse();

        this.mockMvc.perform(MockMvcRequestBuilders.post(CreateUserControllerTest.URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.JSONUserDTO))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedUserResponse));
    }
}
