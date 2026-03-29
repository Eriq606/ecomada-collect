package mg.ecomada.collect.collecteur;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollecteurRepository extends JpaRepository<Collecteur, Long> {
    java.util.List<Collecteur> findByActif(Boolean actif);
}
