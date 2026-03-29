package mg.ecomada.collect.depot;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepotMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.nom", target = "userName")
    @Mapping(source = "pointCollecte.id", target = "pointCollecteId")
    @Mapping(source = "pointCollecte.nom", target = "pointCollecteNom")
    @Mapping(source = "typeDechet.id", target = "typeDechetId")
    @Mapping(source = "typeDechet.nom", target = "typeDechetNom")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.nom", target = "statusNom")
    @Mapping(source = "collecteur.id", target = "collecteurId")
    @Mapping(source = "collecteur.nom", target = "collecteurNom")
    @Mapping(source = "recycleur.id", target = "recycleurId")
    @Mapping(source = "recycleur.nom", target = "recycleurNom")
    DepotDto toDto(Depot entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "pointCollecte", ignore = true)
    @Mapping(target = "typeDechet", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "collecteur", ignore = true)
    @Mapping(target = "recycleur", ignore = true)
    Depot toEntity(DepotDto dto);
}
