package com.microservicio.cliente.persona.cliente_persona.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;



@Configuration
public class RabbitConfig {
    public static final String MOVIMIENTO_A_CLIENTE_QUEUE = "movimientoAClienteQueue";

    @Bean
    public Queue movimientoAClienteQueue() {
        return new Queue(MOVIMIENTO_A_CLIENTE_QUEUE, false);
    }
}
