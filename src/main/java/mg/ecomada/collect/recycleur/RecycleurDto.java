package mg.ecomada.collect.recycleur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecycleurDto {
    private Long id;
    private String nom;
    private String adresse;
    private Boolean actif;
    private Set<String> typeDechetNoms;
}
