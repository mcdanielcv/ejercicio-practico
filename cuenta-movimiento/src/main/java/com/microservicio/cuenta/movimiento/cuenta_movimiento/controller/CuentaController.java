package com.microservicio.cuenta.movimiento.cuenta_movimiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Cuenta;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.ResponseVo;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.services.CuentaService;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.services.UtilitariosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;
    @Autowired
    UtilitariosService utilitariosService;

    @GetMapping
    public ResponseEntity<?> obtenerTodasCuentas() {
        List<Cuenta> list = cuentaService.obtenerTodasCuentas();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseVo(false, "No existen datos"));
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseVo(true, "", list));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearCuenta(@Valid @RequestBody Cuenta cuenta, BindingResult result) {
        try {
            if (result.hasErrors())
                return utilitariosService.validacionDatos(result);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseVo(true, "Cuenta creada", cuentaService.guadarCuenta(cuenta)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCuenta(@Valid @RequestBody Cuenta cuenta, BindingResult result,
            @PathVariable Long id) {
        try {
            if (result.hasErrors()) {
                return utilitariosService.validacionDatos(result);
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseVo(true, "Cuenta Actualizado", cuentaService.actualizarCuenta(cuenta, id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseVo(true, "Cuenta Borrada", cuentaService.borrarCuentaPorId(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false, e.getMessage()));
        }
    }

    
}
