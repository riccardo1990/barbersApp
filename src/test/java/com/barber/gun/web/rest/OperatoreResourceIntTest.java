package com.barber.gun.web.rest;

import com.barber.gun.BarbersApp;

import com.barber.gun.domain.Operatore;
import com.barber.gun.repository.OperatoreRepository;
import com.barber.gun.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.barber.gun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OperatoreResource REST controller.
 *
 * @see OperatoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BarbersApp.class)
public class OperatoreResourceIntTest {

    private static final String DEFAULT_CODICE_OPERATORE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE_OPERATORE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_FOTO_PROFILO = "AAAAAAAAAA";
    private static final String UPDATED_FOTO_PROFILO = "BBBBBBBBBB";

    @Autowired
    private OperatoreRepository operatoreRepository;
    @Mock
    private OperatoreRepository operatoreRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperatoreMockMvc;

    private Operatore operatore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperatoreResource operatoreResource = new OperatoreResource(operatoreRepository);
        this.restOperatoreMockMvc = MockMvcBuilders.standaloneSetup(operatoreResource)
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
    public static Operatore createEntity(EntityManager em) {
        Operatore operatore = new Operatore()
            .codiceOperatore(DEFAULT_CODICE_OPERATORE)
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME)
            .note(DEFAULT_NOTE)
            .fotoProfilo(DEFAULT_FOTO_PROFILO);
        return operatore;
    }

    @Before
    public void initTest() {
        operatore = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperatore() throws Exception {
        int databaseSizeBeforeCreate = operatoreRepository.findAll().size();

        // Create the Operatore
        restOperatoreMockMvc.perform(post("/api/operatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatore)))
            .andExpect(status().isCreated());

        // Validate the Operatore in the database
        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeCreate + 1);
        Operatore testOperatore = operatoreList.get(operatoreList.size() - 1);
        assertThat(testOperatore.getCodiceOperatore()).isEqualTo(DEFAULT_CODICE_OPERATORE);
        assertThat(testOperatore.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testOperatore.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testOperatore.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testOperatore.getFotoProfilo()).isEqualTo(DEFAULT_FOTO_PROFILO);
    }

    @Test
    @Transactional
    public void createOperatoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operatoreRepository.findAll().size();

        // Create the Operatore with an existing ID
        operatore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatoreMockMvc.perform(post("/api/operatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatore)))
            .andExpect(status().isBadRequest());

        // Validate the Operatore in the database
        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodiceOperatoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatoreRepository.findAll().size();
        // set the field null
        operatore.setCodiceOperatore(null);

        // Create the Operatore, which fails.

        restOperatoreMockMvc.perform(post("/api/operatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatore)))
            .andExpect(status().isBadRequest());

        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatoreRepository.findAll().size();
        // set the field null
        operatore.setNome(null);

        // Create the Operatore, which fails.

        restOperatoreMockMvc.perform(post("/api/operatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatore)))
            .andExpect(status().isBadRequest());

        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCognomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatoreRepository.findAll().size();
        // set the field null
        operatore.setCognome(null);

        // Create the Operatore, which fails.

        restOperatoreMockMvc.perform(post("/api/operatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatore)))
            .andExpect(status().isBadRequest());

        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatoreRepository.findAll().size();
        // set the field null
        operatore.setNote(null);

        // Create the Operatore, which fails.

        restOperatoreMockMvc.perform(post("/api/operatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatore)))
            .andExpect(status().isBadRequest());

        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperatores() throws Exception {
        // Initialize the database
        operatoreRepository.saveAndFlush(operatore);

        // Get all the operatoreList
        restOperatoreMockMvc.perform(get("/api/operatores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operatore.getId().intValue())))
            .andExpect(jsonPath("$.[*].codiceOperatore").value(hasItem(DEFAULT_CODICE_OPERATORE.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].fotoProfilo").value(hasItem(DEFAULT_FOTO_PROFILO.toString())));
    }
    
    public void getAllOperatoresWithEagerRelationshipsIsEnabled() throws Exception {
        OperatoreResource operatoreResource = new OperatoreResource(operatoreRepositoryMock);
        when(operatoreRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restOperatoreMockMvc = MockMvcBuilders.standaloneSetup(operatoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOperatoreMockMvc.perform(get("/api/operatores?eagerload=true"))
        .andExpect(status().isOk());

        verify(operatoreRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllOperatoresWithEagerRelationshipsIsNotEnabled() throws Exception {
        OperatoreResource operatoreResource = new OperatoreResource(operatoreRepositoryMock);
            when(operatoreRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restOperatoreMockMvc = MockMvcBuilders.standaloneSetup(operatoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOperatoreMockMvc.perform(get("/api/operatores?eagerload=true"))
        .andExpect(status().isOk());

            verify(operatoreRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOperatore() throws Exception {
        // Initialize the database
        operatoreRepository.saveAndFlush(operatore);

        // Get the operatore
        restOperatoreMockMvc.perform(get("/api/operatores/{id}", operatore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operatore.getId().intValue()))
            .andExpect(jsonPath("$.codiceOperatore").value(DEFAULT_CODICE_OPERATORE.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.fotoProfilo").value(DEFAULT_FOTO_PROFILO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOperatore() throws Exception {
        // Get the operatore
        restOperatoreMockMvc.perform(get("/api/operatores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperatore() throws Exception {
        // Initialize the database
        operatoreRepository.saveAndFlush(operatore);

        int databaseSizeBeforeUpdate = operatoreRepository.findAll().size();

        // Update the operatore
        Operatore updatedOperatore = operatoreRepository.findById(operatore.getId()).get();
        // Disconnect from session so that the updates on updatedOperatore are not directly saved in db
        em.detach(updatedOperatore);
        updatedOperatore
            .codiceOperatore(UPDATED_CODICE_OPERATORE)
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .note(UPDATED_NOTE)
            .fotoProfilo(UPDATED_FOTO_PROFILO);

        restOperatoreMockMvc.perform(put("/api/operatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperatore)))
            .andExpect(status().isOk());

        // Validate the Operatore in the database
        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeUpdate);
        Operatore testOperatore = operatoreList.get(operatoreList.size() - 1);
        assertThat(testOperatore.getCodiceOperatore()).isEqualTo(UPDATED_CODICE_OPERATORE);
        assertThat(testOperatore.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testOperatore.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testOperatore.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testOperatore.getFotoProfilo()).isEqualTo(UPDATED_FOTO_PROFILO);
    }

    @Test
    @Transactional
    public void updateNonExistingOperatore() throws Exception {
        int databaseSizeBeforeUpdate = operatoreRepository.findAll().size();

        // Create the Operatore

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperatoreMockMvc.perform(put("/api/operatores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operatore)))
            .andExpect(status().isBadRequest());

        // Validate the Operatore in the database
        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperatore() throws Exception {
        // Initialize the database
        operatoreRepository.saveAndFlush(operatore);

        int databaseSizeBeforeDelete = operatoreRepository.findAll().size();

        // Get the operatore
        restOperatoreMockMvc.perform(delete("/api/operatores/{id}", operatore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Operatore> operatoreList = operatoreRepository.findAll();
        assertThat(operatoreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operatore.class);
        Operatore operatore1 = new Operatore();
        operatore1.setId(1L);
        Operatore operatore2 = new Operatore();
        operatore2.setId(operatore1.getId());
        assertThat(operatore1).isEqualTo(operatore2);
        operatore2.setId(2L);
        assertThat(operatore1).isNotEqualTo(operatore2);
        operatore1.setId(null);
        assertThat(operatore1).isNotEqualTo(operatore2);
    }
}
