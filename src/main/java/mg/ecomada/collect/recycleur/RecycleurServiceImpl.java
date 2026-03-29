package mg.ecomada.collect.recycleur;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.dechet.typedechet.TypeDechetRepository;
import mg.ecomada.collect.depot.Depot;
import mg.ecomada.collect.depot.DepotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecycleurServiceImpl implements RecycleurService {
    private final RecycleurRepository repository;
    private final RecycleurMapper mapper;
    private final TypeDechetRepository typeDechetRepository;
    private final DepotRepository depotRepository;

    @Override
    public List<RecycleurDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<RecycleurDto> findByActif(Boolean actif) {
        return repository.findByActif(actif).stream().map(mapper::toDto).toList();
    }

    @Override
    public RecycleurDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Recycleur", id));
    }

    @Override
    public RecycleurDto create(RecycleurDto dto) {
        Recycleur entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public RecycleurDto update(Long id, RecycleurDto dto) {
        Recycleur entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recycleur", id));
        entity.setNom(dto.getNom());
        entity.setAdresse(dto.getAdresse());
        entity.setActif(dto.getActif());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Recycleur", id);
        repository.deleteById(id);
    }

    @Override
    public Map<String, Object> getPerformance(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Recycleur", id);
        List<Depot> depots = depotRepository.findByRecycleurId(id);
        long valorises = depots.stream().filter(d -> d.getStatus().getNom().equals("VALORISE")).count();
        double totalKg = depots.stream().mapToDouble(Depot::getPoidsKg).sum();
        Map<String, Double> parType = depots.stream()
                .collect(Collectors.groupingBy(d -> d.getTypeDechet().getNom(),
                        Collectors.summingDouble(Depot::getPoidsKg)));
        return Map.of("recycleurId", id, "totalDepots", depots.size(),
                "depotsValorises", valorises, "totalKg", totalKg,
                "volumesParType", parType);
    }
}
