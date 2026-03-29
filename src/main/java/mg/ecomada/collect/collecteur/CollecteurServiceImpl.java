package mg.ecomada.collect.collecteur;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CollecteurServiceImpl implements CollecteurService {
    private final CollecteurRepository repository;
    private final CollecteurMapper mapper;

    @Override
    public List<CollecteurDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public CollecteurDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Collecteur", id));
    }

    @Override
    public CollecteurDto create(CollecteurDto dto) {
        Collecteur entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public CollecteurDto update(Long id, CollecteurDto dto) {
        Collecteur entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collecteur", id));
        entity.setNom(dto.getNom());
        entity.setTelephone(dto.getTelephone());
        entity.setActif(dto.getActif());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Collecteur", id);
        repository.deleteById(id);
    }
}
