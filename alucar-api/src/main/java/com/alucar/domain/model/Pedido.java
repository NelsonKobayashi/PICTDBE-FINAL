package com.alucar.domain.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pedido_id")
    private Integer pedidoId;

    @Column(name="data_retirada")
    private LocalDate dataRetirada;

    @NotBlank // impede que seja passado campo em branco ou nulo
    @Size(max = 15) //limita tamanhop máximo de caracteres
    @Column(name="horario_retirada")
    private String horarioRetirada;

    @Column(name="data_entrega")
    private LocalDate dataEntrega;

    @NotBlank // impede que seja passado campo em branco ou nulo
    @Size (max = 15) //limita tamanhop máximo de caracteres
    @Column(name="horario_entrega")
    private String horarioEntrega;

    @NotBlank // impede que seja passado campo em branco ou nulo
    @Size (max = 60) //limita tamanhop máximo de caracteres
    @Column(name="local_retirada")
    private String localRetirada;

    @NotBlank // impede que seja passado campo em branco ou nulo
    @Size (max = 60) //limita tamanhop máximo de caracteres
    @Column(name="local_entrega")
    private String localEntrega;

    @ManyToOne
    //@JoinColumn(name = "cliente_id") // anotação para determinar o nome da coluna no BD, caso não colocado, assumi o default ("classe"_id)
    private Cliente cliente;

    @ManyToOne
    private Cidades cidades;

    @ManyToOne
    private Carro carro;
}
