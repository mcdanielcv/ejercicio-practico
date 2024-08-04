package com.microservicio.cuenta.movimiento.cuenta_movimiento.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimientoVo {
    private Long numeroCuenta;

    private String fecha;

    private String tipoMovimiento;

    private Double valor;

    public MovimientoVo(Long numeroCuenta, String tipoMovimiento, String fecha, Double valor) {
        this.numeroCuenta = numeroCuenta;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
    }
}
