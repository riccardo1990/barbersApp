package com.barber.gun.repository;

import com.barber.gun.domain.Operatore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Operatore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperatoreRepository extends JpaRepository<Operatore, Long> {

    @Query(value = "select distinct operatore from Operatore operatore left join fetch operatore.negozios",
        countQuery = "select count(distinct operatore) from Operatore operatore")
    Page<Operatore> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct operatore from Operatore operatore left join fetch operatore.negozios")
    List<Operatore> findAllWithEagerRelationships();

    @Query("select operatore from Operatore operatore left join fetch operatore.negozios where operatore.id =:id")
    Optional<Operatore> findOneWithEagerRelationships(@Param("id") Long id);

}
