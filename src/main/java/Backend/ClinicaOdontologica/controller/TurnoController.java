package Backend.ClinicaOdontologica.controller;

import Backend.ClinicaOdontologica.entity.Turno;
import Backend.ClinicaOdontologica.exeption.ResourceNotFoundException;
import Backend.ClinicaOdontologica.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private static final Logger logger = Logger.getLogger(TurnoController.class);
    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno) {
        try {
            Turno nuevoTurno = turnoService.guardarTurno(turno);
            logger.info("Turno guardado con éxito: " + nuevoTurno);
            return ResponseEntity.ok(nuevoTurno);
        } catch (Exception e) {
            logger.error("Error al guardar el turno: " + e.getMessage(), e);
            throw new RuntimeException("Error al guardar el turno: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Turno>> buscarPorId(@PathVariable Long id){
        logger.info("Buscando turno por ID: " + id);
        Optional<Turno> turnoBuscado = turnoService.buscarPorId(id);
        if (turnoBuscado.isEmpty()) {
            logger.warn("Turno no encontrado con ID: " + id);
            throw new ResourceNotFoundException("Turno no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(turnoBuscado);
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno){
        try {
            turnoService.actualizarTurno(turno);
            logger.info("Turno actualizado con éxito: " + turno);
            return ResponseEntity.ok("Turno actualizado con éxito");
        } catch (Exception e) {
            logger.error("Error al actualizar el turno: " + e.getMessage(), e);
            throw new RuntimeException("Error al actualizar el turno: " + e.getMessage(), e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos(){
        logger.info("Listando todos los turnos");
        List<Turno> turnos = turnoService.listarTodos();
        if (turnos.isEmpty()) {
            logger.warn("No se encontraron turnos.");
            throw new ResourceNotFoundException("No se encontraron turnos.");
        }
        return ResponseEntity.ok(turnos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id){
        logger.info("Eliminando turno con ID: " + id);
        Optional<Turno> turnoBuscado= turnoService.buscarPorId(id);
        if (turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            logger.info("Turno eliminado con éxito: ID " + id);
            return ResponseEntity.ok("Turno eliminado con éxito");
        }else{
            logger.warn("Turno no encontrado para eliminar con ID: " + id);
            throw new ResourceNotFoundException("Turno no encontrado para eliminar con ID: " + id);
        }
    }
}



