package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.security.ErrorCreatingJWTException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByEmailException;
import com.ocsoares.advancedcrudspringboot.domain.exceptions.user.InvalidUserByIdException;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class UpdateUserControllerTest {
    private static final String URI_WITH_INVALID_USER_ID = "/user/" + UUID.randomUUID();
    private static final UserDomainEntity updateUserDTO = new UserDomainEntity("Updated", "updated@gmail.com",
            "updated123"
    );
    private final UserDomainEntity createUserDTO = TestUtils.createUser();
    private String JSONUpdateUserDTO;
    private String token;
    private String createdUserId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepositoryGateway userRepositoryGateway;

    @Autowired
    private ITokenServiceGateway tokenServiceGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws InvalidUserByEmailException, ErrorCreatingJWTException, JsonProcessingException {
        this.createdUserId = TestUtils.createUserAndGetId(this.userRepositoryGateway, this.createUserDTO);
        this.JSONUpdateUserDTO = this.objectMapper.writeValueAsString(UpdateUserControllerTest.updateUserDTO);
        this.token = TestUtils.generateToken(this.userRepositoryGateway, this.tokenServiceGateway);
    }

    @AfterEach
    void down() {
        this.userRepositoryGateway.deleteAll();
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to update a user if forbidden request")
    void handle_Fail_WhenForbiddenRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch(UpdateUserControllerTest.URI_WITH_INVALID_USER_ID))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("It SHOULD NOT be possible update a user if it does not exists by id")
    void handle_Fail_WhenUserDoesNotExistsById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch(UpdateUserControllerTest.URI_WITH_INVALID_USER_ID)
                        .header("Authorization", "Bearer " + this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.JSONUpdateUserDTO))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.message").value(InvalidUserByIdException.EXCEPTION_MESSAGE));
    }

    @Test
    @DisplayName("It should be possible to update a user")
    void handle() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/user/" + this.createdUserId)
                        .header("Authorization", "Bearer " + this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.JSONUpdateUserDTO))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(""));

        UserDomainEntity userFoundAfterUpdating = this.userRepositoryGateway.findUserById(
                UUID.fromString(this.createdUserId)).orElseThrow(InvalidUserByIdException::new);

        var expectedUserAfterUpdating = new UserDomainEntity(UpdateUserControllerTest.updateUserDTO.name(),
                UpdateUserControllerTest.updateUserDTO.email(), userFoundAfterUpdating.password()
        );

        Assertions.assertEquals(userFoundAfterUpdating, expectedUserAfterUpdating);
    }
}