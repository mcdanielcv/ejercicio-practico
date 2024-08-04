package com.microservicio.cliente.persona.cliente_persona;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.cliente.persona.cliente_persona.entities.Cliente;
import com.microservicio.cliente.persona.cliente_persona.repositories.ClienteRepository;

@SpringBootTest
@AutoConfigureMockMvc

public class IntegrationTest {
@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testCrearCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Jose Lema");
		cliente.setEdad(30);
		cliente.setContrasena("9875");
        cliente.setTelefono("0939241848");
        cliente.setDireccion("Calderon");
        cliente.setIdentificacion("1721521613");
		cliente.setEstado(true);


        ObjectMapper objectMapper = new ObjectMapper();
        String clienteJson = objectMapper.writeValueAsString(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andDo(print())
                .andExpect(status().isCreated());

        // Verifica que el cliente fue guardado en la base de datos
        List<Cliente> list = clienteRepository.findByIdentificacion("1721521613");
        Cliente clienteDb = list.get(0);

        assertThat(clienteDb).isNotNull();
        assertThat(clienteDb.getNombre()).isEqualTo("Jose Lema");
    }
}
