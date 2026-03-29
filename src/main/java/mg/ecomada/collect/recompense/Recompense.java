package mg.ecomada.collect.recompense;

import jakarta.persistence.*;
import lombok.*;
import mg.ecomada.collect.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recompenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recompense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;
    private String description;
    @Column(nullable = false)
    private Integer pointsRequis;

    @ManyToMany
    @JoinTable(name = "user_recompenses",
            joinColumns = @JoinColumn(name = "recompense_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
