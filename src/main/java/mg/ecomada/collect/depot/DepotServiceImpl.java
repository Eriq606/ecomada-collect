package mg.ecomada.collect.depot;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.collecteur.CollecteurRepository;
import mg.ecomada.collect.common.exception.BusinessException;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.dechet.status.Status;
import mg.ecomada.collect.dechet.status.StatusRepository;
import mg.ecomada.collect.dechet.typedechet.TypeDechetRepository;
import mg.ecomada.collect.geo.pointcollecte.PointCollecteRepository;
import mg.ecomada.collect.recycleur.RecycleurRepository;
import mg.ecomada.collect.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class DepotServiceImpl implements DepotService {
    private final DepotRepository repository;
    private final DepotMapper mapper;
    private final UserRepository userRepository;
    private final PointCollecteRepository pointCollecteRepository;
    private final TypeDechetRepository typeDechetRepository;
    private final StatusRepository statusRepository;
    private final CollecteurRepository collecteurRepository;
    private final RecycleurRepository recycleurRepository;

    @Override
    public Page<DepotDto> findAll(Long villeId, Long statusId, Long typeDechetId,
                                  LocalDateTime dateMin, LocalDateTime dateMax, Pageable pageable) {
        return repository.findWithFilters(villeId, statusId, typeDechetId, dateMin, dateMax, pageable)
                .map(mapper::toDto);
    }

    @Override
    public DepotDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Depot", id));
    }

    @Override
    public DepotDto create(DepotDto dto) {
        Depot depot = new Depot();
        depot.setPoidsKg(dto.getPoidsKg());
        depot.setDateDepot(LocalDateTime.now());
        depot.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId())));
        depot.setPointCollecte(pointCollecteRepository.findById(dto.getPointCollecteId())
                .orElseThrow(() -> new ResourceNotFoundException("PointCollecte", dto.getPointCollecteId())));
        depot.setTypeDechet(typeDechetRepository.findById(dto.getTypeDechetId())
                .orElseThrow(() -> new ResourceNotFoundException("TypeDechet", dto.getTypeDechetId())));
        Status enregistre = statusRepository.findByRang(1)
                .orElseThrow(() -> new BusinessException("Status ENREGISTRE introuvable"));
        depot.setStatus(enregistre);
        return mapper.toDto(repository.save(depot));
    }

    @Override
    public DepotDto updateStatus(Long id, Long statusId, Long collecteurId, Long recycleurId) {
        Depot depot = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Depot", id));
        Status newStatus = statusRepository.findById(statusId)
                .orElseThrow(() -> new ResourceNotFoundException("Status", statusId));

        if (newStatus.getRang() <= depot.getStatus().getRang()) {
            throw new BusinessException("Le statut ne peut passer qu'a un rang superieur. "
                    + "Rang actuel: " + depot.getStatus().getRang() + ", Rang demande: " + newStatus.getRang());
        }
        if (newStatus.getNom().equals("VALORISE") && recycleurId == null) {
            throw new BusinessException("Un recycleur doit etre specifie pour le statut VALORISE");
        }
        depot.setStatus(newStatus);
        if (collecteurId != null) {
            depot.setCollecteur(collecteurRepository.findById(collecteurId)
                    .orElseThrow(() -> new ResourceNotFoundException("Collecteur", collecteurId)));
        }
        if (recycleurId != null) {
            depot.setRecycleur(recycleurRepository.findById(recycleurId)
                    .orElseThrow(() -> new ResourceNotFoundException("Recycleur", recycleurId)));
        }
        return mapper.toDto(repository.save(depot));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Depot", id);
        repository.deleteById(id);
    }
}
