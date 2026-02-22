package net.petstore.service;

import net.petstore.mapper.UserMapper;
import net.petstore.model.User;
import net.petstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    // domain entity (stored in Redis)
    private net.petstore.domain.User domainUser;

    // model DTO (returned by the API)
    private User modelUser;

    @BeforeEach
    void setUp() {
        domainUser = new net.petstore.domain.User();
        domainUser.setId(1L);
        domainUser.setUsername("john");
        domainUser.setFirstName("John");
        domainUser.setLastName("Doe");
        domainUser.setEmail("john@example.com");
        domainUser.setPassword("secret");

        modelUser = new User();
        modelUser.setId(1L);
        modelUser.setUsername("john");
        modelUser.setFirstName("John");
        modelUser.setLastName("Doe");
        modelUser.setEmail("john@example.com");
        modelUser.setPassword("secret");
    }

    // ─── createUser ─────────────────────────────────────────────────────────────

    @Test
    void createUser_shouldMapAndSave() {
        when(userMapper.toEntity(modelUser)).thenReturn(domainUser);

        userService.createUser(modelUser);

        verify(userMapper).toEntity(modelUser);
        verify(userRepository).save(domainUser);
    }

    // ─── getAllUsers ─────────────────────────────────────────────────────────────

    @Test
    void getAllUsers_shouldReturnAllMappedUsers() {
        when(userRepository.findAll()).thenReturn(List.of(domainUser));
        when(userMapper.toDto(domainUser)).thenReturn(modelUser);

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("john");
        verify(userRepository).findAll();
    }

    @Test
    void getAllUsers_whenEmpty_shouldReturnEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = userService.getAllUsers();

        assertThat(result).isEmpty();
    }

    // ─── getUserByName ───────────────────────────────────────────────────────────

    @Test
    void getUserByName_whenExists_shouldReturnMappedUser() {
        when(userRepository.findByUsername("john")).thenReturn(domainUser);
        when(userMapper.toDto(domainUser)).thenReturn(modelUser);

        User result = userService.getUserByName("john");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("john");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void getUserByName_whenNotExists_shouldReturnNull() {
        when(userRepository.findByUsername("nobody")).thenReturn(null);
        when(userMapper.toDto(null)).thenReturn(null);

        User result = userService.getUserByName("nobody");

        assertThat(result).isNull();
    }

    // ─── deleteUser ──────────────────────────────────────────────────────────────

    @Test
    void deleteUser_whenExists_shouldDeleteFromRepository() {
        when(userRepository.findByUsername("john")).thenReturn(domainUser);

        userService.deleteUser("john");

        verify(userRepository).delete(domainUser);
    }

    @Test
    void deleteUser_whenNotExists_shouldNotCallDelete() {
        when(userRepository.findByUsername("nobody")).thenReturn(null);

        userService.deleteUser("nobody");

        verify(userRepository, never()).delete(any());
    }

    // ─── updateUser ──────────────────────────────────────────────────────────────

    @Test
    void updateUser_whenExists_shouldUpdateFieldsAndSave() {
        User updatedModel = new User();
        updatedModel.setFirstName("Jane");
        updatedModel.setLastName("Smith");
        updatedModel.setEmail("jane@example.com");
        updatedModel.setPassword("newpass");

        when(userRepository.findByUsername("john")).thenReturn(domainUser);

        userService.updateUser("john", updatedModel);

        assertThat(domainUser.getFirstName()).isEqualTo("Jane");
        assertThat(domainUser.getLastName()).isEqualTo("Smith");
        assertThat(domainUser.getEmail()).isEqualTo("jane@example.com");
        assertThat(domainUser.getPassword()).isEqualTo("newpass");
        verify(userRepository).save(domainUser);
    }

    @Test
    void updateUser_whenNotExists_shouldNotSave() {
        when(userRepository.findByUsername("nobody")).thenReturn(null);

        userService.updateUser("nobody", modelUser);

        verify(userRepository, never()).save(any());
    }

    // ─── createUsersWithArrayInput / createUsersWithListInput ────────────────────

    @Test
    void createUsersWithArrayInput_shouldCreateEachUser() {
        when(userMapper.toEntity(any(User.class))).thenReturn(domainUser);

        userService.createUsersWithArrayInput(List.of(modelUser, modelUser));

        verify(userRepository, times(2)).save(domainUser);
    }

    @Test
    void createUsersWithListInput_shouldCreateEachUser() {
        when(userMapper.toEntity(any(User.class))).thenReturn(domainUser);

        userService.createUsersWithListInput(List.of(modelUser));

        verify(userRepository, times(1)).save(domainUser);
    }
}
