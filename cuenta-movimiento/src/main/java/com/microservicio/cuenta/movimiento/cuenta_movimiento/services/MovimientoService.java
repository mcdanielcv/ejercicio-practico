package com.microservicio.cuenta.movimiento.cuenta_movimiento.services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Movimiento;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.MovimientoId;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.MovimientoVo;

public interface MovimientoService {

    public List<Movimiento> obtenerTodosMovimientos();

    public Optional<Movimiento> buscarPorId(@NonNull MovimientoId id);

    public Movimiento guardarMovimiento(MovimientoVo movimientoVo) throws ParseException;

    Movimiento borrarMovimientoPorId(MovimientoVo movimiento) throws ParseException;
}
