package mg.ecomada.collect.collecteur;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "collecteurs")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collecteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String telephone;

    @Builder.Default
    private Boolean actif = true;
}
