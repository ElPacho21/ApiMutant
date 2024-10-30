package com.example.parcialDSW.services;

import com.example.parcialDSW.DTOs.StatsDTO;

public interface IHumanoService {
    boolean isMutant(String[] dna);
    StatsDTO obtenerEstadisticas();

}
