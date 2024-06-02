package Backend.ClinicaOdontologica.controller;

import Backend.ClinicaOdontologica.model.Odontologo;
import Backend.ClinicaOdontologica.model.Paciente;
import Backend.ClinicaOdontologica.model.Turno;
import Backend.ClinicaOdontologica.service.OdontologoService;
import Backend.ClinicaOdontologica.service.PacienteService;
import Backend.ClinicaOdontologica.service.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    public TurnoController() {
        turnoService= new TurnoService();
        pacienteService= new PacienteService();
        odontologoService= new OdontologoService();
    }

    @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){
        Paciente pacienteBuscado= pacienteService.buscarPorID(turno.getPaciente().getId());
        Odontologo odontologoBuscado= odontologoService.buscarPorId(turno.getOdontologo().getId());
        if(pacienteBuscado!=null&&odontologoBuscado!=null){
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable int id) {
        Turno turno = turnoService.buscarPorId(id);
        if (turno != null) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) {
        Turno turnoExistente = turnoService.buscarPorId(turno.getId());
        if (turnoExistente != null) {
            Paciente pacienteBuscado = pacienteService.buscarPorID(turno.getPaciente().getId());
            Odontologo odontologoBuscado = odontologoService.buscarPorId(turno.getOdontologo().getId());
            if (pacienteBuscado != null && odontologoBuscado != null) {
                return ResponseEntity.ok(turnoService.actualizarTurno(turno));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable int id) {
        Turno turno = turnoService.buscarPorId(id);
        if (turno != null) {
            return ResponseEntity.ok(turno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}