package com.barber.gun.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.barber.gun.domain.Negozio;
import com.barber.gun.repository.NegozioRepository;
import com.barber.gun.web.rest.errors.BadRequestAlertException;
import com.barber.gun.web.rest.util.HeaderUtil;
import com.barber.gun.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Negozio.
 */
@RestController
@RequestMapping("/api")
public class NegozioResource {

    private final Logger log = LoggerFactory.getLogger(NegozioResource.class);

    private static final String ENTITY_NAME = "negozio";

    private final NegozioRepository negozioRepository;

    public NegozioResource(NegozioRepository negozioRepository) {
        this.negozioRepository = negozioRepository;
    }

    /**
     * POST  /negozios : Create a new negozio.
     *
     * @param negozio the negozio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new negozio, or with status 400 (Bad Request) if the negozio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/negozios")
    @Timed
    public ResponseEntity<Negozio> createNegozio(@Valid @RequestBody Negozio negozio) throws URISyntaxException {
        log.debug("REST request to save Negozio : {}", negozio);
        if (negozio.getId() != null) {
            throw new BadRequestAlertException("A new negozio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Negozio result = negozioRepository.save(negozio);
        return ResponseEntity.created(new URI("/api/negozios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /negozios : Updates an existing negozio.
     *
     * @param negozio the negozio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated negozio,
     * or with status 400 (Bad Request) if the negozio is not valid,
     * or with status 500 (Internal Server Error) if the negozio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/negozios")
    @Timed
    public ResponseEntity<Negozio> updateNegozio(@Valid @RequestBody Negozio negozio) throws URISyntaxException {
        log.debug("REST request to update Negozio : {}", negozio);
        if (negozio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Negozio result = negozioRepository.save(negozio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, negozio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /negozios : get all the negozios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of negozios in body
     */
    @GetMapping("/negozios")
    @Timed
    public ResponseEntity<List<Negozio>> getAllNegozios(Pageable pageable) {
        log.debug("REST request to get a page of Negozios");
        Page<Negozio> page = negozioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/negozios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /negozios/:id : get the "id" negozio.
     *
     * @param id the id of the negozio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the negozio, or with status 404 (Not Found)
     */
    @GetMapping("/negozios/{id}")
    @Timed
    public ResponseEntity<Negozio> getNegozio(@PathVariable Long id) {
        log.debug("REST request to get Negozio : {}", id);
        Optional<Negozio> negozio = negozioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(negozio);
    }

    /**
     * DELETE  /negozios/:id : delete the "id" negozio.
     *
     * @param id the id of the negozio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/negozios/{id}")
    @Timed
    public ResponseEntity<Void> deleteNegozio(@PathVariable Long id) {
        log.debug("REST request to delete Negozio : {}", id);

        negozioRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
