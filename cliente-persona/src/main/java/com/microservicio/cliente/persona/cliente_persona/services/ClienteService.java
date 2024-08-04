package com.microservicio.cliente.persona.cliente_persona.services;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import com.microservicio.cliente.persona.cliente_persona.entities.Cliente;

public interface ClienteService {

    List<Cliente> obtenerClientes();

    Cliente guardarCliente(Cliente cliente);

    Cliente actualizarCliente(Cliente cliente, Long id);

    Cliente borrarClientePorId(Long id);

    Optional<Cliente> buscarPorId(@NonNull Long id);

    String obtenerNombreCliente(Long clienteId);

    List<Long> obtenerTodosIdClientes();
}
