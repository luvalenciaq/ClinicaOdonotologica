package Backend.ClinicaOdontologica.controller;

import Backend.ClinicaOdontologica.model.Odontologo;
import Backend.ClinicaOdontologica.model.Paciente;
import Backend.ClinicaOdontologica.service.OdontologoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private OdontologoService odontologoService;

    public OdontologoController() {
        odontologoService= new OdontologoService();
    }
    @PostMapping
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo) {
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        return ResponseEntity.ok(odontologoGuardado);
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodos() {
        List<Odontologo> odontologos = odontologoService.listarTodos();
        return ResponseEntity.ok(odontologos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOdontologo(@PathVariable int id) {
        Odontologo odontologo = odontologoService.buscarPorId(id);
        if (odontologo != null) {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) {
        Odontologo odontologoExistente = odontologoService.buscarPorId(odontologo.getId());
        if (odontologoExistente != null) {
            Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok(odontologoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable int id) {
        Odontologo odontologo = odontologoService.buscarPorId(id);
        if (odontologo != null) {
            return ResponseEntity.ok(odontologo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}