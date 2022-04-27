package com.alucar.domain.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Carro {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="carro_id")
    private Integer carroId;

    //@NotBlank // impede que seja passado campo em branco ou nulo
    @Size(max = 30) //limita tamanho máximo de caracteres
    private String modelo;

    private double valor;

    //@NotBlank // impede que seja passado campo em branco ou nulo
    @Size(max = 7) //limita tamanho máximo de caracteres
    private String placa;

    private int rating;

    @Column(name="unid_disponiveis")
    private int unidDisponiveis;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categorias categorias;

    @ManyToOne
    @JoinColumn(name = "imagens_id")
    private Imagens imagens;

    @ManyToOne
    @JoinColumn(name = "caracteristicas_id")
    private Caracteristicas caracteristicas;

}
