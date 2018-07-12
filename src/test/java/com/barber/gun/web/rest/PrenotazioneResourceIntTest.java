package com.barber.gun.web.rest;

import com.barber.gun.BarbersApp;

import com.barber.gun.domain.Prenotazione;
import com.barber.gun.repository.PrenotazioneRepository;
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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.barber.gun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrenotazioneResource REST controller.
 *
 * @see PrenotazioneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BarbersApp.class)
public class PrenotazioneResourceIntTest {

    private static final String DEFAULT_CODICE_PRENOTAZIONE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE_PRENOTAZIONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrenotazioneMockMvc;

    private Prenotazione prenotazione;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrenotazioneResource prenotazioneResource = new PrenotazioneResource(prenotazioneRepository);
        this.restPrenotazioneMockMvc = MockMvcBuilders.standaloneSetup(prenotazioneResource)
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
    public static Prenotazione createEntity(EntityManager em) {
        Prenotazione prenotazione = new Prenotazione()
            .codicePrenotazione(DEFAULT_CODICE_PRENOTAZIONE)
            .data(DEFAULT_DATA)
            .note(DEFAULT_NOTE);
        return prenotazione;
    }

    @Before
    public void initTest() {
        prenotazione = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrenotazione() throws Exception {
        int databaseSizeBeforeCreate = prenotazioneRepository.findAll().size();

        // Create the Prenotazione
        restPrenotazioneMockMvc.perform(post("/api/prenotaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prenotazione)))
            .andExpect(status().isCreated());

        // Validate the Prenotazione in the database
        List<Prenotazione> prenotazioneList = prenotazioneRepository.findAll();
        assertThat(prenotazioneList).hasSize(databaseSizeBeforeCreate + 1);
        Prenotazione testPrenotazione = prenotazioneList.get(prenotazioneList.size() - 1);
        assertThat(testPrenotazione.getCodicePrenotazione()).isEqualTo(DEFAULT_CODICE_PRENOTAZIONE);
        assertThat(testPrenotazione.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPrenotazione.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createPrenotazioneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prenotazioneRepository.findAll().size();

        // Create the Prenotazione with an existing ID
        prenotazione.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrenotazioneMockMvc.perform(post("/api/prenotaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prenotazione)))
            .andExpect(status().isBadRequest());

        // Validate the Prenotazione in the database
        List<Prenotazione> prenotazioneList = prenotazioneRepository.findAll();
        assertThat(prenotazioneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodicePrenotazioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = prenotazioneRepository.findAll().size();
        // set the field null
        prenotazione.setCodicePrenotazione(null);

        // Create the Prenotazione, which fails.

        restPrenotazioneMockMvc.perform(post("/api/prenotaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prenotazione)))
            .andExpect(status().isBadRequest());

        List<Prenotazione> prenotazioneList = prenotazioneRepository.findAll();
        assertThat(prenotazioneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = prenotazioneRepository.findAll().size();
        // set the field null
        prenotazione.setData(null);

        // Create the Prenotazione, which fails.

        restPrenotazioneMockMvc.perform(post("/api/prenotaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prenotazione)))
            .andExpect(status().isBadRequest());

        List<Prenotazione> prenotazioneList = prenotazioneRepository.findAll();
        assertThat(prenotazioneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrenotaziones() throws Exception {
        // Initialize the database
        prenotazioneRepository.saveAndFlush(prenotazione);

        // Get all the prenotazioneList
        restPrenotazioneMockMvc.perform(get("/api/prenotaziones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prenotazione.getId().intValue())))
            .andExpect(jsonPath("$.[*].codicePrenotazione").value(hasItem(DEFAULT_CODICE_PRENOTAZIONE.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    

    @Test
    @Transactional
    public void getPrenotazione() throws Exception {
        // Initialize the database
        prenotazioneRepository.saveAndFlush(prenotazione);

        // Get the prenotazione
        restPrenotazioneMockMvc.perform(get("/api/prenotaziones/{id}", prenotazione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prenotazione.getId().intValue()))
            .andExpect(jsonPath("$.codicePrenotazione").value(DEFAULT_CODICE_PRENOTAZIONE.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPrenotazione() throws Exception {
        // Get the prenotazione
        restPrenotazioneMockMvc.perform(get("/api/prenotaziones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrenotazione() throws Exception {
        // Initialize the database
        prenotazioneRepository.saveAndFlush(prenotazione);

        int databaseSizeBeforeUpdate = prenotazioneRepository.findAll().size();

        // Update the prenotazione
        Prenotazione updatedPrenotazione = prenotazioneRepository.findById(prenotazione.getId()).get();
        // Disconnect from session so that the updates on updatedPrenotazione are not directly saved in db
        em.detach(updatedPrenotazione);
        updatedPrenotazione
            .codicePrenotazione(UPDATED_CODICE_PRENOTAZIONE)
            .data(UPDATED_DATA)
            .note(UPDATED_NOTE);

        restPrenotazioneMockMvc.perform(put("/api/prenotaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrenotazione)))
            .andExpect(status().isOk());

        // Validate the Prenotazione in the database
        List<Prenotazione> prenotazioneList = prenotazioneRepository.findAll();
        assertThat(prenotazioneList).hasSize(databaseSizeBeforeUpdate);
        Prenotazione testPrenotazione = prenotazioneList.get(prenotazioneList.size() - 1);
        assertThat(testPrenotazione.getCodicePrenotazione()).isEqualTo(UPDATED_CODICE_PRENOTAZIONE);
        assertThat(testPrenotazione.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPrenotazione.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrenotazione() throws Exception {
        int databaseSizeBeforeUpdate = prenotazioneRepository.findAll().size();

        // Create the Prenotazione

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrenotazioneMockMvc.perform(put("/api/prenotaziones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prenotazione)))
            .andExpect(status().isBadRequest());

        // Validate the Prenotazione in the database
        List<Prenotazione> prenotazioneList = prenotazioneRepository.findAll();
        assertThat(prenotazioneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrenotazione() throws Exception {
        // Initialize the database
        prenotazioneRepository.saveAndFlush(prenotazione);

        int databaseSizeBeforeDelete = prenotazioneRepository.findAll().size();

        // Get the prenotazione
        restPrenotazioneMockMvc.perform(delete("/api/prenotaziones/{id}", prenotazione.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prenotazione> prenotazioneList = prenotazioneRepository.findAll();
        assertThat(prenotazioneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prenotazione.class);
        Prenotazione prenotazione1 = new Prenotazione();
        prenotazione1.setId(1L);
        Prenotazione prenotazione2 = new Prenotazione();
        prenotazione2.setId(prenotazione1.getId());
        assertThat(prenotazione1).isEqualTo(prenotazione2);
        prenotazione2.setId(2L);
        assertThat(prenotazione1).isNotEqualTo(prenotazione2);
        prenotazione1.setId(null);
        assertThat(prenotazione1).isNotEqualTo(prenotazione2);
    }
}
