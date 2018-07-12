package com.barber.gun.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Prenotazione.
 */
@Entity
@Table(name = "prenotazione")
public class Prenotazione implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codice_prenotazione", nullable = false)
    private String codicePrenotazione;

    @NotNull
    @Column(name = "data", nullable = false)
    private Instant data;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Operatore operatore;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Servizio servizio;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Negozio negozio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public Prenotazione codicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
        return this;
    }

    public void setCodicePrenotazione(String codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    public Instant getData() {
        return data;
    }

    public Prenotazione data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public String getNote() {
        return note;
    }

    public Prenotazione note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Operatore getOperatore() {
        return operatore;
    }

    public Prenotazione operatore(Operatore operatore) {
        this.operatore = operatore;
        return this;
    }

    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    public Servizio getServizio() {
        return servizio;
    }

    public Prenotazione servizio(Servizio servizio) {
        this.servizio = servizio;
        return this;
    }

    public void setServizio(Servizio servizio) {
        this.servizio = servizio;
    }

    public Negozio getNegozio() {
        return negozio;
    }

    public Prenotazione negozio(Negozio negozio) {
        this.negozio = negozio;
        return this;
    }

    public void setNegozio(Negozio negozio) {
        this.negozio = negozio;
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
        Prenotazione prenotazione = (Prenotazione) o;
        if (prenotazione.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prenotazione.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
            "id=" + getId() +
            ", codicePrenotazione='" + getCodicePrenotazione() + "'" +
            ", data='" + getData() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
