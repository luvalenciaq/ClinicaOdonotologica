package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private static final Logger logger = Logger.getLogger(PacienteService.class);
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente guardarPaciente(Paciente paciente){
        logger.info("Guardando paciente: " + paciente);
        return pacienteRepository.save(paciente);}

    public Optional<Paciente> buscarPorId(Long id){
        return pacienteRepository.findById(id);}

    public Optional<Paciente> buscarPorEmail(String email){
        logger.info("Buscando paciente por email: " + email);
        return pacienteRepository.findByEmail(email);}

    public void actualizarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);}

    public List<Paciente> listarTodos(){
        return pacienteRepository.findAll();}

    public void eliminarPaciente(Long id){
        pacienteRepository.deleteById(id);}

}