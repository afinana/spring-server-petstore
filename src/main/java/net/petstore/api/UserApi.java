/**
 * Petstore User API
 */
package net.petstore.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.petstore.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Validated
@Tag(name = "user", description = "the user API")
@RequestMapping(value = "/v2")
public interface UserApi {

    // declare getAllUsers as default method to avoid breaking existing implementations of UserApi
    @Operation(summary = "Get all users", description = "Returns all users from the system that the user has access to", tags={ "user" })
    @RequestMapping(value = "/user",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<List<User>> getAllUsers();

    @Operation(summary = "Create user", description = "This can only be done by the logged in user.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
    @RequestMapping(value = "/user",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createUser(@Parameter(description = "Created user object", required = true) @Valid @RequestBody User body);

    @Operation(summary = "Creates list of users with given input array")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
    @RequestMapping(value = "/user/createWithArray",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createUsersWithArrayInput(@Parameter(description = "List of user object", required = true) @Valid @RequestBody List<User> body);

    @Operation(summary = "Creates list of users with given input list")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
    @RequestMapping(value = "/user/createWithList",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createUsersWithListInput(@Parameter(description = "List of user object", required = true) @Valid @RequestBody List<User> body);

    @Operation(summary = "Delete user", description = "This can only be done by the logged in user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/user/{username}",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUser(@Parameter(description = "The name that needs to be deleted", required = true) @PathVariable("username") String username);

    @Operation(summary = "Get user by user name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/user/{username}",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<User> getUserByName(@Parameter(description = "The name that needs to be fetched. Use user1 for testing.", required = true) @PathVariable("username") String username);

    @Operation(summary = "Logs user into the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid username/password supplied") })
    @RequestMapping(value = "/user/login",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<String> loginUser(@NotNull @Parameter(description = "The user name for login", required = true) @Valid @RequestParam(value = "username", required = true) String username,
                                     @NotNull @Parameter(description = "The password for login in clear text", required = true) @Valid @RequestParam(value = "password", required = true) String password);

    @Operation(summary = "Logs out current logged in user session")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation") })
    @RequestMapping(value = "/user/logout",
        produces = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    ResponseEntity<Void> logoutUser();

    @Operation(summary = "Updated user", description = "This can only be done by the logged in user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid user supplied"),
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/user/{username}",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateUser(@Parameter(description = "name that need to be updated", required = true) @PathVariable("username") String username,
                                    @Parameter(description = "Updated user object", required = true) @Valid @RequestBody User body);
}
