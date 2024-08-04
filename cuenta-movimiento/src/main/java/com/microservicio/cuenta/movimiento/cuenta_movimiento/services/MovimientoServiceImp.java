package com.microservicio.cuenta.movimiento.cuenta_movimiento.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.configuracion.RabbitConfig;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Cuenta;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Movimiento;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.MovimientoId;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.MovimientoVo;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.repositories.CuentaRepository;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.repositories.MovimientoRepository;

@Service
public class MovimientoServiceImp implements MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional(readOnly = true)
    public List<Movimiento> obtenerTodosMovimientos() {
        return movimientoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Movimiento> buscarPorId(@NonNull MovimientoId id) {
        return movimientoRepository.findById(id);
    }

    @Transactional
    public Movimiento guardarMovimiento(MovimientoVo movimientoVo) throws ParseException {
            Optional<Cuenta> cuentaOptional = cuentaRepository.findById(movimientoVo.getNumeroCuenta());
            if (cuentaOptional.isPresent()) {
                Cuenta cuentaDb = cuentaOptional.get();
                SimpleDateFormat formato = new SimpleDateFormat("dd/M/yyyy");
                Date dataFormateada = formato.parse(movimientoVo.getFecha()); 
                MovimientoId id = new MovimientoId(movimientoVo.getNumeroCuenta(), dataFormateada, 
                movimientoVo.getTipoMovimiento(), movimientoVo.getValor());
                Optional<Movimiento> movimientOptional = movimientoRepository.findById(id);   
                
                if (movimientOptional.isPresent()) {
                    throw new RuntimeException("Movimiento ya existe");
                } else {
                    if (movimientoVo.getValor() < 0 && cuentaDb.getSaldoDisponible() < Math.abs(movimientoVo.getValor())) {
                        throw new RuntimeException("Saldo no disponible");
                    }
                    cuentaDb.setSaldoDisponible(cuentaDb.getSaldoInicial() + (1 * movimientoVo.getValor()));
                    
                    
                    Movimiento movimiento = new Movimiento(id, cuentaDb.getSaldoDisponible());
                    Movimiento savedMovimiento = movimientoRepository.save(movimiento);
                    cuentaRepository.save(cuentaDb);
                    enviarMensaje(formato.format(savedMovimiento.getId().getFecha()));
                    return savedMovimiento;
                }
            } else {
                throw new RuntimeException("No existe la cuenta");
            }
    }

    @Transactional
    public Movimiento borrarMovimientoPorId(MovimientoVo movimientoVo) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/M/yyyy");
                Date dataFormateada = formato.parse(movimientoVo.getFecha()); 
        MovimientoId id = new MovimientoId(movimientoVo.getNumeroCuenta(), dataFormateada,
        movimientoVo.getTipoMovimiento(), movimientoVo.getValor());
        Optional<Movimiento> movimientoOptional = movimientoRepository.findById(id);
        if (movimientoOptional.isPresent()) {
            Optional<Cuenta> cuentaOptional = cuentaRepository
                    .findById(movimientoOptional.get().getId().getNumeroCuenta());
            if (cuentaOptional.isPresent()) {
                Cuenta cuentaDb = cuentaOptional.get();
                cuentaDb.setSaldoDisponible(cuentaDb.getSaldoInicial() + ((-1) * movimientoOptional.get().getId().getValor()));
                cuentaRepository.save(cuentaDb);
                movimientoRepository.deleteById(id);
                return movimientoOptional.get();
            } else {
                throw new RuntimeException("No Existe la cuenta para realizar el movimiento");
            }
        } else {
            throw new RuntimeException("No existe el movimiento a eliminar");
        }
    }

    public void enviarMensaje(String mensaje) {
        rabbitTemplate.convertAndSend(RabbitConfig.MOVIMIENTO_A_CLIENTE_QUEUE, mensaje);
    }
}
