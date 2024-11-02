package com.desarrollo.parcial;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import com.desarrollo.parcial.model.Mutante;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
class MutanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MutanteService mutanteService;

    @InjectMocks
    private MutanteController mutanteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testVerificarMutante() throws Exception {
        String[] adn = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        ADNRequest request = new ADNRequest(adn);

        when(mutanteService.isMutant(adn)).thenReturn(true);
        doNothing().when(mutanteService).guardarADN(adn);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/mutante/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Es un mutante"));

        verify(mutanteService, times(1)).guardarADN(adn);
    }

    @Test
    void testObtenerMutantes() throws Exception {
        List<Mutante> mutantes = List.of(new Mutante("ATGCGA", true), new Mutante("TTTTTT", false));
        when(mutanteService.obtenerTodosLosMutantes()).thenReturn(mutantes);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/mutante/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].adn").value("ATGCGA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].esMutante").value(false));
    }

    @Test
    void testObtenerMutantePorId() throws Exception {
        Mutante mutante = new Mutante("ATGCGA", true);
        when(mutanteService.obtenerMutantePorId(anyLong())).thenReturn(Optional.of(mutante));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/mutante/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.adn").value("ATGCGA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.esMutante").value(true));
    }

    @Test
    void testObtenerMutantePorIdNoEncontrado() throws Exception {
        when(mutanteService.obtenerMutantePorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/mutante/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testObtenerEstadisticas() throws Exception {
        StatsResponse stats = new StatsResponse(2, 1, 2.0);
        when(mutanteService.obtenerEstadisticas()).thenReturn(stats);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/mutante/stats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.countMutantDna").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countHumanDna").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratio").value(2.0));
    }
}
