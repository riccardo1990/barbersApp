package com.barber.gun.repository;

import com.barber.gun.domain.Servizio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Servizio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServizioRepository extends JpaRepository<Servizio, Long> {

}
