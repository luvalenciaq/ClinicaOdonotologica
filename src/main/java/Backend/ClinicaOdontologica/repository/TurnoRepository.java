package Backend.ClinicaOdontologica.repository;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
}
