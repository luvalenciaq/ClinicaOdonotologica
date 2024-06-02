package Backend.ClinicaOdontologica;

import Backend.ClinicaOdontologica.dao.BD;
import Backend.ClinicaOdontologica.model.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Backend.ClinicaOdontologica.service.PacienteService;

public class PacienteServiceTest {
    @Test
    public void buscarPacientePorID(){
        BD.crearTablas();

        PacienteService pacienteService= new PacienteService();

        int id=2;
        Paciente paciente= pacienteService.buscarPorID(id);

        Assertions.assertTrue(paciente!=null);
    }
}
