package com.barber.gun.web.rest;

import com.barber.gun.BarbersApp;

import com.barber.gun.domain.Servizio;
import com.barber.gun.repository.ServizioRepository;
import com.barber.gun.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.barber.gun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ServizioResource REST controller.
 *
 * @see ServizioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BarbersApp.class)
public class ServizioResourceIntTest {

    private static final String DEFAULT_CODICE_SERVIZIO = "AAAAAAAAAA";
    private static final String UPDATED_CODICE_SERVIZIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE_SERVIZIO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE_SERVIZIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATA = 1;
    private static final Integer UPDATED_DURATA = 2;

    private static final Double DEFAULT_COSTO = 1D;
    private static final Double UPDATED_COSTO = 2D;

    private static final String DEFAULT_FOTO_SERVIZIO = "AAAAAAAAAA";
    private static final String UPDATED_FOTO_SERVIZIO = "BBBBBBBBBB";

    @Autowired
    private ServizioRepository servizioRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServizioMockMvc;

    private Servizio servizio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServizioResource servizioResource = new ServizioResource(servizioRepository);
        this.restServizioMockMvc = MockMvcBuilders.standaloneSetup(servizioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servizio createEntity(EntityManager em) {
        Servizio servizio = new Servizio()
            .codiceServizio(DEFAULT_CODICE_SERVIZIO)
            .descrizioneServizio(DEFAULT_DESCRIZIONE_SERVIZIO)
            .durata(DEFAULT_DURATA)
            .costo(DEFAULT_COSTO)
            .fotoServizio(DEFAULT_FOTO_SERVIZIO);
        return servizio;
    }

    @Before
    public void initTest() {
        servizio = createEntity(em);
    }

    @Test
    @Transactional
    public void createServizio() throws Exception {
        int databaseSizeBeforeCreate = servizioRepository.findAll().size();

        // Create the Servizio
        restServizioMockMvc.perform(post("/api/servizios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servizio)))
            .andExpect(status().isCreated());

        // Validate the Servizio in the database
        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeCreate + 1);
        Servizio testServizio = servizioList.get(servizioList.size() - 1);
        assertThat(testServizio.getCodiceServizio()).isEqualTo(DEFAULT_CODICE_SERVIZIO);
        assertThat(testServizio.getDescrizioneServizio()).isEqualTo(DEFAULT_DESCRIZIONE_SERVIZIO);
        assertThat(testServizio.getDurata()).isEqualTo(DEFAULT_DURATA);
        assertThat(testServizio.getCosto()).isEqualTo(DEFAULT_COSTO);
        assertThat(testServizio.getFotoServizio()).isEqualTo(DEFAULT_FOTO_SERVIZIO);
    }

    @Test
    @Transactional
    public void createServizioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servizioRepository.findAll().size();

        // Create the Servizio with an existing ID
        servizio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServizioMockMvc.perform(post("/api/servizios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servizio)))
            .andExpect(status().isBadRequest());

        // Validate the Servizio in the database
        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodiceServizioIsRequired() throws Exception {
        int databaseSizeBeforeTest = servizioRepository.findAll().size();
        // set the field null
        servizio.setCodiceServizio(null);

        // Create the Servizio, which fails.

        restServizioMockMvc.perform(post("/api/servizios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servizio)))
            .andExpect(status().isBadRequest());

        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescrizioneServizioIsRequired() throws Exception {
        int databaseSizeBeforeTest = servizioRepository.findAll().size();
        // set the field null
        servizio.setDescrizioneServizio(null);

        // Create the Servizio, which fails.

        restServizioMockMvc.perform(post("/api/servizios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servizio)))
            .andExpect(status().isBadRequest());

        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurataIsRequired() throws Exception {
        int databaseSizeBeforeTest = servizioRepository.findAll().size();
        // set the field null
        servizio.setDurata(null);

        // Create the Servizio, which fails.

        restServizioMockMvc.perform(post("/api/servizios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servizio)))
            .andExpect(status().isBadRequest());

        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servizioRepository.findAll().size();
        // set the field null
        servizio.setCosto(null);

        // Create the Servizio, which fails.

        restServizioMockMvc.perform(post("/api/servizios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servizio)))
            .andExpect(status().isBadRequest());

        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServizios() throws Exception {
        // Initialize the database
        servizioRepository.saveAndFlush(servizio);

        // Get all the servizioList
        restServizioMockMvc.perform(get("/api/servizios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servizio.getId().intValue())))
            .andExpect(jsonPath("$.[*].codiceServizio").value(hasItem(DEFAULT_CODICE_SERVIZIO.toString())))
            .andExpect(jsonPath("$.[*].descrizioneServizio").value(hasItem(DEFAULT_DESCRIZIONE_SERVIZIO.toString())))
            .andExpect(jsonPath("$.[*].durata").value(hasItem(DEFAULT_DURATA)))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].fotoServizio").value(hasItem(DEFAULT_FOTO_SERVIZIO.toString())));
    }
    

    @Test
    @Transactional
    public void getServizio() throws Exception {
        // Initialize the database
        servizioRepository.saveAndFlush(servizio);

        // Get the servizio
        restServizioMockMvc.perform(get("/api/servizios/{id}", servizio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servizio.getId().intValue()))
            .andExpect(jsonPath("$.codiceServizio").value(DEFAULT_CODICE_SERVIZIO.toString()))
            .andExpect(jsonPath("$.descrizioneServizio").value(DEFAULT_DESCRIZIONE_SERVIZIO.toString()))
            .andExpect(jsonPath("$.durata").value(DEFAULT_DURATA))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.doubleValue()))
            .andExpect(jsonPath("$.fotoServizio").value(DEFAULT_FOTO_SERVIZIO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingServizio() throws Exception {
        // Get the servizio
        restServizioMockMvc.perform(get("/api/servizios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServizio() throws Exception {
        // Initialize the database
        servizioRepository.saveAndFlush(servizio);

        int databaseSizeBeforeUpdate = servizioRepository.findAll().size();

        // Update the servizio
        Servizio updatedServizio = servizioRepository.findById(servizio.getId()).get();
        // Disconnect from session so that the updates on updatedServizio are not directly saved in db
        em.detach(updatedServizio);
        updatedServizio
            .codiceServizio(UPDATED_CODICE_SERVIZIO)
            .descrizioneServizio(UPDATED_DESCRIZIONE_SERVIZIO)
            .durata(UPDATED_DURATA)
            .costo(UPDATED_COSTO)
            .fotoServizio(UPDATED_FOTO_SERVIZIO);

        restServizioMockMvc.perform(put("/api/servizios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServizio)))
            .andExpect(status().isOk());

        // Validate the Servizio in the database
        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeUpdate);
        Servizio testServizio = servizioList.get(servizioList.size() - 1);
        assertThat(testServizio.getCodiceServizio()).isEqualTo(UPDATED_CODICE_SERVIZIO);
        assertThat(testServizio.getDescrizioneServizio()).isEqualTo(UPDATED_DESCRIZIONE_SERVIZIO);
        assertThat(testServizio.getDurata()).isEqualTo(UPDATED_DURATA);
        assertThat(testServizio.getCosto()).isEqualTo(UPDATED_COSTO);
        assertThat(testServizio.getFotoServizio()).isEqualTo(UPDATED_FOTO_SERVIZIO);
    }

    @Test
    @Transactional
    public void updateNonExistingServizio() throws Exception {
        int databaseSizeBeforeUpdate = servizioRepository.findAll().size();

        // Create the Servizio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServizioMockMvc.perform(put("/api/servizios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servizio)))
            .andExpect(status().isBadRequest());

        // Validate the Servizio in the database
        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServizio() throws Exception {
        // Initialize the database
        servizioRepository.saveAndFlush(servizio);

        int databaseSizeBeforeDelete = servizioRepository.findAll().size();

        // Get the servizio
        restServizioMockMvc.perform(delete("/api/servizios/{id}", servizio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Servizio> servizioList = servizioRepository.findAll();
        assertThat(servizioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servizio.class);
        Servizio servizio1 = new Servizio();
        servizio1.setId(1L);
        Servizio servizio2 = new Servizio();
        servizio2.setId(servizio1.getId());
        assertThat(servizio1).isEqualTo(servizio2);
        servizio2.setId(2L);
        assertThat(servizio1).isNotEqualTo(servizio2);
        servizio1.setId(null);
        assertThat(servizio1).isNotEqualTo(servizio2);
    }
}
