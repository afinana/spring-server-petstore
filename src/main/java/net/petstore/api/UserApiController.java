package net.petstore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.petstore.model.User;
import net.petstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserApiController implements UserApi {

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final UserService userService;


    public ResponseEntity<Void> createUser(@Parameter(description = "Created user object", required = true) @Valid @RequestBody User body) {
        userService.createUser(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createUsersWithArrayInput(@Parameter(description = "List of user object", required = true) @Valid @RequestBody List<User> body) {
        userService.createUsersWithListInput(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> createUsersWithListInput(@Parameter(description = "List of user object", required = true) @Valid @RequestBody List<User> body) {
        userService.createUsersWithListInput(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(@Parameter(description = "The name that needs to be deleted", required = true) @PathVariable("username") String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<User> getUserByName(@Parameter(description = "The name that needs to be fetched. Use user1 for testing.", required = true) @PathVariable("username") String username) {
        User user = userService.getUserByName(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<String> loginUser(@NotNull @Parameter(description = "The user name for login", required = true) @Valid @RequestParam(value = "username", required = true) String username,
                                            @NotNull @Parameter(description = "The password for login in clear text", required = true) @Valid @RequestParam(value = "password", required = true) String password) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<>(objectMapper.readValue("{  \"blank\": true,  \"bytes\": [],  \"empty\": true}", String.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (accept != null && accept.contains("application/xml")) {
            try {
                return new ResponseEntity<>(objectMapper.readValue("aeiou", String.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/xml", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> logoutUser() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateUser(@Parameter(description = "name that need to be updated", required = true) @PathVariable("username") String username,
                                           @Parameter(description = "Updated user object", required = true) @Valid @RequestBody User body) {
        userService.updateUser(username, body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
