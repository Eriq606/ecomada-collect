package mg.ecomada.collect.depot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface DepotService {
    Page<DepotDto> findAll(Long villeId, Long statusId, Long typeDechetId,
                           LocalDateTime dateMin, LocalDateTime dateMax, Pageable pageable);

    DepotDto findById(Long id);

    DepotDto create(DepotDto dto);

    DepotDto updateStatus(Long id, Long statusId, Long collecteurId, Long recycleurId);

    void delete(Long id);
}
