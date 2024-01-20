package com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.ITokenServiceGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.security.PasswordHasherGateway;
import com.ocsoares.advancedcrudspringboot.application.gateways.user.IUserRepositoryGateway;
import com.ocsoares.advancedcrudspringboot.domain.entity.UserDomainEntity;
import com.ocsoares.advancedcrudspringboot.infrastructure.controllers.user.dtos.LoginDTO;
import com.ocsoares.advancedcrudspringboot.utils.TestUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
@ActiveProfiles("test")
class LoginUserControllerTest {
    private static final String URI = "/auth/login";
    private UserDomainEntity createdUserDatabase;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepositoryGateway userRepositoryGateway;

    @Autowired
    private ITokenServiceGateway tokenServiceGateway;

    @Autowired
    private PasswordHasherGateway passwordHasherGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.createdUserDatabase = TestUtils.createUserDatabase(this.userRepositoryGateway);
    }

    @AfterEach
    void down() {
        this.userRepositoryGateway.deleteAll();
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to login user if it does not exists by email")
    void handle_Fail_WhenUserDoesNotExistsByEmail() throws Exception {
        var loginDTO = new LoginDTO("anyemail@gmail.com", this.createdUserDatabase.password());
        String JSONLoginDTO = this.objectMapper.writeValueAsString(loginDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post(LoginUserControllerTest.URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONLoginDTO))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bad credentials"));
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to login user if password is invalid")
    void handle_Fail_WhenUserPasswordIsInvalid() throws Exception {
        var loginDTO = new LoginDTO(this.createdUserDatabase.email(), "any_password123");
        String JSONLoginDTO = this.objectMapper.writeValueAsString(loginDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post(LoginUserControllerTest.URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONLoginDTO))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bad credentials"));
    }

    @Test
    @DisplayName("It SHOULD NOT be possible to login user if it does not exists by email and password is invalid")
    void handle_Fail_WhenUserByEmailDoesNotExistsAndPasswordIsInvalid() throws Exception {
        var loginDTO = new LoginDTO("anyemail@gmail.com", "any_password123");
        String JSONLoginDTO = this.objectMapper.writeValueAsString(loginDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post(LoginUserControllerTest.URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONLoginDTO))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bad credentials"));
    }

    @Test
    @DisplayName("It should be possible to login user")
    void handle() throws Exception {
        // Tive que criar um Usuário com a Senha HASHEADA porque se não dá ERRO!!!!
        String userPassword = "test123";
        String hashedPassword = this.passwordHasherGateway.hash(userPassword);

        var userDTO = new UserDomainEntity("test", "test@gmail.com", hashedPassword);
        UserDomainEntity createdUser = this.userRepositoryGateway.createUser(userDTO);

        var loginDTO = new LoginDTO(createdUser.email(), userPassword);
        String JSONLoginDTO = this.objectMapper.writeValueAsString(loginDTO);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(LoginUserControllerTest.URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONLoginDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andReturn();

        String token = new JSONObject(mvcResult.getResponse().getContentAsString()).get("token").toString();

        Assertions.assertEquals(this.tokenServiceGateway.validateToken(token), createdUser.email());
    }
}