package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.exeption.ResourceNotFoundException;
import Backend.ClinicaOdontologica.repository.OdontologoRepository;
import Backend.ClinicaOdontologica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo){
        try {
            return odontologoRepository.save(odontologo);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el odontólogo: " + e.getMessage());
        }
    }

    public Optional<Odontologo> buscarPorId(Long id) {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(id);
        if (odontologoBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Odontólogo no encontrado con ID: " + id);
        }
        return odontologoBuscado;
    }

    public Optional<Odontologo> buscarPorMatricula(String matricula){
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findByMatricula(matricula);
        if (odontologoBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Odontólogo no encontrado con matrícula: " + matricula);
        }
        return odontologoBuscado;
    }
    public void actualizarOdontologo(Odontologo odontologo){
        Optional<Odontologo> odontologoBuscado = buscarPorId(odontologo.getId());
        if (odontologoBuscado.isEmpty()) {
            throw new ResourceNotFoundException("Odontólogo no encontrado para actualizar con ID: " + odontologo.getId());
        }
        odontologoRepository.save(odontologo);
    }
    public void eliminarOdontologo(Long id){
        if (!odontologoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Odontólogo no encontrado para eliminar con ID: " + id);
        }
        odontologoRepository.deleteById(id);
    }
    public List<Odontologo> listarTodos(){
        try {
            return odontologoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los odontólogos: " + e.getMessage());
        }
    }
}