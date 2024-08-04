package com.microservicio.cuenta.movimiento.cuenta_movimiento.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cuenta {
    @Id
    @Column(unique = true)
    private Long numeroCuenta;
    @NotBlank
    private String tipoCuenta;
    @NotNull
    private double saldoInicial;
    @NotNull
    private double saldoDisponible;
    @NotNull
    private boolean estado;
    @NotNull
    private Long clienteid;

}
