package com.microservicio.cuenta.movimiento.cuenta_movimiento.services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Cuenta;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.ReporteDTO;

public interface CuentaService {

    List<Cuenta> obtenerTodasCuentas();

    Optional<Cuenta> buscarPorId(@NonNull Long id);

    Cuenta guadarCuenta(Cuenta Cuenta);

    Cuenta actualizarCuenta(Cuenta cuenta, Long id);

    Cuenta borrarCuentaPorId(Long id);

    List<ReporteDTO> generarReporte(Long clienteId, String fechaInicio, String fechaFin)  throws ParseException; 
  
}
