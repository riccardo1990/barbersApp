package com.barber.gun.web.rest;

import com.barber.gun.BarbersApp;

import com.barber.gun.domain.Negozio;
import com.barber.gun.repository.NegozioRepository;
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
import java.util.List;


import static com.barber.gun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NegozioResource REST controller.
 *
 * @see NegozioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BarbersApp.class)
public class NegozioResourceIntTest {

    private static final String DEFAULT_CODICE_NEGOZIO = "AAAAAAAAAA";
    private static final String UPDATED_CODICE_NEGOZIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE_NEGOZIO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE_NEGOZIO = "BBBBBBBBBB";

    private static final String DEFAULT_INDIRIZZO = "AAAAAAAAAA";
    private static final String UPDATED_INDIRIZZO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAP = 1;
    private static final Integer UPDATED_CAP = 2;

    private static final String DEFAULT_CITTA = "AAAAAAAAAA";
    private static final String UPDATED_CITTA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    @Autowired
    private NegozioRepository negozioRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNegozioMockMvc;

    private Negozio negozio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NegozioResource negozioResource = new NegozioResource(negozioRepository);
        this.restNegozioMockMvc = MockMvcBuilders.standaloneSetup(negozioResource)
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
    public static Negozio createEntity(EntityManager em) {
        Negozio negozio = new Negozio()
            .codiceNegozio(DEFAULT_CODICE_NEGOZIO)
            .descrizioneNegozio(DEFAULT_DESCRIZIONE_NEGOZIO)
            .indirizzo(DEFAULT_INDIRIZZO)
            .cap(DEFAULT_CAP)
            .citta(DEFAULT_CITTA)
            .telefono(DEFAULT_TELEFONO);
        return negozio;
    }

    @Before
    public void initTest() {
        negozio = createEntity(em);
    }

