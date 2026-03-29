package mg.ecomada.collect.recompense;

import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.common.exception.ResourceNotFoundException;
import mg.ecomada.collect.user.User;
import mg.ecomada.collect.user.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecompenseServiceImpl implements RecompenseService {
    private final RecompenseRepository repository;
    private final RecompenseMapper mapper;
    private final UserRepository userRepository;

    public RecompenseServiceImpl(RecompenseRepository repository, @Qualifier("recompenseMapperImpl") RecompenseMapper mapper, UserRepository userRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<RecompenseDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public RecompenseDto findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Recompense", id));
    }

    @Override
    public RecompenseDto create(RecompenseDto dto) {
        Recompense entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public RecompenseDto update(Long id, RecompenseDto dto) {
        Recompense entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recompense", id));
        entity.setNom(dto.getNom());
        entity.setDescription(dto.getDescription());
        entity.setPointsRequis(dto.getPointsRequis());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Recompense", id);
        repository.deleteById(id);
    }

    @Override
    public void attribuer(Long userId, Long recompenseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        Recompense recompense = repository.findById(recompenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Recompense", recompenseId));
        recompense.getUsers().add(user);
        repository.save(recompense);
    }
}
