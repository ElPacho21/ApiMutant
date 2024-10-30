package com.example.parcialDSW.controllers;

import com.example.parcialDSW.entities.Humano;
import com.example.parcialDSW.DTOs.StatsDTO;
import com.example.parcialDSW.services.HumanoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/adn")

public class HumanoController {

    private final HumanoService humanoService;

    public HumanoController(HumanoService humanoService) {
        this.humanoService = humanoService;
    }

    @PostMapping("/mutant")
    public ResponseEntity<?> verificarMutante(@RequestBody Humano humano) {
        try{
            String[] dnaArray = humano.getDna().toArray(new String[0]);
            if (humanoService.isMutant(dnaArray)) {
                return ResponseEntity.status(HttpStatus.OK).body("Es Mutante");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(("{\"error\": \"" + "No es mutante" + "\"}"));
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> obtenerEstadisticas() {
        try{
            StatsDTO stats = humanoService.obtenerEstadisticas();
            return ResponseEntity.status(HttpStatus.OK).body(stats);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
}
