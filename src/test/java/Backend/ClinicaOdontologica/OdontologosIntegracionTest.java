package Backend.ClinicaOdontologica;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.service.OdontologoService;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OdontologosIntegracionTest {

    @Autowired
    private OdontologoService odontologoService;

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
    void guardarOdontologoTest() throws Exception {
        Odontologo odontologo = new Odontologo("MP10", "Gina", "Arias");

        String odontologoJson = objectMapper.writeValueAsString(odontologo);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contenidoRespuesta = resultado.getResponse().getContentAsString();
        assertFalse(contenidoRespuesta.isEmpty());
    }

    @Test
    @Order(2)
    void buscarOdontologoPorIdTest() throws Exception {
        Long id = 1L;

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(3)
    void actualizarOdontologoTest() throws Exception {
        Odontologo odontologo = new Odontologo("MP10", "Gina", "Arias");

        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        odontologoGuardado.setNombre("Laura");

        String odontologoJson = objectMapper.writeValueAsString(odontologoGuardado);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.put("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contenidoRespuesta = resultado.getResponse().getContentAsString();
        assertTrue(contenidoRespuesta.contains("actualizado con éxito"));
    }

    @Test
    @Order(4)
    void eliminarOdontologoTest() throws Exception {
        Long id = 1L;

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/odontologos/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String contenidoRespuesta = resultado.getResponse().getContentAsString();
        assertTrue(contenidoRespuesta.contains("eliminado con éxito"));
    }

    @Test
    @Order(5)
    void listarTodosLosOdontologosTest() throws Exception {
        Odontologo odontologo = new Odontologo("MP10", "Laura", "Arias");
        odontologoService.guardarOdontologo(odontologo);

        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(resultado.getResponse().getContentAsString().isEmpty());
    }
}