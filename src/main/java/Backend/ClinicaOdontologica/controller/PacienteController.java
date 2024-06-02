package Backend.ClinicaOdontologica.controller;

import Backend.ClinicaOdontologica.model.Paciente;
import Backend.ClinicaOdontologica.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //para trabajar sin tecnologia de vista
// @Controller<-- es controller pq vamos a usar una tecnologia de vista

@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController() {
        pacienteService= new PacienteService();
    }

    @PostMapping
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
        return ResponseEntity.ok(pacienteGuardado);
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteExistente = pacienteService.buscarPorID(paciente.getId());
        if (pacienteExistente != null) {
            Paciente pacienteActualizado = pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok(pacienteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id) {
        Paciente paciente = pacienteService.buscarPorID(id);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        Paciente paciente = pacienteService.buscarPorID(id);
        if (paciente != null) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

