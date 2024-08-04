package com.microservicio.cuenta.movimiento.cuenta_movimiento.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Cuenta;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.entities.Movimiento;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.model.ReporteDTO;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.repositories.CuentaRepository;
import com.microservicio.cuenta.movimiento.cuenta_movimiento.repositories.MovimientoRepository;

@Service
public class CuentaServiceImp implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ClienteApiService clienteApiService;

    @Transactional(readOnly = true)
    public List<Cuenta> obtenerTodasCuentas() {
        return cuentaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cuenta> buscarPorId(@NonNull Long id) {
        return cuentaRepository.findById(id);
    }

    @Transactional
    public Cuenta guadarCuenta(Cuenta cuenta) {
        if (cuentaRepository.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            throw new RuntimeException("La Cuenta ya existe");
        }
        ;
        cuenta.setSaldoDisponible(cuenta.getSaldoInicial());
        Cuenta savedCuenta = cuentaRepository.save(cuenta);
        return savedCuenta;
    }

    @Transactional
    public Cuenta actualizarCuenta(Cuenta cuenta, Long id) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
        if (cuentaOptional.isPresent()) {
            Cuenta cuentaDb = cuentaOptional.get();
            cuentaDb.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDb.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDb.setEstado(cuenta.isEstado());
            return cuentaRepository.save(cuentaDb);
        } else {
            throw new RuntimeException("La cuenta no existe");
        }
    }

    @Transactional
    public Cuenta borrarCuentaPorId(Long id) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
        if (cuentaOptional.isPresent()) {
            cuentaRepository.deleteById(id);
            return cuentaOptional.get();
        } else {
            throw new RuntimeException("La cuenta no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<ReporteDTO> generarReporte(Long clienteId, String fechaInicio, String fechaFin) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/M/yyyy");
        Date dataInicio = formato.parse(fechaInicio);
        Date dataFin = formato.parse(fechaFin);

        List<Cuenta> cuentas = cuentaRepository.findByClienteid(clienteId);

        return cuentas.stream().map(cuenta -> {
            List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getNumeroCuenta(),
                    dataInicio, dataFin);
            return movimientos.stream().map(movimiento -> {
                ReporteDTO reporteDTO = new ReporteDTO();
                reporteDTO.setCliente(clienteApiService.obtenerNombreCliente(clienteId));
                reporteDTO.setFecha(formato.format(movimiento.getId().getFecha()));
                reporteDTO.setNumeroCuenta(cuenta.getNumeroCuenta().toString());
                reporteDTO.setTipo(cuenta.getTipoCuenta());
                reporteDTO.setSaldoInicial(cuenta.getSaldoInicial());
                reporteDTO.setMovimiento(movimiento.getId().getValor());
                reporteDTO.setSaldoDisponible(movimiento.getSaldo());

                return reporteDTO;
            }).collect(Collectors.toList());
        }).flatMap(List::stream).collect(Collectors.toList());
    }
}
