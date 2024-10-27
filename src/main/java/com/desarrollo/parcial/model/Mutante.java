package com.desarrollo.parcial.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import java.util.List;

@Entity
public class Mutante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> adn; // Cambiado a List<String>
    private boolean esMutante;

    public Mutante() {
    }

    public Mutante(String[] adn, boolean esMutante) {
        this.adn = List.of(adn); // Convertido a List<String>
        this.esMutante = esMutante;
    }

    public Long getId() {
        return id;
    }

    public List<String> getAdn() { // Cambiado a List<String>
        return adn;
    }

    public void setAdn(List<String> adn) { // Cambiado a List<String>
        this.adn = adn;
    }

    public boolean isEsMutante() {
        return esMutante;
    }

    public void setEsMutante(boolean esMutante) {
        this.esMutante = esMutante;
    }
}
