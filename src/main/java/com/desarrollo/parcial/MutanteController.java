package com.desarrollo.parcial;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.desarrollo.parcial.model.Mutante;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mutante")
public class MutanteController {

    private final MutanteService mutanteService;

    @Autowired
    public MutanteController(MutanteService mutanteService) {
        this.mutanteService = mutanteService;
    }

    @PostMapping("/")
    public ResponseEntity<String> verificarMutante(@RequestBody ADNRequest request) {
        boolean esMutante = mutanteService.isMutant(request.getAdn());

        // Guarda el ADN en la base de datos
        mutanteService.guardarADN(request.getAdn());

        if (esMutante) {
            return ResponseEntity.ok("Es un mutante");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es un mutante");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Mutante>> obtenerMutantes() {
        List<Mutante> mutantes = mutanteService.obtenerTodosLosMutantes();
        return ResponseEntity.ok(mutantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mutante> obtenerMutantePorId(@PathVariable Long id) {
        Optional<Mutante> mutante = mutanteService.obtenerMutantePorId(id);
        return mutante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Método para obtener estadísticas
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> obtenerEstadisticas() {
        StatsResponse stats = mutanteService.obtenerEstadisticas();
        return ResponseEntity.ok(stats);
    }
}


