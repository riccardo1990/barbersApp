package com.barber.gun.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Negozio.
 */
@Entity
@Table(name = "negozio")
public class Negozio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codice_negozio", nullable = false)
    private String codiceNegozio;

    @NotNull
    @Column(name = "descrizione_negozio", nullable = false)
    private String descrizioneNegozio;

    @NotNull
    @Column(name = "indirizzo", nullable = false)
    private String indirizzo;

    @NotNull
    @Column(name = "cap", nullable = false)
    private Integer cap;

    @NotNull
    @Column(name = "citta", nullable = false)
    private String citta;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @ManyToMany(mappedBy = "negozios")
    @JsonIgnore
    private Set<Operatore> operatores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodiceNegozio() {
        return codiceNegozio;
    }

    public Negozio codiceNegozio(String codiceNegozio) {
        this.codiceNegozio = codiceNegozio;
        return this;
    }

    public void setCodiceNegozio(String codiceNegozio) {
        this.codiceNegozio = codiceNegozio;
    }

    public String getDescrizioneNegozio() {
        return descrizioneNegozio;
    }

    public Negozio descrizioneNegozio(String descrizioneNegozio) {
        this.descrizioneNegozio = descrizioneNegozio;
        return this;
    }

    public void setDescrizioneNegozio(String descrizioneNegozio) {
        this.descrizioneNegozio = descrizioneNegozio;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Negozio indirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
        return this;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Integer getCap() {
        return cap;
    }

    public Negozio cap(Integer cap) {
        this.cap = cap;
        return this;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public Negozio citta(String citta) {
        this.citta = citta;
        return this;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getTelefono() {
        return telefono;
    }

    public Negozio telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Operatore> getOperatores() {
        return operatores;
    }

    public Negozio operatores(Set<Operatore> operatores) {
        this.operatores = operatores;
        return this;
    }

    public Negozio addOperatore(Operatore operatore) {
        this.operatores.add(operatore);
        operatore.getNegozios().add(this);
        return this;
    }

    public Negozio removeOperatore(Operatore operatore) {
        this.operatores.remove(operatore);
        operatore.getNegozios().remove(this);
        return this;
    }

    public void setOperatores(Set<Operatore> operatores) {
        this.operatores = operatores;
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
        Negozio negozio = (Negozio) o;
        if (negozio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), negozio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Negozio{" +
            "id=" + getId() +
            ", codiceNegozio='" + getCodiceNegozio() + "'" +
            ", descrizioneNegozio='" + getDescrizioneNegozio() + "'" +
            ", indirizzo='" + getIndirizzo() + "'" +
            ", cap=" + getCap() +
            ", citta='" + getCitta() + "'" +
            ", telefono='" + getTelefono() + "'" +
            "}";
    }
}
