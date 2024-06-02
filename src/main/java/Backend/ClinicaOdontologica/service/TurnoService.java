package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.dao.IDao;
import Backend.ClinicaOdontologica.dao.TurnoDAOLista;
import Backend.ClinicaOdontologica.model.Turno;

import java.util.List;

public class TurnoService {
    private IDao<Turno> turnoiDao;

    public TurnoService() {
        turnoiDao= new TurnoDAOLista();
    }
    public Turno guardarTurno(Turno turno){
        return turnoiDao.guardar(turno);
    }
    public List<Turno> buscarTodos(){
        return turnoiDao.buscarTodos();
    }
    public Turno buscarPorId(Integer id) {return turnoiDao.buscarPorId(id);}
    public void eliminarTurno(int id) {turnoiDao.eliminar(id);}
    public Turno actualizarTurno(Turno turno) {
        turnoiDao.actualizar(turno);
        return turnoiDao.buscarPorId(turno.getId());
    }
}