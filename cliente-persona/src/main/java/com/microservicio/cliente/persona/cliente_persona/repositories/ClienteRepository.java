package com.microservicio.cliente.persona.cliente_persona.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservicio.cliente.persona.cliente_persona.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>{

    List<Cliente> findByIdentificacion(String identificacion);

    @Query(value = "SELECT a.clienteid from Cliente a")
    List<Long> findAllIdClientes();
}
