package com.microservicio.cuenta.movimiento.cuenta_movimiento.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteApiService {


    @Autowired
    private RestTemplate restTemplate;

    private static final String CLIENTE_PERSONA_URL = "http://localhost:8080";

    public String obtenerNombreCliente(Long clienteId) {
        String url = CLIENTE_PERSONA_URL + "/clientes/cliente/" + clienteId;
        return restTemplate.getForObject(url, String.class);
    }

    @SuppressWarnings("unchecked")
    public List<Long> listarTodosIdClientes() {
        String url = CLIENTE_PERSONA_URL + "/clientes/cliente/";
        return restTemplate.getForObject(url, List.class);
    }
}
