package mg.ecomada.collect.recompense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecompenseDto {
    private Long id;
    private String nom;
    private String description;
    private Integer pointsRequis;
}
