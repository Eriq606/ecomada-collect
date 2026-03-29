package mg.ecomada.collect.recycleur;

import mg.ecomada.collect.dechet.typedechet.TypeDechet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RecycleurMapper {

    @Mapping(target = "typeDechetNoms", expression = "java(mapTypes(entity.getTypeDechets()))")
    RecycleurDto toDto(Recycleur entity);

    @Mapping(target = "typeDechets", ignore = true)
    Recycleur toEntity(RecycleurDto dto);

    default Set<String> mapTypes(Set<TypeDechet> types) {
        if (types == null) return null;
        return types.stream().map(TypeDechet::getNom).collect(Collectors.toSet());
    }
}
