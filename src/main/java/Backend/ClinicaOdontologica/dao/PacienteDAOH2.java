package Backend.ClinicaOdontologica.dao;

import Backend.ClinicaOdontologica.model.Domicilio;
import Backend.ClinicaOdontologica.model.Odontologo;
import Backend.ClinicaOdontologica.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOH2 implements IDao<Paciente>{
    private static final Logger logger= Logger.getLogger(PacienteDAOH2.class);
    private static final String SQL_INSERT="INSERT INTO PACIENTES(NOMBRE, APELLIDO, CEDULA, FECHA_INGRESO, DOMICILIO_ID, EMAIL) VALUES(?,?,?,?,?,?)";
    private static final String SQL_SELECT_ONE="SELECT * FROM PACIENTES WHERE ID=?";
    private static final String SQL_DELETE = "DELETE FROM PACIENTES WHERE ID=?";
    private static final String SQL_UPDATE="UPDATE PACIENTES SET NOMBRE=?, APELLIDO=?, CEDULA=?, FECHA_INGRESO=?, DOMICILIO_ID=?, EMAIL=? WHERE ID=?";
    private static final String SQL_SELECT_ALL="SELECT * FROM PACIENTES";
    private static final String SQL_SELECT_BY_EMAIL="SELECT * FROM PACIENTES WHERE EMAIL=?";



    @Override
    public Paciente guardar(Paciente paciente) {
        logger.info("inciando las operaciones de guardado");
        Connection connection= null;
        DomicilioDAOH2 daoAux= new DomicilioDAOH2();
        Domicilio domicilio=  daoAux.guardar(paciente.getDomicilio());

        try{
            connection= BD.getConnection();
            PreparedStatement psInsert= connection.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, paciente.getNombre());
            psInsert.setString(2, paciente.getApellido());
            psInsert.setString(3, paciente.getCedula());
            psInsert.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            psInsert.setInt(5,domicilio.getId());
            psInsert.setString(6, paciente.getEmail());
            psInsert.execute();
            ResultSet clave= psInsert.getGeneratedKeys();
            while (clave.next()){
                paciente.setId(clave.getInt(1));
            }
            logger.info("paciente guardado");

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return paciente;
    }

    @Override
    public Paciente buscarPorId(int id) {
        logger.info("iniciando la operación de búsqueda de un paciente con id : "+id);
        Connection connection= null;
        Paciente paciente= null;
        Domicilio domicilio= null;

        try{
            connection= BD.getConnection();
            PreparedStatement psSElectOne= connection.prepareStatement(SQL_SELECT_ONE);
            psSElectOne.setInt(1,id);
            ResultSet rs= psSElectOne.executeQuery();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();
            while(rs.next()){
                domicilio= daoAux.buscarPorId(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5).toLocalDate(), domicilio, rs.getString(7));
            }
            logger.info("Se buscó el paciente con id: "+id);

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return paciente;
    }

    @Override
    public void eliminar(int id) {
        logger.info("iniciando las operaciones de eliminación de un paciente con id: "+id);
        Connection connection= null;

        try{
            connection= BD.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            psDelete.setInt(1, id);
            psDelete.execute();
            logger.info("paciente eliminado");

        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void actualizar(Paciente paciente) {
        logger.warn("iniciando las operaciones de actualización de un paciente con id : "+paciente.getId());
        Connection connection= null;
        DomicilioDAOH2 daoAux= new DomicilioDAOH2();

        try{
            connection= BD.getConnection();
            daoAux.actualizar(paciente.getDomicilio());
            PreparedStatement psUpdate= connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1, paciente.getNombre());
            psUpdate.setString(2, paciente.getApellido());
            psUpdate.setString(3, paciente.getCedula());
            psUpdate.setDate(4,Date.valueOf(paciente.getFechaIngreso()));
            psUpdate.setInt(5,paciente.getDomicilio().getId());
            psUpdate.setString(6, paciente.getEmail());
            psUpdate.setInt(7,paciente.getId());
            psUpdate.execute();
            connection.commit();
            logger.info("paciente actualizado");
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Paciente> buscarTodos() {
        logger.info("iniciando las operaciones de bÚsqueda de todos los pacientes");
        Connection connection=null;
        List<Paciente> pacientes= new ArrayList<>();
        Paciente paciente= null;
        Domicilio domicilio=null;
        DomicilioDAOH2 daoAux= new DomicilioDAOH2();

        try{
            connection=BD.getConnection();
            Statement statement= connection.createStatement();
            ResultSet rs= statement.executeQuery(SQL_SELECT_ALL);
            while(rs.next()){
                domicilio= daoAux.buscarPorId(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5).toLocalDate(),domicilio,rs.getString(7));
                pacientes.add(paciente);
            }
            logger.info("Se consultaron todos los pacientes");

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return pacientes;
    }

    @Override
    public Paciente buscarPorString(String string) {
        logger.info("iniciando la busqueda por email: "+string);
        Connection connection=null;
        Paciente paciente= null;
        Domicilio domicilio= null;
        DomicilioDAOH2 daoAux= new DomicilioDAOH2();

        try{
            connection=BD.getConnection();
            PreparedStatement psSelectE= connection.prepareStatement(SQL_SELECT_BY_EMAIL);
            psSelectE.setString(1,string);
            ResultSet rs= psSelectE.executeQuery();
            while(rs.next()){
                domicilio= daoAux.buscarPorId(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5).toLocalDate(),domicilio,rs.getString(7));
            }
            logger.info("Se buscó el paciente con email: "+string);

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return paciente;
    }
}
