package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.entity.Turno;
import Backend.ClinicaOdontologica.exeption.ResourceNotFoundException;
import Backend.ClinicaOdontologica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    public Turno guardarTurno(Turno turno){
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(turno.getOdontologo().getId());

        if (pacienteBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + turno.getPaciente().getId());
        }

        if (odontologoBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Odontólogo no encontrado con ID: " + turno.getOdontologo().getId());
        }

        turno.setPaciente(pacienteBuscado.get());
        turno.setOdontologo(odontologoBuscado.get());
        try {
            return turnoRepository.save(turno);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el turno: " + e.getMessage());
        }
    }
    public Optional<Turno> buscarPorId(Long id){
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if (turnoBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Turno no encontrado con ID: " + id);
        }
        return turnoBuscado;
    }
    public void actualizarTurno(Turno turno){
        if (!turnoRepository.existsById(turno.getId())) {
            throw new ResourceNotFoundException("Turno no encontrado para actualizar con ID: " + turno.getId());
        }
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(turno.getOdontologo().getId());
        if (pacienteBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + turno.getPaciente().getId());
        }
        if (odontologoBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Odontólogo no encontrado con ID: " + turno.getOdontologo().getId());
        }
        turno.setPaciente(pacienteBuscado.get());
        turno.setOdontologo(odontologoBuscado.get());
        turnoRepository.save(turno);
    }
    public void eliminarTurno(Long id){
        if (!turnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turno no encontrado para eliminar con ID: " + id);
        }
        turnoRepository.deleteById(id);
    }

    public List<Turno> listarTodos(){
        try {
            return turnoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los turnos: " + e.getMessage());
        }
    }
}