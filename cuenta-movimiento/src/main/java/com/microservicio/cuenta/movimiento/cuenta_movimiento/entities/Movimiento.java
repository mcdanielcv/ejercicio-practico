package com.microservicio.cuenta.movimiento.cuenta_movimiento.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Movimiento {

    @EmbeddedId
    private MovimientoId id;

    @NotNull
    private double saldo;

    public Movimiento(MovimientoId id, @NotNull double saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public Movimiento() {
    }
}
