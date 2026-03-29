package mg.ecomada.collect.recycleur;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecycleurRepository extends JpaRepository<Recycleur, Long> {
    java.util.List<Recycleur> findByActif(Boolean actif);
}
