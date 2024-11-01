package com.desarrollo.parcial.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Mutante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Garantiza que el ADN sea único en la base de datos
    private String adn;

    private boolean esMutante;

    // Constructor vacío requerido por JPA
    public Mutante() {
    }

    // Constructor que acepta un String en lugar de un array
    public Mutante(String adn, boolean esMutante) {
        this.adn = adn;
        this.esMutante = esMutante;
    }

    public Long getId() {
        return id;
    }

    public String getAdn() {
        return adn;
    }

    public void setAdn(String adn) {
        this.adn = adn;
    }

    public boolean isEsMutante() {
        return esMutante;
    }

    public void setEsMutante(boolean esMutante) {
        this.esMutante = esMutante;
    }
}
