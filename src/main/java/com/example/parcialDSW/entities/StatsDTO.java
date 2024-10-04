package com.example.parcialDSW.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class StatsDTO {

    private long countMutantDna;
    private long countHumanDna;
    private double ratio;
}
