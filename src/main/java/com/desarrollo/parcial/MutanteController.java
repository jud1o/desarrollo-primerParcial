package com.desarrollo.parcial;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if (esMutante) {
            return ResponseEntity.ok("Es un mutante");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es un mutante");
        }
    }
}

