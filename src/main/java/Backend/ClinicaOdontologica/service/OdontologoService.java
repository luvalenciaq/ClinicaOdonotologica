package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.entity.Odontologo;
import Backend.ClinicaOdontologica.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private static final Logger logger = Logger.getLogger(OdontologoService.class);
    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo){
        logger.info("Guardando odontólogo: " + odontologo);
        return odontologoRepository.save(odontologo);
    }
    public Optional<Odontologo> buscarPorId(Long id){
        return odontologoRepository.findById(id);}
    public Optional<Odontologo> buscarPorMatricula(String matricula){
        logger.info("Buscando paciente por matrícula: " + matricula);
        return odontologoRepository.findByMatricula(matricula);}
    public void eliminarOdontologo(Long id){
        odontologoRepository.deleteById(id);
    }
    public void actualizarOdontologo(Odontologo odontologo){
        odontologoRepository.save(odontologo);
    }
    public List<Odontologo> listarTodos(){
        return odontologoRepository.findAll();
    }
}
