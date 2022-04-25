package com.alucar.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Funcao implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funcao_id")
    private Long funcaoId;

    @Enumerated(EnumType.STRING)
    private Funcao.Authority authority;

    public String getAuthority() {
        return this.authority.name();
    }

    public enum Authority {
    CLIENTE, MODERADOR, ADMIN
    }
}
