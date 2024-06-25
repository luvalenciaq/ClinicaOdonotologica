package Backend.ClinicaOdontologica.serviceTest;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.service.OdontologoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologo() {
        Odontologo odontologo = new Odontologo("12345", "Juan", "Perez");
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        assertNotNull(odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorId() {
        Long id = 1L;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        assertTrue(odontologoBuscado.isPresent());
    }

    @Test
    @Order(3)
    public void actualizarOdontologo() {
        Long id = 1L;
        Odontologo odontologo = new Odontologo(id, "54321", "Pedro", "Gomez");
        odontologoService.actualizarOdontologo(odontologo);
        Optional<Odontologo> odontologoActualizado = odontologoService.buscarPorId(id);
        assertEquals("Pedro", odontologoActualizado.get().getNombre());
    }

    @Test
    @Order(4)
    public void eliminarOdontologo() {
        Long id = 1L;
        odontologoService.eliminarOdontologo(id);
        Optional<Odontologo> odontologoEliminado = odontologoService.buscarPorId(id);
        assertFalse(odontologoEliminado.isPresent());
    }

    @Test
    @Order(5)
    public void listarTodos() {
        List<Odontologo> listaOdontologos = odontologoService.listarTodos();
        assertNotEquals(0, listaOdontologos.size(), "La lista de odontólogos no debe estar vacía");
    }
}