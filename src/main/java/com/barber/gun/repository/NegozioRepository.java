package com.barber.gun.repository;

import com.barber.gun.domain.Negozio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Negozio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NegozioRepository extends JpaRepository<Negozio, Long> {

}
