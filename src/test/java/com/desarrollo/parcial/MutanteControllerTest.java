package com.desarrollo.parcial;

import com.desarrollo.parcial.model.Mutante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MutanteControllerTest {

    @InjectMocks
    private MutanteController mutanteController;

    @Mock
    private MutanteService mutanteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVerificarMutante_EsMutante() {
        ADNRequest request = new ADNRequest();
        request.setAdn(Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG").toArray(new String[0]));

        when(mutanteService.isMutant(any())).thenReturn(true);

        ResponseEntity<String> response = mutanteController.verificarMutante(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Es un mutante", response.getBody());
        verify(mutanteService, times(1)).guardarADN(any());
    }

    @Test
    public void testVerificarMutante_NoEsMutante() {
        ADNRequest request = new ADNRequest();
        request.setAdn(Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CTGAAA", "TCACTG").toArray(new String[0]));

        when(mutanteService.isMutant(any())).thenReturn(false);

        ResponseEntity<String> response = mutanteController.verificarMutante(request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("No es un mutante", response.getBody());
        verify(mutanteService, times(1)).guardarADN(any());
    }

    @Test
    public void testObtenerMutantes() {
        List<Mutante> mutantes = Arrays.asList(new Mutante(), new Mutante());
        when(mutanteService.obtenerTodosLosMutantes()).thenReturn(mutantes);

        ResponseEntity<List<Mutante>> response = mutanteController.obtenerMutantes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mutantes, response.getBody());
    }

    @Test
    public void testObtenerMutantePorId_Encontrado() {
        Long id = 1L;
        Mutante mutante = new Mutante();
        when(mutanteService.obtenerMutantePorId(id)).thenReturn(Optional.of(mutante));

        ResponseEntity<Mutante> response = mutanteController.obtenerMutantePorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mutante, response.getBody());
    }

    @Test
    public void testObtenerMutantePorId_NoEncontrado() {
        Long id = 1L;
        when(mutanteService.obtenerMutantePorId(id)).thenReturn(Optional.empty());

        ResponseEntity<Mutante> response = mutanteController.obtenerMutantePorId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testObtenerEstadisticas() {
        StatsResponse stats = new StatsResponse(100, 50, 2.0);
        when(mutanteService.obtenerEstadisticas()).thenReturn(stats);

        ResponseEntity<StatsResponse> response = mutanteController.obtenerEstadisticas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stats, response.getBody());
    }
}

