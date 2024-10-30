package com.example.parcialDSW.services;

import com.example.parcialDSW.DTOs.StatsDTO;
import com.example.parcialDSW.repositories.HumanoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;

class HumanoServiceTest {

    @Mock
    private HumanoRepository humanoRepository;

    @InjectMocks
    private HumanoService humanoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMutante(){
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        assertEquals(true, humanoService.isMutant(dna));
    }

    @Test
    public void testNoMutante(){
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        assertEquals(false, humanoService.isMutant(dna));
    }

    @Test
    public void testArrayVacio() {
        String[] dna = {};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            humanoService.isMutant(dna);
        });
        assertEquals("El array de ADN no puede ser nulo o vacio", exception.getMessage());
    }

    @Test
    public void testArrayNxM() {
        String[] dna = {"ATG", "CAGT", "TTAT"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            humanoService.isMutant(dna);
        });
        assertEquals("El array de ADN debe ser NxN", exception.getMessage());
    }

    @Test
    public void testArrayConNumeros() {
        String[] dna = {"A1G", "C2T", "TT3"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            humanoService.isMutant(dna);
        });
        assertEquals("El ADN contiene caracteres no permitidos. Solo se permiten A, T, C, G", exception.getMessage());
    }

    @Test
    public void testArrayNull() {
        String[] dna = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            humanoService.isMutant(dna);
        });
        assertEquals("El array de ADN no puede ser nulo o vacio", exception.getMessage());
    }

    @Test
    public void testArrayDeNulls() {
        String[] dna = {null, null, null};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            humanoService.isMutant(dna);
        });
        assertEquals("El array de ADN debe ser NxN", exception.getMessage());
    }

    @Test
    public void testArrayConOtrasLetras() {
        String[] dna = {"ATGC", "HAGT", "TTAT", "TCAA"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            humanoService.isMutant(dna);
        });
        assertEquals("El ADN contiene caracteres no permitidos. Solo se permiten A, T, C, G", exception.getMessage());
    }

    @Test
    public void testObtenerEstadisticas() {
        Mockito.when(humanoRepository.countHuman()).thenReturn(1L);
        Mockito.when(humanoRepository.countMutant()).thenReturn(1L);

        StatsDTO stats = humanoService.obtenerEstadisticas();

        assertEquals(1, stats.getCountHumanDna());
        assertEquals(1, stats.getCountMutantDna());
        assertEquals(1.0D, stats.getRatio());
    }
}