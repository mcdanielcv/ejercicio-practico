package com.microservicio.cliente.persona.cliente_persona.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Persona {

    @NotBlank
    private String nombre;

    private String genero;

    private int edad;

    private String identificacion;
    @NotBlank
    private String direccion;
    @NotBlank
    private String telefono;
}
