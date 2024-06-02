package Backend.ClinicaOdontologica.service;

import Backend.ClinicaOdontologica.dao.IDao;
import Backend.ClinicaOdontologica.dao.PacienteDAOH2;
import Backend.ClinicaOdontologica.model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class PacienteService {
    private IDao<Paciente> pacienteiDao;

    public PacienteService() {
        pacienteiDao= new PacienteDAOH2();
    }
    //metodos manuales
    public Paciente guardarPaciente(Paciente paciente){
        return pacienteiDao.guardar(paciente);
    }
    public Paciente buscarPorID(int id){
        return pacienteiDao.buscarPorId(id);
    }
    public List<Paciente> listarTodos() { return pacienteiDao.buscarTodos();}
    public Paciente buscarPorEmail(String email){return pacienteiDao.buscarPorString(email);}
    public void eliminarPaciente(int id){pacienteiDao.eliminar(id);}
    public Paciente actualizarPaciente(Paciente paciente){ pacienteiDao.actualizar(paciente);
        return pacienteiDao.buscarPorId(paciente.getId());}
}
