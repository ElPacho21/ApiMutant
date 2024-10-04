package com.example.parcialDSW.services;

import com.example.parcialDSW.entities.StatsDTO;

public interface IHumanoService {
    boolean isMutant(String[] dna);
    StatsDTO obtenerEstadisticas();

}
