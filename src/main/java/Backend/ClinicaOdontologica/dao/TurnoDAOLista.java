package Backend.ClinicaOdontologica.dao;

import Backend.ClinicaOdontologica.model.Turno;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TurnoDAOLista implements IDao<Turno>{
    private Logger logger= Logger.getLogger(TurnoDAOLista.class);
    private List<Turno> turnos= new ArrayList<>();
    @Override
    public Turno guardar(Turno turno) {
        logger.info("iniciando las operaciones de guardado de un turno");
        PacienteDAOH2 pacienteDAOH2= new PacienteDAOH2();
        OdontologoDAOH2 odontologoDAOH2= new OdontologoDAOH2();
        turno.setPaciente(pacienteDAOH2.buscarPorId(turno.getPaciente().getId()));
        turno.setOdontologo(odontologoDAOH2.buscarPorId(turno.getOdontologo().getId()));
        turnos.add(turno);
        logger.info("turno guardado con éxito");
        return turno;
    }

    @Override
    public Turno buscarPorId(int id) {
        for (Turno turno : turnos) {
            if(turno.getId().equals(id)){
                return turno;
            }
        }
        logger.info("Turno no encontrado con id: " + id);
        return null;
    }

    @Override
    public void eliminar(int id) {
        Turno turnoAEliminar = buscarPorId(id);
        if (turnoAEliminar != null) {
            turnos.remove(turnoAEliminar);
            logger.info("Turno eliminado con éxito con id: " + id);
        } else {
            logger.info("Turno no encontrado con id: " + id);
        }
    }

    @Override
    public void actualizar(Turno turno) {
        Turno turnoAActualizar = buscarPorId(turno.getId());
        if (turnoAActualizar != null) {
            turnoAActualizar.setPaciente(turno.getPaciente());
            turnoAActualizar.setOdontologo(turno.getOdontologo());
            turnoAActualizar.setFecha(turno.getFecha());
            logger.info("Turno actualizado con éxito con id: " + turno.getId());
        } else {
            logger.info("Turno no encontrado con id: " + turno.getId());
        }
    }

    @Override
    public List<Turno> buscarTodos() {
        logger.info("iniciando las operacion de mostrar todos los turnos");
        return turnos;
    }

    @Override
    public Turno buscarPorString(String string) {
        return null;
    }
}