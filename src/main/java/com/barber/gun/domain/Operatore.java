package com.barber.gun.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Operatore.
 */
@Entity
@Table(name = "operatore")
public class Operatore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codice_operatore", nullable = false)
    private String codiceOperatore;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cognome", nullable = false)
    private String cognome;

    @NotNull
    @Column(name = "note", nullable = false)
    private String note;

    @Lob
    @Column(name = "foto_profilo")
    private String fotoProfilo;

    @ManyToMany
    @JoinTable(name = "operatore_negozio",
               joinColumns = @JoinColumn(name = "operatores_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "negozios_id", referencedColumnName = "id"))
    private Set<Negozio> negozios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodiceOperatore() {
        return codiceOperatore;
    }

    public Operatore codiceOperatore(String codiceOperatore) {
        this.codiceOperatore = codiceOperatore;
        return this;
    }

    public void setCodiceOperatore(String codiceOperatore) {
        this.codiceOperatore = codiceOperatore;
    }

    public String getNome() {
        return nome;
    }

    public Operatore nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Operatore cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNote() {
        return note;
    }

    public Operatore note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public Operatore fotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
        return this;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public Set<Negozio> getNegozios() {
        return negozios;
    }

    public Operatore negozios(Set<Negozio> negozios) {
        this.negozios = negozios;
        return this;
    }

    public Operatore addNegozio(Negozio negozio) {
        this.negozios.add(negozio);
        negozio.getOperatores().add(this);
        return this;
    }

    public Operatore removeNegozio(Negozio negozio) {
        this.negozios.remove(negozio);
        negozio.getOperatores().remove(this);
        return this;
    }

    public void setNegozios(Set<Negozio> negozios) {
        this.negozios = negozios;
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
        Operatore operatore = (Operatore) o;
        if (operatore.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operatore.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operatore{" +
            "id=" + getId() +
            ", codiceOperatore='" + getCodiceOperatore() + "'" +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", note='" + getNote() + "'" +
            ", fotoProfilo='" + getFotoProfilo() + "'" +
            "}";
    }
}
