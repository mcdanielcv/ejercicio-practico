package com.microservicio.cuenta.movimiento.cuenta_movimiento.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Movimiento;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.MovimientoId;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, MovimientoId> {

    @Query(value = "SELECT a from Movimiento a  where a.id.numeroCuenta=?1 and a.id.fecha between ?2 and ?3")
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, Date fechaInicio, Date fechaFin);

    
    /*
        @Query(value = "SELECT new ResultDTO(c.id, o.id, p.id, c.name, c.email, o.orderDate, p.productName, p.price) "
    + " from Customer c, CustomerOrder o ,Product p "
    + " where c.id=o.customer.id "
    + " and o.id=p.customerOrder.id "
    + " and c.id=?1 ")List<ResultDTO> findResultDTOByCustomer(Long id); */
}
