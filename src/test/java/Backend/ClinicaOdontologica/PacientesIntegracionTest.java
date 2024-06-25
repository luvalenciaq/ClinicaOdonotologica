package Backend.ClinicaOdontologica;

import Backend.ClinicaOdontologica.entity.Domicilio;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.service.PacienteService;
import jakarta.websocket.OnError;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PacientesIntegracionTest {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @Order(1)
    void guardarPacienteTest() throws Exception {
        Paciente paciente = new Paciente("Laura", "García", "1234",
                LocalDate.of(2024, 6, 20),
                new Domicilio("Calle Falsa", 123, "La Rioja", "Argentina"),
                "laura@dh.com");

        String pacienteJson = objectMapper.writeValueAsString(paciente);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contenidoRespuesta = resultado.getResponse().getContentAsString();
        assertFalse(contenidoRespuesta.isEmpty());
    }

    @Test
    @Order(2)
    void buscarPacientePorIdTest() throws Exception {
        Long id = 2L;

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(3)
    void actualizarPacienteTest() throws Exception {
        Paciente paciente = new Paciente("Jorgito", "Pereyra", "11111",
                LocalDate.of(2024, 6, 20),
                new Domicilio("Calle Falsa", 123, "La Rioja", "Argentina"),
                "jorge.pereyra@dh.com");


        Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
        pacienteGuardado.setNombre("Andrés");

        String pacienteJson = objectMapper.writeValueAsString(pacienteGuardado);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.put("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pacienteJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contenidoRespuesta = resultado.getResponse().getContentAsString();
        assertTrue(contenidoRespuesta.contains("actualizado con éxito"));
    }

    @Test
    @Order(4)
    void eliminarPacienteTest() throws Exception {
        Long id = 1L;

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contenidoRespuesta = resultado.getResponse().getContentAsString();
        assertTrue(contenidoRespuesta.contains("eliminado con éxito"));
    }

    @Test
    @Order(5)
    void listarTodosLosPacientesTest() throws Exception {
        Paciente paciente= pacienteService.guardarPaciente(new Paciente("Jorgito","pereyra","11111", LocalDate.of(2024,6,20),new Domicilio("calle falsa",123,"La Rioja","Argentina"),"jorge.pereyra@digitalhouse.com"));

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }
}

