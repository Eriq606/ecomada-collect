package mg.ecomada.collect.collecteur;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollecteurMapper {
    CollecteurDto toDto(Collecteur entity);

    Collecteur toEntity(CollecteurDto dto);
}
