package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.entity.Turno;
import Backend.ClinicaOdontologica.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private static final Logger logger = Logger.getLogger(TurnoService.class);
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    public Turno guardarTurno(Turno turno) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(turno.getOdontologo().getId());

        if (pacienteBuscado.isEmpty() || odontologoBuscado.isEmpty()) {
            logger.error("No se pudo guardar el turno porque no se encontró el paciente o el odontólogo.");
            return null;
        }

        turno.setPaciente(pacienteBuscado.get());
        turno.setOdontologo(odontologoBuscado.get());

        logger.info("Guardando turno: " + turno);
        return turnoRepository.save(turno);
    }

    public Optional<Turno> buscarPorId(Long id) {
        return turnoRepository.findById(id);}

    public void actualizarTurno(Turno turno) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(turno.getOdontologo().getId());

        if (turnoRepository.existsById(turno.getId())) {

            turno.setPaciente(pacienteBuscado.get());
            turno.setOdontologo(odontologoBuscado.get());

            logger.info("Actualizando turno: " + turno);
            turnoRepository.save(turno);
        }
    }

    public List<Turno> listarTodos() {
        return turnoRepository.findAll();}

    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);}

}