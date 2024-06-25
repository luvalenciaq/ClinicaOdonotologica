package Backend.ClinicaOdontologica.serviceTest;

import Backend.ClinicaOdontologica.entity.Domicilio;
import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.entity.Turno;
import Backend.ClinicaOdontologica.service.OdontologoService;
import Backend.ClinicaOdontologica.service.PacienteService;
import Backend.ClinicaOdontologica.service.TurnoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarTurno() {
        Paciente paciente = new Paciente("Carlos", "Lopez", "22222", LocalDate.of(2024,6,20),
                new Domicilio("calle", 456, "Buenos Aires", "Argentina"), "carlos@dh.com");
        paciente = pacienteService.guardarPaciente(paciente);

        Odontologo odontologo = new Odontologo("54321", "Ana", "Martinez");
        odontologo = odontologoService.guardarOdontologo(odontologo);

        Turno turno = new Turno(paciente, odontologo, LocalDate.of(2024, 7, 15));
        Turno turnoGuardado = turnoService.guardarTurno(turno);
        assertNotNull(turnoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarTurnoPorId() {
        Long id = 1L;
        Optional<Turno> turnoBuscado = turnoService.buscarPorId(id);
        assertTrue(turnoBuscado.isPresent());
    }

    @Test
    @Order(3)
    public void actualizarTurno() {
        Long id = 1L;

        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(1L);
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(1L);
        assertTrue(pacienteBuscado.isPresent());
        assertTrue(odontologoBuscado.isPresent());

        Turno turno = new Turno(id, pacienteBuscado.get(), odontologoBuscado.get(), LocalDate.of(2024, 8, 20));
        turnoService.actualizarTurno(turno);
        Optional<Turno> turnoActualizado = turnoService.buscarPorId(id);
        assertEquals(odontologoBuscado.get().getId(), turnoActualizado.get().getOdontologo().getId());
    }

    @Test
    @Order(4)
    public void eliminarTurno() {
        Long id = 1L;
        turnoService.eliminarTurno(id);
        Optional<Turno> turnoEliminado = turnoService.buscarPorId(id);
        assertFalse(turnoEliminado.isPresent());
    }

    @Test
    @Order(5)
    public void listarTodos() {
        List<Turno> listaTurnos = turnoService.listarTodos();
        assertNotEquals(0, listaTurnos.size(), "La lista de turnos no debe estar vacía");
    }
}