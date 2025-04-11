package net.petstore.repository;

import net.petstore.domain.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomPetRepositoryImplTest {

    @Mock
    private RedisTemplate<Object, Object> redisTemplate;

    @Mock
    private SetOperations<Object, Object> setOperations;

    @Mock
    private ValueOperations<Object, Object> valueOperations;

    @InjectMocks
    private CustomPetRepositoryImpl customPetRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindCustomPetByTag() {
        // Arrange
        String tag = "dog";
        String key = "pet:tags.name:" + tag;
        Set<Object> ids = new HashSet<>(Arrays.asList(1, 2));
        Map<String, Object> petMap1 = new HashMap<>();
        petMap1.put("id", 1);
        petMap1.put("name", "Buddy");
        petMap1.put("tag", "dog");

        Map<String, Object> petMap2 = new HashMap<>();
        petMap2.put("id", 2);
        petMap2.put("name", "Max");
        petMap2.put("tag", "dog");

        when(redisTemplate.opsForSet()).thenReturn(setOperations);
        when(setOperations.members(key)).thenReturn(ids);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("pet:1")).thenReturn(petMap1);
        when(valueOperations.get("pet:2")).thenReturn(petMap2);

        // Act
        List<Pet> result = customPetRepository.findCustomPetByTag(tag);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Buddy", result.get(0).getName());
        assertEquals("Max", result.get(1).getName());

        verify(redisTemplate.opsForSet(), times(1)).members(key);
        verify(redisTemplate.opsForValue(), times(2)).get(anyString());
    }
}