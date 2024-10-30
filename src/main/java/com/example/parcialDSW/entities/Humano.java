package com.example.parcialDSW.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @Column(name = "dnaConcatenado", unique = true, length = 10000)
    private String dnaConcatenado;

    @JsonIgnore
    @Column(name = "esMutante")
    private boolean esMutante;

}