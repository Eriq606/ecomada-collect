package mg.ecomada.collect.recompense;

import java.util.List;

public interface RecompenseService {
    List<RecompenseDto> findAll();

    RecompenseDto findById(Long id);

    RecompenseDto create(RecompenseDto dto);

    RecompenseDto update(Long id, RecompenseDto dto);

    void delete(Long id);

    void attribuer(Long userId, Long recompenseId);
}
