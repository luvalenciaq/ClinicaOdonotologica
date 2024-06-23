package Backend.ClinicaOdontologica.controller;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.entity.Paciente;
import Backend.ClinicaOdontologica.entity.Turno;
import Backend.ClinicaOdontologica.service.OdontologoService;
import Backend.ClinicaOdontologica.service.PacienteService;
import Backend.ClinicaOdontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){
       return ResponseEntity.ok(turnoService.guardarTurno(turno));
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno){
        turnoService.actualizarTurno(turno);
        return ResponseEntity.ok("Turno actualizado con éxito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Turno>> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(turnoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos(){
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id){
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Turno eliminado con éxito");
    }
}
