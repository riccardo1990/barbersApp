package com.barber.gun.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.barber.gun.domain.Servizio;
import com.barber.gun.repository.ServizioRepository;
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
 * REST controller for managing Servizio.
 */
@RestController
@RequestMapping("/api")
public class ServizioResource {

    private final Logger log = LoggerFactory.getLogger(ServizioResource.class);

    private static final String ENTITY_NAME = "servizio";

    private final ServizioRepository servizioRepository;

    public ServizioResource(ServizioRepository servizioRepository) {
        this.servizioRepository = servizioRepository;
    }

    /**
     * POST  /servizios : Create a new servizio.
     *
     * @param servizio the servizio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new servizio, or with status 400 (Bad Request) if the servizio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servizios")
    @Timed
    public ResponseEntity<Servizio> createServizio(@Valid @RequestBody Servizio servizio) throws URISyntaxException {
        log.debug("REST request to save Servizio : {}", servizio);
        if (servizio.getId() != null) {
            throw new BadRequestAlertException("A new servizio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Servizio result = servizioRepository.save(servizio);
        return ResponseEntity.created(new URI("/api/servizios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servizios : Updates an existing servizio.
     *
     * @param servizio the servizio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated servizio,
     * or with status 400 (Bad Request) if the servizio is not valid,
     * or with status 500 (Internal Server Error) if the servizio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servizios")
    @Timed
    public ResponseEntity<Servizio> updateServizio(@Valid @RequestBody Servizio servizio) throws URISyntaxException {
        log.debug("REST request to update Servizio : {}", servizio);
        if (servizio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Servizio result = servizioRepository.save(servizio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servizio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servizios : get all the servizios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of servizios in body
     */
    @GetMapping("/servizios")
    @Timed
    public List<Servizio> getAllServizios() {
        log.debug("REST request to get all Servizios");
        return servizioRepository.findAll();
    }

    /**
     * GET  /servizios/:id : get the "id" servizio.
     *
     * @param id the id of the servizio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the servizio, or with status 404 (Not Found)
     */
    @GetMapping("/servizios/{id}")
    @Timed
    public ResponseEntity<Servizio> getServizio(@PathVariable Long id) {
        log.debug("REST request to get Servizio : {}", id);
        Optional<Servizio> servizio = servizioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(servizio);
    }

    /**
     * DELETE  /servizios/:id : delete the "id" servizio.
     *
     * @param id the id of the servizio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servizios/{id}")
    @Timed
    public ResponseEntity<Void> deleteServizio(@PathVariable Long id) {
        log.debug("REST request to delete Servizio : {}", id);

        servizioRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
