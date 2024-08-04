package com.microservicio.cuenta.movimiento.cuenta_movimiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Cuenta;
import java.util.List;


@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long>{
        boolean   existsByNumeroCuenta(Long numeroCuenta);

        List<Cuenta> findByClienteid(Long clienteid);
}
