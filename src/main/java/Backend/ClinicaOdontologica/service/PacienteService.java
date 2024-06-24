package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.entity.Domicilio;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.exeption.ResourceNotFoundException;
import Backend.ClinicaOdontologica.repository.PacienteRepository;
import Backend.ClinicaOdontologica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente guardarPaciente(Paciente paciente){
        try {
            return pacienteRepository.save(paciente);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el paciente: " + e.getMessage());
        }
    }
    public Optional<Paciente> buscarPorId(Long id){
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if (pacienteBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        return pacienteBuscado;
    }
    public Optional<Paciente> buscarPorEmail(String email){
        Optional<Paciente> pacienteBuscado = pacienteRepository.findByEmail(email);
        if (pacienteBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado con email: " + email);
        }
        return pacienteBuscado;
    }
    public void actualizarPaciente(Paciente paciente){
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(paciente.getId());

        if (pacienteBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Paciente no encontrado para actualizar con ID: " + paciente.getId());
        }

        // Verificamos y actualizamos el domicilio
        Domicilio domicilio = pacienteBuscado.get().getDomicilio();
        if (domicilio != null) {
                paciente.getDomicilio().setId(domicilio.getId());
        }

        pacienteRepository.save(paciente);
    }
    public void eliminarPaciente(Long id){
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente no encontrado para eliminar con ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }
    public List<Paciente> listarTodos(){
        try {
            return pacienteRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los pacientes: " + e.getMessage());
        }
    }
}
