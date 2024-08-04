package com.microservicio.cuenta.movimiento.cuenta_movimiento.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteDTO {
    private String fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private Double movimiento;
    private Double saldoDisponible;
}
