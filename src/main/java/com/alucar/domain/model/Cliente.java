package com.alucar.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;





@EqualsAndHashCode(onlyExplicitlyIncluded = true) // verifica a igualdade de dois objetos, com a inclusão dos parenteses, temmos a comparação através de um campo determinado deforma explicita
@Data // gera Getter Setter ToString e Hashcodes
@Entity // define a classe como uma entidade, ou seja, uma tabela no banco de dados
@Table(name="cliente") // pode ser usado para nomear a tabela do BD da maneira que preferir, se não for usado, pega o próprio nome da classe
public class Cliente {

    @EqualsAndHashCode.Include // determina o campo id como fator de comparação explicitamente
    @Id //define a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cliente_id")
    private Integer clienteId;

    //@NotBlank // impede que seja passado campo em branco ou nulo
    @Size (max = 30) //limita tamanho máximo de caracteres
    @Column(name="cliente_nome")
    private String clienteNome;

    //@NotBlank // impede que seja passado campo em branco ou nulo
    @Size (max = 30) //limita tamanho máximo de caracteres
    @Column(name="cliente_sobrenome")
    private String clienteSobrenome;

    //@NotBlank
    @Email // valida email válido (sintaxe correta)
    @Size (max = 255)
    private String email;


    @Size (max = 20) //limita tamanhop máximo de caracteres
    private String telefone;


    @Size (max = 10) //limita tamanhop máximo de caracteres
    @Column(name="data_nascimento")
    private String dataNascimento;


    @Size (max = 15) //limita tamanhop máximo de caracteres
    private String cpf;


    @Size (max = 20) //limita tamanhop máximo de caracteres
    private String cnh;

    //@NotBlank // impede que seja passado campo em branco ou nulo
    @Size (max = 100) //limita tamanhop máximo de caracteres
    private String senha;

    @ManyToOne
    @JoinColumn(name = "funcao_id")
    private Funcao funcao;

    private boolean ativo = true;

}
