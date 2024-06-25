package Backend.ClinicaOdontologica.controller;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.exeption.ResourceNotFoundException;
import Backend.ClinicaOdontologica.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController{
    private static final Logger logger = Logger.getLogger(OdontologoController.class);
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo){
        try {
            Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
            logger.info("Odontólogo guardado con éxito: " + odontologoGuardado);
            return ResponseEntity.ok(odontologoGuardado);
        } catch (Exception e) {
            logger.error("Error al guardar el odontólogo: " + e.getMessage(), e);
            throw new RuntimeException("Error al guardar el odontólogo: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Odontologo>> buscarPorId(@PathVariable Long id){
        logger.info("Buscando odontólogo por ID: " + id);
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        if (odontologoBuscado.isEmpty()) {
            logger.warn("Odontólogo no encontrado con ID: " + id);
            throw new ResourceNotFoundException("Odontólogo no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(odontologoBuscado);
    }


    @GetMapping("/buscar/{matricula}")
    public ResponseEntity<Optional<Odontologo>> buscarPorMatricula(@PathVariable String matricula){
        logger.info("Buscando odontólogo por matrícula: " + matricula);
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorMatricula(matricula);
        if(odontologoBuscado.isEmpty()){
            logger.warn("Odontólogo no encontrado con matrícula: " + matricula);
            throw new ResourceNotFoundException("Odontólogo no encontrado con email: " + matricula);
        }
        return ResponseEntity.ok(odontologoBuscado);
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo){
        logger.info("Actualizando odontólogo con ID: " + odontologo.getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(odontologo.getId());
        if(odontologoBuscado.isEmpty()){
            logger.warn("Odontólogo no encontrado para actualizar con ID: " + odontologo.getId());
            throw new ResourceNotFoundException("Odontólogo no encontrado para actualizar con ID: " + odontologo.getId());
        }
        odontologoService.actualizarOdontologo(odontologo);
        logger.info("Odontólogo actualizado con éxito: " + odontologo);
        return ResponseEntity.ok("Odontólogo actualizado con éxito");
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        logger.info("Listando todos los odontólogos");
        List<Odontologo> odontologos = odontologoService.listarTodos();
        if (odontologos.isEmpty()) {
            logger.warn("No se encontraron odontólogos.");
            throw new ResourceNotFoundException("No se encontraron odontólogos.");
        }
        return ResponseEntity.ok(odontologos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) {
        logger.info("Eliminando odontólogo con ID: " + id);
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorId(id);
        if (odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            logger.info("Odontólogo eliminado con éxito: ID " + id);
            return ResponseEntity.ok("Odontólogo eliminado con éxito");
        }else{
            logger.warn("Odontólogo no encontrado para eliminar con ID: " + id);
            throw new ResourceNotFoundException("Odontólogo no encontrado para eliminar con ID: " + id);
        }
    }
}
