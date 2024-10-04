package com.example.parcialDSW.repositories;


import com.example.parcialDSW.entities.Humano;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HumanoRepository extends BaseRepository<Humano, Long> {
    @Query(value = "SELECT COUNT(*) FROM Humano h WHERE h.esMutante = true")
    long countMutant();

    @Query(value = "SELECT COUNT(*) FROM Humano h")
    long countHuman();

}
