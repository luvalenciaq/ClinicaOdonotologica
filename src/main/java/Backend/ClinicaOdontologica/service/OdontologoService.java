package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.dao.IDao;
import Backend.ClinicaOdontologica.dao.OdontologoDAOH2;
import Backend.ClinicaOdontologica.dao.PacienteDAOH2;
import Backend.ClinicaOdontologica.model.Odontologo;
import Backend.ClinicaOdontologica.model.Paciente;
import Backend.ClinicaOdontologica.model.Turno;

import java.util.List;

public class OdontologoService {
    private IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public OdontologoService() { odontologoIDao= new OdontologoDAOH2(); }

    public void setOdontologoIDao(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){ return odontologoIDao.guardar(odontologo); }


    public Odontologo buscarPorId(int id){return odontologoIDao.buscarPorId(id);}

    public void eliminarOdontologo(int id){odontologoIDao.eliminar(id);}

    public List<Odontologo> listarTodos(){
        return odontologoIDao.buscarTodos();
    }

    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        odontologoIDao.actualizar(odontologo);
        return odontologoIDao.buscarPorId(odontologo.getId());
    }
}
