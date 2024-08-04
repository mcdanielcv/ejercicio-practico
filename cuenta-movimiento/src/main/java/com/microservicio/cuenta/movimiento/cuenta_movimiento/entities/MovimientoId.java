package com.microservicio.cuenta.movimiento.cuenta_movimiento.entities;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class MovimientoId implements Serializable {

    @NotNull
    private Long numeroCuenta;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotNull
    private String tipoMovimiento;

    @NotNull
    private Double valor;

    public MovimientoId() {}

    public MovimientoId(Long numeroCuenta, Date fecha, String tipoMovimiento, Double valor) {
        this.numeroCuenta = numeroCuenta;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovimientoId that = (MovimientoId) o;
        return numeroCuenta.equals(that.numeroCuenta) &&
               fecha.equals(that.fecha) &&
               tipoMovimiento.equals(that.tipoMovimiento) &&
               valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroCuenta, fecha, tipoMovimiento, valor);
    }
}
