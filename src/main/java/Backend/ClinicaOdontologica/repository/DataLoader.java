package Backend.ClinicaOdontologica.repository;

import Backend.ClinicaOdontologica.entity.Domicilio;
import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.entity.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private TurnoRepository turnoRepository;
    @Override

    public void run(ApplicationArguments args) throws Exception {
        // Cargar datos a la bd al iniciar
        Odontologo odontologo1 = new Odontologo("OD-123", "Crentist", "The Dentist");
        Odontologo odontologo2 = new Odontologo("OD-124", "Wendell", "Bender");

        odontologoRepository.save(odontologo1);
        odontologoRepository.save(odontologo2);

        Domicilio domicilio1 = new Domicilio("Calle uno", 567, "Localidad uno", "Provincia uno");
        Paciente paciente1 = new Paciente("Luisa", "Valencia", "PX-001", LocalDate.now(), domicilio1,"lu@dh.com");
        Domicilio domicilio2 = new Domicilio("Calle dos", 568, "Localidad dos", "Provincia dos");
        Paciente paciente2 = new Paciente("Juan", "Palaceto", "PX-002", LocalDate.now(), domicilio2,"jp@dh.com");

        pacienteRepository.save(paciente1);
        pacienteRepository.save(paciente2);

        Turno turno1 = new Turno(paciente1, odontologo1, LocalDate.now());
        Turno turno2 = new Turno(paciente2, odontologo2, LocalDate.now());

        turnoRepository.save(turno1);
        turnoRepository.save(turno2);
    }
}
