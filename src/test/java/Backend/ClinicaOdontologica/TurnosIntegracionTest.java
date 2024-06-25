package Backend.ClinicaOdontologica;

import Backend.ClinicaOdontologica.entity.Domicilio;
import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.entity.Turno;
import Backend.ClinicaOdontologica.service.OdontologoService;
import Backend.ClinicaOdontologica.service.PacienteService;
import Backend.ClinicaOdontologica.service.TurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class TurnosIntegracionTest {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private void cargarDatos(){
        Paciente paciente= pacienteService.guardarPaciente(new Paciente("Jorgito","pereyra","11111", LocalDate.of(2024,6,20),new Domicilio("calle falsa",123,"La Rioja","Argentina"),"jorge.pereyra@digitalhouse.com"));
        Odontologo odontologo= odontologoService.guardarOdontologo(new Odontologo("MP10","Gina","Arias"));
        Turno turno = turnoService.guardarTurno(new Turno(paciente,odontologo,LocalDate.of(2024,6,20)));

    }

    @Test
    @Order(1)
    public void ListarTodosLosTurnosTest() throws Exception{
        cargarDatos();
        MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(2)
    void guardarTurnoTest() throws Exception {
        Paciente paciente= pacienteService.guardarPaciente(new Paciente("Jorge","pereyra","1020", LocalDate.of(2024,6,20),new Domicilio("calle falsa",123,"La Rioja","Argentina"),"jorge@pereyra.com"));
        Odontologo odontologo= odontologoService.guardarOdontologo(new Odontologo("MP10","Gina","Arias"));
        Turno turno = turnoService.guardarTurno(new Turno(paciente,odontologo,LocalDate.of(2024,6,20)));

        String turnoJson = objectMapper.writeValueAsString(turno);

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contenidoRespuesta = respuesta.getResponse().getContentAsString();
        assertFalse(contenidoRespuesta.isEmpty());
    }

    @Test
    @Order(3)
    void buscarPorIdTest() throws Exception {
        Long id = 2L;

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());

    }

    @Test
    @Order(4)
    void eliminarTurnoTest() throws Exception {
        Long id = 1L;

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.delete("/turnos/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(5)
    void actualizarTurnoTest() throws Exception {
        Paciente paciente= pacienteService.guardarPaciente(new Paciente("Jorgito","pereyra","11111", LocalDate.of(2024,6,20),new Domicilio("calle falsa",123,"La Rioja","Argentina"),"jorge1.pereyra@digitalhouse.com"));
        Odontologo odontologo= odontologoService.guardarOdontologo(new Odontologo("MP10","Gina","Arias"));
        Turno turno = turnoService.guardarTurno(new Turno(paciente,odontologo,LocalDate.of(2024,6,20)));

        turno.setFecha(LocalDate.of(2024, 6, 20));

        String turnoJson = objectMapper.writeValueAsString(turno);

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.put("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contenido = respuesta.getResponse().getContentAsString();
        assertTrue(contenido.contains("actualizado con éxito"));
    }
}
