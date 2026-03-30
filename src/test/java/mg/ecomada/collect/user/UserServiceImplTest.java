package mg.ecomada.collect.user;

import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.depot.Depot;
import mg.ecomada.collect.depot.DepotRepository;
import mg.ecomada.collect.recompense.Recompense;
import mg.ecomada.collect.recompense.RecompenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DepotRepository depotRepository;

    @Mock
    private RecompenseRepository recompenseRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .nom("Test User")
                .email("test@example.com")
                .build();
    }

    @Test
    void getImpact_ShouldReturnCorrectStats() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);
        
        Depot depot1 = Depot.builder().poidsKg(10.0).build();
        Depot depot2 = Depot.builder().poidsKg(5.5).build();
        when(depotRepository.findByUserId(1L)).thenReturn(List.of(depot1, depot2));
        
        Recompense recompense = Recompense.builder().nom("Eco-Bronze").users(Collections.singleton(user)).build();
        when(recompenseRepository.findAll()).thenReturn(List.of(recompense));

        // Act
        Map<String, Object> impact = userService.getImpact(1L);

        // Assert
        assertEquals(1L, impact.get("userId"));
        assertEquals(2, impact.get("totalDepots"));
        assertEquals(15.5, impact.get("totalKg"));
        assertEquals(155L, impact.get("pointsCumules")); // 15.5 * 10
        assertTrue(((List<?>) impact.get("recompenses")).contains("Eco-Bronze"));
        
        verify(userRepository).existsById(1L);
        verify(depotRepository).findByUserId(1L);
    }

    @Test
    void getImpact_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getImpact(1L));
    }
}
