package net.petstore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.petstore.model.User;
import net.petstore.service.PetService;
import net.petstore.service.UserService;
import net.petstore.security.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Slice tests for UserApiController.
 * SecurityConfig permits all requests, so no authentication setup is needed.
 * PetService is mocked to prevent Spring from trying to load MongoDB repositories
 * that are not available in a WebMvcTest slice.
 */
@WebMvcTest(UserApiController.class)
@Import(SecurityConfig.class)
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // Mocked to avoid MongoDB repository wiring in the web slice
    @MockBean
    @SuppressWarnings("unused")
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPassword("secret");
    }

    // ─── GET /v2/user ────────────────────────────────────────────────────────────

    @Test
    void getAllUsers_shouldReturn200WithList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/v2/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("john"))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getAllUsers_whenNoUsers_shouldReturnEmptyList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/v2/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ─── GET /v2/user/{username} ──────────────────────────────────────────────────

    @Test
    void getUserByName_whenExists_shouldReturn200() throws Exception {
        when(userService.getUserByName("john")).thenReturn(user);

        mockMvc.perform(get("/v2/user/john").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getUserByName_whenNotFound_shouldReturn200WithNullBody() throws Exception {
        // Controller always returns 200 regardless — null body is serialized as empty JSON
        when(userService.getUserByName("nobody")).thenReturn(null);

        mockMvc.perform(get("/v2/user/nobody").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ─── POST /v2/user ───────────────────────────────────────────────────────────

    @Test
    void createUser_shouldReturn200() throws Exception {
        doNothing().when(userService).createUser(any(User.class));

        mockMvc.perform(post("/v2/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userService).createUser(any(User.class));
    }

    // ─── POST /v2/user/createWithArray ────────────────────────────────────────────

    @Test
    void createUsersWithArrayInput_shouldReturn200() throws Exception {
        doNothing().when(userService).createUsersWithListInput(anyList());

        mockMvc.perform(post("/v2/user/createWithArray")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(user))))
                .andExpect(status().isOk());

        verify(userService).createUsersWithListInput(anyList());
    }

    // ─── POST /v2/user/createWithList ─────────────────────────────────────────────

    @Test
    void createUsersWithListInput_shouldReturn200() throws Exception {
        doNothing().when(userService).createUsersWithListInput(anyList());

        mockMvc.perform(post("/v2/user/createWithList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(user))))
                .andExpect(status().isOk());

        verify(userService).createUsersWithListInput(anyList());
    }

    // ─── DELETE /v2/user/{username} ───────────────────────────────────────────────

    @Test
    void deleteUser_shouldReturn200() throws Exception {
        doNothing().when(userService).deleteUser("john");

        mockMvc.perform(delete("/v2/user/john"))
                .andExpect(status().isOk());

        verify(userService).deleteUser("john");
    }

    // ─── PUT /v2/user/{username} ──────────────────────────────────────────────────

    @Test
    void updateUser_shouldReturn200() throws Exception {
        doNothing().when(userService).updateUser(eq("john"), any(User.class));

        mockMvc.perform(put("/v2/user/john")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq("john"), any(User.class));
    }

    // ─── GET /v2/user/login ───────────────────────────────────────────────────────

    @Test
    void loginUser_shouldReturn501NotImplemented() throws Exception {
        // login is not yet implemented — controller always returns NOT_IMPLEMENTED
        mockMvc.perform(get("/v2/user/login")
                        .param("username", "john")
                        .param("password", "secret")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotImplemented());
    }

    // ─── GET /v2/user/logout ──────────────────────────────────────────────────────

    @Test
    void logoutUser_shouldReturn501NotImplemented() throws Exception {
        mockMvc.perform(get("/v2/user/logout").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotImplemented());
    }
}

