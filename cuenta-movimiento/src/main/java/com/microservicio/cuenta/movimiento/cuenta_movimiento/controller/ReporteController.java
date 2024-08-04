package com.microservicio.cuenta.movimiento.cuenta_movimiento.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.ReporteDTO;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.ResponseVo;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.services.CuentaService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    

    @Autowired
    private CuentaService cuentaService;
    
    @GetMapping
    public ResponseEntity<?> estadoDeCuenta(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        try {
            List<ReporteDTO> reporte = cuentaService.generarReporte(clienteId, fechaInicio, fechaFin);
            return new ResponseEntity<>(reporte, HttpStatus.OK);
        } catch (RuntimeException | ParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false, e.getMessage()));
        }
    }
  
}
