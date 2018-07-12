package com.barber.gun.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.barber.gun.domain.Operatore;
import com.barber.gun.repository.OperatoreRepository;
import com.barber.gun.web.rest.errors.BadRequestAlertException;
import com.barber.gun.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Operatore.
 */
@RestController
@RequestMapping("/api")
public class OperatoreResource {

    private final Logger log = LoggerFactory.getLogger(OperatoreResource.class);

    private static final String ENTITY_NAME = "operatore";

    private final OperatoreRepository operatoreRepository;

    public OperatoreResource(OperatoreRepository operatoreRepository) {
        this.operatoreRepository = operatoreRepository;
    }

    /**
     * POST  /operatores : Create a new operatore.
     *
     * @param operatore the operatore to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operatore, or with status 400 (Bad Request) if the operatore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operatores")
    @Timed
    public ResponseEntity<Operatore> createOperatore(@Valid @RequestBody Operatore operatore) throws URISyntaxException {
        log.debug("REST request to save Operatore : {}", operatore);
        if (operatore.getId() != null) {
            throw new BadRequestAlertException("A new operatore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Operatore result = operatoreRepository.save(operatore);
        return ResponseEntity.created(new URI("/api/operatores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operatores : Updates an existing operatore.
     *
     * @param operatore the operatore to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operatore,
     * or with status 400 (Bad Request) if the operatore is not valid,
     * or with status 500 (Internal Server Error) if the operatore couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operatores")
    @Timed
    public ResponseEntity<Operatore> updateOperatore(@Valid @RequestBody Operatore operatore) throws URISyntaxException {
        log.debug("REST request to update Operatore : {}", operatore);
        if (operatore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Operatore result = operatoreRepository.save(operatore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operatore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operatores : get all the operatores.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of operatores in body
     */
    @GetMapping("/operatores")
    @Timed
    public List<Operatore> getAllOperatores(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Operatores");
        return operatoreRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /operatores/:id : get the "id" operatore.
     *
     * @param id the id of the operatore to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operatore, or with status 404 (Not Found)
     */
    @GetMapping("/operatores/{id}")
    @Timed
    public ResponseEntity<Operatore> getOperatore(@PathVariable Long id) {
        log.debug("REST request to get Operatore : {}", id);
        Optional<Operatore> operatore = operatoreRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(operatore);
    }

    /**
     * DELETE  /operatores/:id : delete the "id" operatore.
     *
     * @param id the id of the operatore to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operatores/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperatore(@PathVariable Long id) {
        log.debug("REST request to delete Operatore : {}", id);

        operatoreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
