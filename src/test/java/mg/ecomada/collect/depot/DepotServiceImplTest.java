package mg.ecomada.collect.depot;

import mg.ecomada.collect.collecteur.CollecteurRepository;
import mg.ecomada.collect.common.exception.BusinessException;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.dechet.status.Status;
import mg.ecomada.collect.dechet.status.StatusRepository;
import mg.ecomada.collect.dechet.typedechet.TypeDechetRepository;
import mg.ecomada.collect.geo.pointcollecte.PointCollecteRepository;
import mg.ecomada.collect.recycleur.RecycleurRepository;
import mg.ecomada.collect.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepotServiceImplTest {

    @Mock
    private DepotRepository repository;
    @Mock
    private DepotMapper mapper;
    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private DepotServiceImpl service;

    private Depot depot;
    private Status statusEnregistre;
    private Status statusCollecte;

    @BeforeEach
    void setUp() {
        statusEnregistre = Status.builder().id(1L).nom("ENREGISTRE").rang(1).build();
        statusCollecte = Status.builder().id(2L).nom("COLLECTE").rang(2).build();
        depot = Depot.builder().id(1L).status(statusEnregistre).build();
    }

    @Test
    void updateStatus_ShouldSuccess_WhenRangIsHigher() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(depot));
        when(statusRepository.findById(2L)).thenReturn(Optional.of(statusCollecte));
        when(repository.save(any(Depot.class))).thenReturn(depot);
        when(mapper.toDto(any())).thenReturn(new DepotDto());

        // Act
        service.updateStatus(1L, 2L, null, null);

        // Assert
        verify(repository).save(depot);
        assertEquals(statusCollecte, depot.getStatus());
    }

    @Test
    void updateStatus_ShouldThrowException_WhenRangIsLowerOrEqual() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(depot));
        when(statusRepository.findById(1L)).thenReturn(Optional.of(statusEnregistre));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
                () -> service.updateStatus(1L, 1L, null, null));
        assertTrue(exception.getMessage().contains("Le statut ne peut passer qu'a un rang superieur"));
    }

    @Test
    void updateStatus_ShouldThrowException_WhenValoriseWithoutRecycleur() {
        // Arrange
        Status statusValorise = Status.builder().id(4L).nom("VALORISE").rang(4).build();
        when(repository.findById(1L)).thenReturn(Optional.of(depot));
        when(statusRepository.findById(4L)).thenReturn(Optional.of(statusValorise));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, 
                () -> service.updateStatus(1L, 4L, null, null));
        assertTrue(exception.getMessage().contains("Un recycleur doit etre specifie"));
    }
}
