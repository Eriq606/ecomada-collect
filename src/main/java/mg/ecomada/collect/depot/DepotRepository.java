package mg.ecomada.collect.depot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DepotRepository extends JpaRepository<Depot, Long> {

    @Query("SELECT d FROM Depot d WHERE "
            + "(:villeId IS NULL OR d.pointCollecte.adresse.quartier.commune.ville.id = :villeId) AND "
            + "(:statusId IS NULL OR d.status.id = :statusId) AND "
            + "(:typeDechetId IS NULL OR d.typeDechet.id = :typeDechetId) AND "
            + "(:dateMin IS NULL OR d.dateDepot >= :dateMin) AND "
            + "(:dateMax IS NULL OR d.dateDepot <= :dateMax)")
    Page<Depot> findWithFilters(@Param("villeId") Long villeId,
                                @Param("statusId") Long statusId,
                                @Param("typeDechetId") Long typeDechetId,
                                @Param("dateMin") LocalDateTime dateMin,
                                @Param("dateMax") LocalDateTime dateMax,
                                Pageable pageable);

    List<Depot> findByUserId(Long userId);

    List<Depot> findByPointCollecteId(Long pointCollecteId);

    List<Depot> findByRecycleurId(Long recycleurId);

    List<Depot> findByCollecteurId(Long collecteurId);
}
