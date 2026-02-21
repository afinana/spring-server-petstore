package net.petstore.api;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.petstore.model.User;
import net.petstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/v2")
public class UserApiController implements UserApi {

    private final UserService userService;


    @RequestMapping(value = "/user",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@Parameter(description = "Created user object", required = true) @Valid @RequestBody User body) {
        userService.createUser(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/createWithArray",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    public ResponseEntity<Void> createUsersWithArrayInput(@Parameter(description = "List of user object", required = true) @Valid @RequestBody List<User> body) {
        userService.createUsersWithListInput(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/createWithList",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    public ResponseEntity<Void> createUsersWithListInput(@Parameter(description = "List of user object", required = true) @Valid @RequestBody List<User> body) {
        userService.createUsersWithListInput(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{username}",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@Parameter(description = "The name that needs to be deleted", required = true) @PathVariable("username") String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{username}",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    public ResponseEntity<User> getUserByName(@Parameter(description = "The name that needs to be fetched. Use user1 for testing.", required = true) @PathVariable("username") String username) {
        User user = userService.getUserByName(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/login",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    public ResponseEntity<String> loginUser(@NotNull @Parameter(description = "The user name for login", required = true) @Valid @RequestParam(value = "username", required = true) String username,
                                            @NotNull @Parameter(description = "The password for login in clear text", required = true) @Valid @RequestParam(value = "password", required = true) String password) {
        // Login is not yet implemented
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/user/logout",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    public ResponseEntity<Void> logoutUser() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/user/{username}",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@Parameter(description = "name that need to be updated", required = true) @PathVariable("username") String username,
                                           @Parameter(description = "Updated user object", required = true) @Valid @RequestBody User body) {
        userService.updateUser(username, body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
