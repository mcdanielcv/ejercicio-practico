package com.microservicio.cuenta.movimiento.cuenta_movimiento.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String MOVIMIENTO_A_CLIENTE_QUEUE = "movimientoAClienteQueue";

    @Bean
    public Queue movimientoAClienteQueue() {
        return new Queue(MOVIMIENTO_A_CLIENTE_QUEUE, false);
    }
}
