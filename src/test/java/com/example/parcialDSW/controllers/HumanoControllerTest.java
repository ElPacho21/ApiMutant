package com.example.parcialDSW.controllers;

import com.example.parcialDSW.entities.Humano;
import com.example.parcialDSW.services.HumanoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HumanoControllerTest {

    @Mock
    private HumanoService humanoService;

    private HumanoController humanoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        humanoController = new HumanoController(humanoService);
    }

    @Test
    public void testIsMutantController(){
        String[] dnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        String[] dnaNoMutant = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

        Mockito.when(humanoService.isMutant(dnaMutant)).thenReturn(true);
        Mockito.when(humanoService.isMutant(dnaNoMutant)).thenReturn(false);

        Humano mutant = new Humano();
        mutant.setDna(Arrays.asList(dnaMutant));

        ResponseEntity<?> responseMutante = humanoController.verificarMutante(mutant);

        assertEquals(HttpStatus.OK, responseMutante.getStatusCode());
        assertEquals("Es Mutante", responseMutante.getBody());

        Humano humanoNoMutante = new Humano();
        humanoNoMutante.setDna(Arrays.asList(dnaNoMutant));

        ResponseEntity<?> responseNoMutante = humanoController.verificarMutante(humanoNoMutante);

        assertEquals(HttpStatus.FORBIDDEN, responseNoMutante.getStatusCode());
        assertEquals("{\"error\": \"No es mutante\"}", responseNoMutante.getBody());
    }
}