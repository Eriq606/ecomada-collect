package mg.ecomada.collect.collecteur;

import java.util.List;

public interface CollecteurService {
    List<CollecteurDto> findAll();

    CollecteurDto findById(Long id);

    CollecteurDto create(CollecteurDto dto);

    CollecteurDto update(Long id, CollecteurDto dto);

    void delete(Long id);
}
