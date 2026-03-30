package mg.ecomada.collect.recycleur;

import jakarta.persistence.*;
import lombok.*;
import mg.ecomada.collect.dechet.typedechet.TypeDechet;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recycleurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recycleur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String adresse;

    @Builder.Default
    private Boolean actif = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "recycleur_type_dechet",
            joinColumns = @JoinColumn(name = "recycleur_id"),
            inverseJoinColumns = @JoinColumn(name = "type_dechet_id"))
    @Builder.Default
    private Set<TypeDechet> typeDechets = new HashSet<>();
}
