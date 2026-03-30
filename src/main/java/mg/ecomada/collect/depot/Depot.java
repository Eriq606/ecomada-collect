package mg.ecomada.collect.depot;

import jakarta.persistence.*;
import lombok.*;
import mg.ecomada.collect.collecteur.Collecteur;
import mg.ecomada.collect.dechet.status.Status;
import mg.ecomada.collect.dechet.typedechet.TypeDechet;
import mg.ecomada.collect.geo.pointcollecte.PointCollecte;
import mg.ecomada.collect.recycleur.Recycleur;
import mg.ecomada.collect.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "depots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Depot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double poidsKg;

    @Column(nullable = false)
    private LocalDateTime dateDepot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_collecte_id", nullable = false)
    private PointCollecte pointCollecte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_dechet_id", nullable = false)
    private TypeDechet typeDechet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collecteur_id")
    private Collecteur collecteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recycleur_id")
    private Recycleur recycleur;
}
