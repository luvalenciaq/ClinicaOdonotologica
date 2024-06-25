package Backend.ClinicaOdontologica.controller;

import Backend.ClinicaOdontologica.entity.Domicilio;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.exeption.ResourceNotFoundException;
import Backend.ClinicaOdontologica.service.OdontologoService;
import Backend.ClinicaOdontologica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private static final Logger logger = Logger.getLogger(PacienteController.class);
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
        try {
            Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
            logger.info("Paciente guardado con éxito: " + pacienteGuardado);
            return ResponseEntity.ok(pacienteGuardado);
        } catch (Exception e) {
            logger.error("Error al guardar el paciente: " + e.getMessage(), e);
            throw new RuntimeException("Error al guardar el paciente: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Paciente>> buscarPorId(@PathVariable Long id){
        logger.info("Buscando paciente por ID: " + id);
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(id);
        if (pacienteBuscado.isEmpty()) {
            logger.warn("Paciente no encontrado con ID: " + id);
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(pacienteBuscado);
    }

    @GetMapping("/buscar/{email}")
    public ResponseEntity<Optional<Paciente>> buscarPorEmail(@PathVariable String email){
        logger.info("Buscando paciente por email: " + email);
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorEmail(email);
        if(pacienteBuscado.isEmpty()){
            logger.warn("Paciente no encontrado con email: " + email);
            throw new ResourceNotFoundException("Paciente no encontrado con email: " + email);
        }
        return ResponseEntity.ok(pacienteBuscado);
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente){
        logger.info("Actualizando paciente con ID: " + paciente.getId());
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(paciente.getId());

        if (pacienteBuscado.isEmpty()) {
            logger.warn("Paciente no encontrado para actualizar con ID: " + paciente.getId());
            throw new ResourceNotFoundException("Paciente no encontrado para actualizar con ID: " + paciente.getId());
        }

        // Verificamos y actualizamos el domicilio
        Domicilio domicilio = pacienteBuscado.get().getDomicilio();
        if (domicilio != null) {
            paciente.getDomicilio().setId(domicilio.getId());
        }

        pacienteService.guardarPaciente(paciente);
        logger.info("Paciente actualizado con éxito: " + paciente);
        return ResponseEntity.ok("Paciente actualizado con éxito.");
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){
        logger.info("Listando todos los pacientes");
        List<Paciente> pacientes = pacienteService.listarTodos();
        if (pacientes.isEmpty()) {
            logger.warn("No se encontraron pacientes.");
            throw new ResourceNotFoundException("No se encontraron pacientes.");
        }
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id){
        logger.info("Eliminando paciente con ID: " + id);
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorId(id);
        if (pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            logger.info("Paciente eliminado con éxito: ID " + id);
            return ResponseEntity.ok("paciente eliminado con éxito");
        }else{
            logger.warn("Paciente no encontrado para eliminar con ID: " + id);
            throw new ResourceNotFoundException("Paciente no encontrado para eliminar con ID: " + id);
        }
    }
}

