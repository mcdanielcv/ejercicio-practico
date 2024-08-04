package com.microservicio.cliente.persona.cliente_persona.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.cliente.persona.cliente_persona.entities.Cliente;
import com.microservicio.cliente.persona.cliente_persona.model.ResponseVo;
import com.microservicio.cliente.persona.cliente_persona.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clientes")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> obtenerTodosClientes() {
        List<Cliente> list = clienteService.obtenerClientes();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseVo(false, "No existen datos"));
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseVo(true, "", list));
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        try {
            if (result.hasErrors())
                return validacionDatos(result);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseVo(true, "Cliente registrado", clienteService.guardarCliente(cliente)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@Valid @RequestBody Cliente cliente, BindingResult result,
            @PathVariable Long id) {
        try {
            if (result.hasErrors()) {
                return validacionDatos(result);
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseVo(true, "Cliente Actualizado", clienteService.actualizarCliente(cliente, id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseVo(true, "Cliente Borrado", clienteService.borrarClientePorId(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false, e.getMessage()));
        }
    }

    private ResponseEntity<?> validacionDatos(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> obtenerNombreCliente(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(clienteService.obtenerNombreCliente(clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseVo(false, e.getMessage()));
        }
    }

    @GetMapping("/cliente/")
    public List<Long> obtenerTodosIdClientes() {
        return  clienteService.obtenerTodosIdClientes();
    }
}
