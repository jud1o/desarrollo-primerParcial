package com.desarrollo.parcial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ADNRequestTest {

    @Test
    public void testConstructorSinParametros() {
        // Crear una instancia de ADNRequest sin parámetros
        ADNRequest adnRequest = new ADNRequest();

        // Verificar que el ADN inicial es null
        assertNull(adnRequest.getAdn());
    }

    @Test
    public void testConstructorConParametros() {
        // Definir un arreglo de ADN
        String[] adn = {"AATAG", "AATCG", "ACGCG"};

        // Crear una instancia de ADNRequest con el arreglo
        ADNRequest adnRequest = new ADNRequest(adn);

        // Verificar que el ADN se establece correctamente
        assertArrayEquals(adn, adnRequest.getAdn());
    }

    @Test
    public void testSetAdn() {
        // Crear una instancia de ADNRequest
        ADNRequest adnRequest = new ADNRequest();

        // Definir un nuevo arreglo de ADN
        String[] newAdn = {"AGCTA", "TTGCA"};

        // Usar el método setAdn para establecer el valor
        adnRequest.setAdn(newAdn);

        // Verificar que el valor se establece correctamente
        assertArrayEquals(newAdn, adnRequest.getAdn());
    }
}
