package com.microservicio.cliente.persona.cliente_persona.services;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicio.cliente.persona.cliente_persona.configuration.RabbitConfig;
import com.microservicio.cliente.persona.cliente_persona.entities.Cliente;
import com.microservicio.cliente.persona.cliente_persona.repositories.ClienteRepository;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(@NonNull Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizarCliente(Cliente cliente, Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteDb = clienteOptional.get();
            clienteDb.setNombre(cliente.getNombre());
            clienteDb.setDireccion(cliente.getDireccion());
            clienteDb.setTelefono(cliente.getTelefono());
            clienteDb.setContrasena(cliente.getContrasena());
            return clienteRepository.save(clienteDb);
        } else {
            throw new RuntimeException("El cliente no existe para actualizar");
        }
    }

    @Transactional
    public Cliente borrarClientePorId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            clienteRepository.deleteById(id);
            return clienteOptional.get();
        }else{
            throw new RuntimeException("El cliente no existe para eliminar");
        }
    }

    @Transactional(readOnly = true)
    public String obtenerNombreCliente(Long clienteId){
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        if(clienteOptional.isPresent()){
            return clienteOptional.get().getNombre();
        }else{
            throw new RuntimeException("No existe cliente con el id: "+clienteId);
        }
    }

    @Transactional(readOnly = true)
    public List<Long> obtenerTodosIdClientes(){
        return clienteRepository.findAllIdClientes();
    }

    @RabbitListener(queues = RabbitConfig.MOVIMIENTO_A_CLIENTE_QUEUE)
    public void recibirMensaje(String mensaje) {
        System.out.println("Mensaje recibido desde cuenta-movimiento: " + mensaje);
        // Lógica para procesar el mensaje
    }
}
