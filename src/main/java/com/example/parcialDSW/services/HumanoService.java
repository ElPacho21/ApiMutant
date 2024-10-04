package com.example.parcialDSW.services;

import com.example.parcialDSW.entities.Humano;
import com.example.parcialDSW.entities.StatsDTO;
import com.example.parcialDSW.repositories.HumanoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class HumanoService implements IHumanoService {

    private HumanoRepository humanoRepository;

    public HumanoService(HumanoRepository humanoRepository) {
        this.humanoRepository = humanoRepository;
    }

    @Override
    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El array de ADN no puede ser nulo o vacio");
        }
        int n = dna.length;

        for (String fila : dna) {
            if (fila == null || fila.length() != n) {
                throw new IllegalArgumentException("El array de ADN debe ser NxN");
            }
        }

        for (String fila : dna) {
            for (char base : fila.toCharArray()) {
                if (base != 'A' && base != 'T' && base != 'C' && base != 'G') {
                    throw new IllegalArgumentException("El ADN contiene caracteres no permitidos. Solo se permiten A, T, C, G");
                }
            }
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<Boolean>> resultados = new ArrayList<>();

        resultados.add(executorService.submit(() -> analizarDireccion(dna, n, 0, 1)));
        resultados.add(executorService.submit(() -> analizarDireccion(dna, n, 1, 0)));
        resultados.add(executorService.submit(() -> analizarDireccion(dna, n, 1, 1)));
        resultados.add(executorService.submit(() -> analizarDireccion(dna, n, -1, 1)));

        executorService.shutdown();

        int secuenciasEncontradas = 0;
        for (Future<Boolean> resultado : resultados) {
            try {
                if (resultado.get()) secuenciasEncontradas++;
                if (secuenciasEncontradas > 1) {
                    guardarADN(dna, true);
                    return true;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        guardarADN(dna, false);
        return false;
    }

    private boolean analizarDireccion(String[] dna, int n, int dx, int dy) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (esSecuenciaValida(dna, i, j, dx, dy)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean esSecuenciaValida(String[] dna, int x, int y, int dx, int dy) {
        int n = dna.length;
        if (x + 3 * dx >= n || y + 3 * dy >= n || x + 3 * dx < 0 || y + 3 * dy < 0) {
            return false;
        }
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
        humano.setEsMutante(esMutante);
        humanoRepository.save(humano);
    }

//    @Override
//    public boolean isMutant(String[] dna) {
//        try{
//            if (dna == null || dna.length == 0) {
//                throw new IllegalArgumentException("El array de ADN no puede ser nulo o vacio");
//            }
//            int n = dna.length;
//
//            for (String fila : dna) {
//                if (fila == null || fila.length() != n) {
//                    throw new IllegalArgumentException("El array de ADN debe ser NxN");
//                }
//            }
//
//            for (String fila : dna) {
//                for (char base : fila.toCharArray()) {
//                    if (base != 'A' && base != 'T' && base != 'C' && base != 'G') {
//                        throw new IllegalArgumentException("El ADN contiene caracteres no permitidos. Solo se permiten A, T, C, G");
//                    }
//                }
//            }
//
//            int secuenciasEncontradas = 0;
//
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < n; j++) {
//                    if (j + 3 < n && comprobarSecuencia(dna, i, j, 0, 1)) secuenciasEncontradas++;
//                    if (i + 3 < n && comprobarSecuencia(dna, i, j, 1, 0)) secuenciasEncontradas++;
//                    if (i + 3 < n && j + 3 < n && comprobarSecuencia(dna, i, j, 1, 1)) secuenciasEncontradas++;
//                    if (i - 3 >= 0 && j + 3 < n && comprobarSecuencia(dna, i, j, -1, 1)) secuenciasEncontradas++;
//
//                    if (secuenciasEncontradas > 0) {
//                        guardarADN(dna, true);
//                        return true;
//                    }
//                }
//            }
//
//            guardarADN(dna, false);
//            return false;
//        }
//        catch(Exception e){
//            e.getMessage();
//            return false;
//        }
//    }
//
//    private boolean comprobarSecuencia(String[] dna, int x, int y, int dx, int dy) {
//        char base = dna[x].charAt(y);
//        for (int i = 1; i < 4; i++) {
//            if (dna[x + i * dx].charAt(y + i * dy) != base) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void guardarADN(String[] dna, boolean esMutante) {
//        Humano humano = new Humano();
//        humano.setDna(Arrays.asList(dna));
//        humano.setEsMutante(esMutante);
//        humanoRepository.save(humano);
//    }

    @Override
    public StatsDTO obtenerEstadisticas() {
        long countMutantDna = humanoRepository.countMutant();
        long countHumanDna = humanoRepository.countHuman();

        return new StatsDTO(countMutantDna, countHumanDna, countHumanDna == 0? 0 : (double)countMutantDna/countHumanDna);
    }
}
