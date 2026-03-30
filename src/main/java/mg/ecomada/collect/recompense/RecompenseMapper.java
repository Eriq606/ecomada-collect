package mg.ecomada.collect.recompense;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RecompenseMapper {
    RecompenseDto toDto(Recompense entity);

    @Mapping(target = "users", ignore = true)
    Recompense toEntity(RecompenseDto dto);
}
