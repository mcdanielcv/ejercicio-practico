package com.microservicio.cliente.persona.cliente_persona;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.cliente.persona.cliente_persona.entities.Cliente;
import com.microservicio.cliente.persona.cliente_persona.repositories.ClienteRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ClientePersonaApplicationTests {


	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

 	private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setClienteid(4L);
        cliente.setNombre("Jose Lema");
        cliente.setEdad(30);
        cliente.setContrasena("9875");
        cliente.setEstado(true);
    }


	@Test
	void contextLoads() {
		assertNotNull(cliente.getClienteid());
        assertEquals(4L, cliente.getClienteid());
		assertEquals("9875", cliente.getContrasena());
	}


	@Test
	void TestCrearCliente() throws Exception{
		        Cliente cliente = new Cliente();
				cliente.setNombre("Jose Lema");
				cliente.setEdad(30);
				cliente.setContrasena("9875");
				cliente.setEstado(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String clienteJson = objectMapper.writeValueAsString(cliente);

		mockMvc.perform(post("/clientes")
		.contentType(MediaType.APPLICATION_JSON)
		.content(clienteJson))
		.andExpect(status().isOk());

		// valida que el cliente esta en la base
		Optional<Cliente> clienteGuardado = clienteRepository.findById(1L);
		if (clienteGuardado.isPresent()) {
			assertThat(clienteGuardado.get().getGenero()).isNotNull();
			assertThat(clienteGuardado.get().getNombre()).isEqualTo("Jose Lema");	
		}
	}
	

}
