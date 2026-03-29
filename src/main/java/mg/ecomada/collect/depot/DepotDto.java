package mg.ecomada.collect.depot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepotDto {
    private Long id;
    private Double poidsKg;
    private LocalDateTime dateDepot;
    private Long userId;
    private String userName;
    private Long pointCollecteId;
    private String pointCollecteNom;
    private Long typeDechetId;
    private String typeDechetNom;
    private Long statusId;
    private String statusNom;
    private Long collecteurId;
    private String collecteurNom;
    private Long recycleurId;
    private String recycleurNom;
}
