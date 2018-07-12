package com.barber.gun.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Servizio.
 */
@Entity
@Table(name = "servizio")
public class Servizio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codice_servizio", nullable = false)
    private String codiceServizio;

    @NotNull
    @Column(name = "descrizione_servizio", nullable = false)
    private String descrizioneServizio;

    @NotNull
    @Column(name = "durata", nullable = false)
    private Integer durata;

    @NotNull
    @Column(name = "costo", nullable = false)
    private Double costo;

    @Lob
    @Column(name = "foto_servizio")
    private String fotoServizio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodiceServizio() {
        return codiceServizio;
    }

    public Servizio codiceServizio(String codiceServizio) {
        this.codiceServizio = codiceServizio;
        return this;
    }

    public void setCodiceServizio(String codiceServizio) {
        this.codiceServizio = codiceServizio;
    }

    public String getDescrizioneServizio() {
        return descrizioneServizio;
    }

    public Servizio descrizioneServizio(String descrizioneServizio) {
        this.descrizioneServizio = descrizioneServizio;
        return this;
    }

    public void setDescrizioneServizio(String descrizioneServizio) {
        this.descrizioneServizio = descrizioneServizio;
    }

    public Integer getDurata() {
        return durata;
    }

    public Servizio durata(Integer durata) {
        this.durata = durata;
        return this;
    }

    public void setDurata(Integer durata) {
        this.durata = durata;
    }

    public Double getCosto() {
        return costo;
    }

    public Servizio costo(Double costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getFotoServizio() {
        return fotoServizio;
    }

    public Servizio fotoServizio(String fotoServizio) {
        this.fotoServizio = fotoServizio;
        return this;
    }

    public void setFotoServizio(String fotoServizio) {
        this.fotoServizio = fotoServizio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Servizio servizio = (Servizio) o;
        if (servizio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), servizio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Servizio{" +
            "id=" + getId() +
            ", codiceServizio='" + getCodiceServizio() + "'" +
            ", descrizioneServizio='" + getDescrizioneServizio() + "'" +
            ", durata=" + getDurata() +
            ", costo=" + getCosto() +
            ", fotoServizio='" + getFotoServizio() + "'" +
            "}";
    }
}