    @Test
    @Transactional
    public void createNegozio() throws Exception {
        int databaseSizeBeforeCreate = negozioRepository.findAll().size();

        // Create the Negozio
        restNegozioMockMvc.perform(post("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isCreated());

        // Validate the Negozio in the database
        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeCreate + 1);
        Negozio testNegozio = negozioList.get(negozioList.size() - 1);
        assertThat(testNegozio.getCodiceNegozio()).isEqualTo(DEFAULT_CODICE_NEGOZIO);
        assertThat(testNegozio.getDescrizioneNegozio()).isEqualTo(DEFAULT_DESCRIZIONE_NEGOZIO);
        assertThat(testNegozio.getIndirizzo()).isEqualTo(DEFAULT_INDIRIZZO);
        assertThat(testNegozio.getCap()).isEqualTo(DEFAULT_CAP);
        assertThat(testNegozio.getCitta()).isEqualTo(DEFAULT_CITTA);
        assertThat(testNegozio.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void createNegozioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = negozioRepository.findAll().size();

        // Create the Negozio with an existing ID
        negozio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNegozioMockMvc.perform(post("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isBadRequest());

        // Validate the Negozio in the database
        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodiceNegozioIsRequired() throws Exception {
        int databaseSizeBeforeTest = negozioRepository.findAll().size();
        // set the field null
        negozio.setCodiceNegozio(null);

        // Create the Negozio, which fails.

        restNegozioMockMvc.perform(post("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isBadRequest());

        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescrizioneNegozioIsRequired() throws Exception {
        int databaseSizeBeforeTest = negozioRepository.findAll().size();
        // set the field null
        negozio.setDescrizioneNegozio(null);

        // Create the Negozio, which fails.

        restNegozioMockMvc.perform(post("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isBadRequest());

        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndirizzoIsRequired() throws Exception {
        int databaseSizeBeforeTest = negozioRepository.findAll().size();
        // set the field null
        negozio.setIndirizzo(null);

        // Create the Negozio, which fails.

        restNegozioMockMvc.perform(post("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isBadRequest());

        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapIsRequired() throws Exception {
        int databaseSizeBeforeTest = negozioRepository.findAll().size();
        // set the field null
        negozio.setCap(null);

        // Create the Negozio, which fails.

        restNegozioMockMvc.perform(post("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isBadRequest());

        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCittaIsRequired() throws Exception {
        int databaseSizeBeforeTest = negozioRepository.findAll().size();
        // set the field null
        negozio.setCitta(null);

        // Create the Negozio, which fails.

        restNegozioMockMvc.perform(post("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isBadRequest());

        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = negozioRepository.findAll().size();
        // set the field null
        negozio.setTelefono(null);

        // Create the Negozio, which fails.

        restNegozioMockMvc.perform(post("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isBadRequest());

        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNegozios() throws Exception {
        // Initialize the database
        negozioRepository.saveAndFlush(negozio);

        // Get all the negozioList
        restNegozioMockMvc.perform(get("/api/negozios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(negozio.getId().intValue())))
            .andExpect(jsonPath("$.[*].codiceNegozio").value(hasItem(DEFAULT_CODICE_NEGOZIO.toString())))
            .andExpect(jsonPath("$.[*].descrizioneNegozio").value(hasItem(DEFAULT_DESCRIZIONE_NEGOZIO.toString())))
            .andExpect(jsonPath("$.[*].indirizzo").value(hasItem(DEFAULT_INDIRIZZO.toString())))
            .andExpect(jsonPath("$.[*].cap").value(hasItem(DEFAULT_CAP)))
            .andExpect(jsonPath("$.[*].citta").value(hasItem(DEFAULT_CITTA.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())));
    }
    

    @Test
    @Transactional
    public void getNegozio() throws Exception {
        // Initialize the database
        negozioRepository.saveAndFlush(negozio);

        // Get the negozio
        restNegozioMockMvc.perform(get("/api/negozios/{id}", negozio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(negozio.getId().intValue()))
            .andExpect(jsonPath("$.codiceNegozio").value(DEFAULT_CODICE_NEGOZIO.toString()))
            .andExpect(jsonPath("$.descrizioneNegozio").value(DEFAULT_DESCRIZIONE_NEGOZIO.toString()))
            .andExpect(jsonPath("$.indirizzo").value(DEFAULT_INDIRIZZO.toString()))
            .andExpect(jsonPath("$.cap").value(DEFAULT_CAP))
            .andExpect(jsonPath("$.citta").value(DEFAULT_CITTA.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingNegozio() throws Exception {
        // Get the negozio
        restNegozioMockMvc.perform(get("/api/negozios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNegozio() throws Exception {
        // Initialize the database
        negozioRepository.saveAndFlush(negozio);

        int databaseSizeBeforeUpdate = negozioRepository.findAll().size();

        // Update the negozio
        Negozio updatedNegozio = negozioRepository.findById(negozio.getId()).get();
        // Disconnect from session so that the updates on updatedNegozio are not directly saved in db
        em.detach(updatedNegozio);
        updatedNegozio
            .codiceNegozio(UPDATED_CODICE_NEGOZIO)
            .descrizioneNegozio(UPDATED_DESCRIZIONE_NEGOZIO)
            .indirizzo(UPDATED_INDIRIZZO)
            .cap(UPDATED_CAP)
            .citta(UPDATED_CITTA)
            .telefono(UPDATED_TELEFONO);

        restNegozioMockMvc.perform(put("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNegozio)))
            .andExpect(status().isOk());

        // Validate the Negozio in the database
        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeUpdate);
        Negozio testNegozio = negozioList.get(negozioList.size() - 1);
        assertThat(testNegozio.getCodiceNegozio()).isEqualTo(UPDATED_CODICE_NEGOZIO);
        assertThat(testNegozio.getDescrizioneNegozio()).isEqualTo(UPDATED_DESCRIZIONE_NEGOZIO);
        assertThat(testNegozio.getIndirizzo()).isEqualTo(UPDATED_INDIRIZZO);
        assertThat(testNegozio.getCap()).isEqualTo(UPDATED_CAP);
        assertThat(testNegozio.getCitta()).isEqualTo(UPDATED_CITTA);
        assertThat(testNegozio.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void updateNonExistingNegozio() throws Exception {
        int databaseSizeBeforeUpdate = negozioRepository.findAll().size();

        // Create the Negozio

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNegozioMockMvc.perform(put("/api/negozios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(negozio)))
            .andExpect(status().isBadRequest());

        // Validate the Negozio in the database
        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNegozio() throws Exception {
        // Initialize the database
        negozioRepository.saveAndFlush(negozio);

        int databaseSizeBeforeDelete = negozioRepository.findAll().size();

        // Get the negozio
        restNegozioMockMvc.perform(delete("/api/negozios/{id}", negozio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Negozio> negozioList = negozioRepository.findAll();
        assertThat(negozioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Negozio.class);
        Negozio negozio1 = new Negozio();
        negozio1.setId(1L);
        Negozio negozio2 = new Negozio();
        negozio2.setId(negozio1.getId());
        assertThat(negozio1).isEqualTo(negozio2);
        negozio2.setId(2L);
        assertThat(negozio1).isNotEqualTo(negozio2);
        negozio1.setId(null);
        assertThat(negozio1).isNotEqualTo(negozio2);
    }
}
