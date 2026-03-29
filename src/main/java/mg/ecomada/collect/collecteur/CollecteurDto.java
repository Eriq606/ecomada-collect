package mg.ecomada.collect.collecteur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollecteurDto {
    private Long id;
    private String nom;
    private String telephone;
    private Boolean actif;
}
