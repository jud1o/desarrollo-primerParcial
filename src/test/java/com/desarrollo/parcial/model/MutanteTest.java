package com.desarrollo.parcial.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutanteTest {

    @Test
    public void testMutante() {
        //Crear instancia Mutante
        Mutante mutante = new Mutante();

        //Comprobar que la instancia se creee correctamente
        assertNotNull(mutante);

        //Verificar ID null
        assertNull(mutante.getId());

        //Verificar ID null
        assertNull(mutante.getAdn());

        //Verificar esMutante falso por defecto
        assertFalse(mutante.isEsMutante());
    }
    @Test
    public void testSetAdn() {
        //Crear Mutante
        Mutante mutante = new Mutante();

        //Definir ADN
        String adn = "AATAG";

        //Usar setAdn para establecer el valor
        mutante.setAdn(adn);

        //Verificar que el valor se establezca correctamente
        assertEquals(adn, mutante.getAdn());
    }
    @Test
    public void testSetEsMutante() {
        Mutante mutante = new Mutante();

        //Definir booleano
        boolean esMutante = true;

        mutante.setEsMutante(esMutante);

        assertTrue(mutante.isEsMutante());

        //Probar con false
        mutante.setEsMutante(false);
        assertFalse(mutante.isEsMutante());
    }
}
