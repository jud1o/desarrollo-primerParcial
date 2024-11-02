package com.desarrollo.parcial;

import com.desarrollo.parcial.model.Mutante;
import com.desarrollo.parcial.repository.MutanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutanteServiceTest {

    @Mock
    private MutanteRepository mutanteRepository;

    @InjectMocks
    private MutanteService mutanteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsMutantTrue() {
        String[] adnMutante = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(MutanteService.isMutant(adnMutante));
    }

    @Test
    void testIsMutantFalse() {
        String[] adnHumano = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        assertFalse(MutanteService.isMutant(adnHumano));
    }

    @Test
    void testGuardarADNNuevo() {
        String[] adn = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        String adnCadena = String.join("", adn);

        when(mutanteRepository.findByAdn(adnCadena)).thenReturn(Optional.empty());

        mutanteService.guardarADN(adn);

        verify(mutanteRepository, times(1)).save(any(Mutante.class));
    }

    @Test
    void testGuardarADNExistenteNoGuarda() {
        String[] adn = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        String adnCadena = String.join("", adn);

        Mutante mutanteExistente = new Mutante(adnCadena, true);
        when(mutanteRepository.findByAdn(adnCadena)).thenReturn(Optional.of(mutanteExistente));

        mutanteService.guardarADN(adn);

        verify(mutanteRepository, never()).save(any(Mutante.class));
    }

    @Test
    void testObtenerTodosLosMutantes() {
        List<Mutante> listaMutantes = List.of(new Mutante("ATGCGA", true), new Mutante("TTTTTT", false));
        when(mutanteRepository.findAll()).thenReturn(listaMutantes);

        List<Mutante> resultado = mutanteService.obtenerTodosLosMutantes();

        assertEquals(2, resultado.size());
        assertEquals("ATGCGA", resultado.get(0).getAdn());
        assertTrue(resultado.get(0).isEsMutante());
    }

    @Test
    void testObtenerMutantePorId() {
        Mutante mutante = new Mutante("ATGCGA", true);
        when(mutanteRepository.findById(1L)).thenReturn(Optional.of(mutante));

        Optional<Mutante> resultado = mutanteService.obtenerMutantePorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("ATGCGA", resultado.get().getAdn());
        assertTrue(resultado.get().isEsMutante());
    }

    @Test
    void testObtenerEstadisticas() {
        List<Mutante> mutantes = List.of(
                new Mutante("ATGCGA", true),
                new Mutante("TCACTG", false),
                new Mutante("TTTTTT", true)
        );

        when(mutanteRepository.findAll()).thenReturn(mutantes);

        StatsResponse stats = mutanteService.obtenerEstadisticas();

        assertEquals(2, stats.getCountMutantDna());
        assertEquals(1, stats.getCountHumanDna());
        assertEquals(2.0, stats.getRatio());
    }
}
