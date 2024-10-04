package com.example.parcialDSW.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "humano")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Humano extends Base{

    @Column(name = "dna")
    @ElementCollection
    private List<String> dna;

    @JsonIgnore
    @Column(name = "esMutante")
    private boolean esMutante;

}