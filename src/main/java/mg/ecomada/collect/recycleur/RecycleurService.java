package mg.ecomada.collect.recycleur;

import java.util.List;
import java.util.Map;

public interface RecycleurService {
    List<RecycleurDto> findAll();

    List<RecycleurDto> findByActif(Boolean actif);

    RecycleurDto findById(Long id);

    RecycleurDto create(RecycleurDto dto);

    RecycleurDto update(Long id, RecycleurDto dto);

    void delete(Long id);

    Map<String, Object> getPerformance(Long id);
}
