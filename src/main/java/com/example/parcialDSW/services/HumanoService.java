package com.example.parcialDSW.services;

import com.example.parcialDSW.entities.Humano;
import com.example.parcialDSW.DTOs.StatsDTO;
import com.example.parcialDSW.repositories.HumanoRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HumanoService implements IHumanoService {

    private HumanoRepository humanoRepository;

    public HumanoService(HumanoRepository humanoRepository) {
        this.humanoRepository = humanoRepository;
    }

    @Override
    public boolean isMutant(String[] dna) {

        // Catchs de algunas posibles excepciones
        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El array de ADN no puede ser nulo o vacio");
        }
        int n = dna.length;

        // Validar que sea una matriz NxN
        for (String fila : dna) {
            if (fila == null || fila.length() != n) {
                throw new IllegalArgumentException("El array de ADN debe ser NxN");
            }
            for (char base : fila.toCharArray()) {
                if (base != 'A' && base != 'T' && base != 'C' && base != 'G') {
                    throw new IllegalArgumentException("El ADN contiene caracteres no permitidos. Solo se permiten A, T, C, G");
                }
            }
        }

        // Si el ADN ya existe en la base de datos, no se vuelve a agregar, se toma el valor de esMutante
        String dnaConcatenado = String.join("", dna);
        Optional<Humano> humanoExistente = humanoRepository.buscarDnaConcatenado(dnaConcatenado);

        if (humanoExistente.isPresent()) {
            return humanoExistente.get().isEsMutante();
        }

        Set<Character> secuenciasEncontradas = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j + 3 < n && comprobarSecuencia(dna, i, j, 0, 1)) {
                    secuenciasEncontradas.add(dna[i].charAt(j));
                }
                if (i + 3 < n && comprobarSecuencia(dna, i, j, 1, 0)) {
                    secuenciasEncontradas.add(dna[i].charAt(j));
                }
                if (i + 3 < n && j + 3 < n && comprobarSecuencia(dna, i, j, 1, 1)) {
                    secuenciasEncontradas.add(dna[i].charAt(j));
                }
                if (i - 3 >= 0 && j + 3 < n && comprobarSecuencia(dna, i, j, -1, 1)) {
                    secuenciasEncontradas.add(dna[i].charAt(j));
                }
                
                if (secuenciasEncontradas.size() > 1) {
                    guardarADN(dna, true);
                    return true;
                }
            }
        }

        guardarADN(dna, false);
        return false;
    }

    private boolean comprobarSecuencia(String[] dna, int x, int y, int dx, int dy) {
        char base = dna[x].charAt(y);
        for (int i = 1; i < 4; i++) {
            if (dna[x + i * dx].charAt(y + i * dy) != base) {
                return false;
            }
        }
        return true;
    }

    private void guardarADN(String[] dna, boolean esMutante) {
        Humano humano = new Humano();
        humano.setDna(Arrays.asList(dna));

        String dnaConcatenado = concatenarADN(dna);
        humano.setDnaConcatenado(dnaConcatenado);

        humano.setEsMutante(esMutante);
        humanoRepository.save(humano);

    }

    private String concatenarADN(String[] dna) {
        return String.join("", dna);
    }

    @Override
    public StatsDTO obtenerEstadisticas() {
        long countMutantDna = humanoRepository.countMutant();
        long countHumanDna = humanoRepository.countHuman();

        return new StatsDTO(countMutantDna, countHumanDna, countHumanDna == 0? 0 : (double)countMutantDna/countHumanDna);
    }
}
