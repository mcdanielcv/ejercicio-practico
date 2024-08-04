package com.microservicio.cuenta.movimiento.cuenta_movimiento.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Movimiento;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.MovimientoVo;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.ResponseVo;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.services.MovimientoService;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.services.UtilitariosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;


    @Autowired
    private UtilitariosService utilitariosService;

    @GetMapping
    public ResponseEntity<?> obtenerTodosClientes() {
        List<Movimiento> list = movimientoService.obtenerTodosMovimientos();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseVo(false, "No existen datos"));
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseVo(true, "", list));
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarMovimiento(@Valid @RequestBody MovimientoVo movimiento, BindingResult result){
       try {
            if(result.hasErrors())
            return utilitariosService.validacionDatos(result);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseVo(true,"Movimiento registrado", movimientoService.guardarMovimiento(movimiento))); 
        } catch (RuntimeException | ParseException e) {
            System.out.println("error-<"+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false,e.getMessage())); 
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovimiento(MovimientoVo movimientoVo) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseVo(true,"Movimiento Eliminado", movimientoService.borrarMovimientoPorId(movimientoVo)));     
        } catch (RuntimeException | ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false,e.getMessage())); 
        }
    }
}
