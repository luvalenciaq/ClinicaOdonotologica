package Backend.ClinicaOdontologica.dao;

import Backend.ClinicaOdontologica.model.Domicilio;
import Backend.ClinicaOdontologica.model.Odontologo;
import Backend.ClinicaOdontologica.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements IDao<Odontologo> {
    private static final Logger logger = Logger.getLogger(OdontologoDAOH2.class);
    private static final String SQL_INSERT_ODO = "INSERT INTO ODONTOLOGOS (MATRICULA, NOMBRE, APELLIDO) VALUES (?,?,?)";
    private static final String SQL_SELECT_ONE = "SELECT * FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_DELETE = "DELETE FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_UPDATE = "UPDATE ODONTOLOGOS SET MATRICULA=?, NOMBRE=?, APELLIDO=? WHERE ID=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    private static final String SQL_SELECT_BY_MATRICULA = "SELECT * FROM ODONTOLOGOS WHERE MATRICULA=?";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        logger.info("iniciando las operaciones de: guardado de :  " + odontologo.getMatricula());
        Connection connection = null;

        try {
            connection = BD.getConnection();

            // 2 Crear la sentencia
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT_ODO, PreparedStatement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, odontologo.getMatricula());
            psInsert.setString(2, odontologo.getNombre());
            psInsert.setString(3, odontologo.getApellido());

            // Ejecutar la sentencia
            psInsert.execute();
            ResultSet clave = psInsert.getGeneratedKeys();
            while (clave.next()) {
                odontologo.setId(clave.getInt(1));
            }
            logger.info("odontologo guardado");

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return odontologo;
    }

    @Override
    public Odontologo buscarPorId(int id) {
        logger.info("iniciando la busqueda de un odontólogo con id: " + id);
        Connection connection = null;
        Odontologo odontologo = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psSelectOne = connection.prepareStatement(SQL_SELECT_ONE);
            psSelectOne.setInt(1, id);
            ResultSet rs = psSelectOne.executeQuery();
            while (rs.next()) {
                odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            logger.info("Se buscó el odontólogo con id: "+id);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return odontologo;
    }

    @Override
    public void eliminar(int id) {
        logger.info("iniciando las operaciones de eliminación de un odontólogo con id: " + id);
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            psDelete.setInt(1, id);
            psDelete.execute();
            logger.info("odontólogo eliminado");

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        logger.warn("iniciando las operaciones de actualización de un odontólogo con id : " + odontologo.getId());
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1, odontologo.getMatricula());
            psUpdate.setString(2, odontologo.getNombre());
            psUpdate.setString(3, odontologo.getApellido());
            psUpdate.setInt(4, odontologo.getId());
            psUpdate.execute();
            connection.commit();
            logger.info("odontólogo actualizado");

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        logger.info("iniciando las operaciones de búsqueda de todos los odontólogos");
        Connection connection=null;
        List<Odontologo> odontologos= new ArrayList<>();
        Odontologo odontologo=null;

        try{
            connection=BD.getConnection();
            Statement statement= connection.createStatement();
            ResultSet set= statement.executeQuery(SQL_SELECT_ALL);
            while(set.next()){
                int id = set.getInt(1);
                String matricula = set.getString(2);
                String nombre = set.getString(3);
                String apellido = set.getString(4);
                odontologo= new Odontologo(id, matricula, nombre, apellido);
                odontologos.add(odontologo);
            }
            logger.info("Se consultaron todos los odontólogos");

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return odontologos;
    }


    @Override
    public Odontologo buscarPorString(String string) {
        logger.info("iniciando la busqueda por matrícula: " + string);
        Connection connection = null;
        Odontologo odontologo = null;

        try {
            connection = BD.getConnection();
            PreparedStatement psSelectM = connection.prepareStatement(SQL_SELECT_BY_MATRICULA);
            psSelectM.setString(1, string);
            ResultSet rs = psSelectM.executeQuery();
            while (rs.next()) {
                odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
            logger.info("Se buscó el odontólogo con matrícula: "+string);
        }catch(Exception e){
                logger.error(e.getMessage());
        }
        return odontologo;
    }
}








