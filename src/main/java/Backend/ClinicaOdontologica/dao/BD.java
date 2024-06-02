package Backend.ClinicaOdontologica.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BD {
    private static final Logger logger = Logger.getLogger(Backend.ClinicaOdontologica.dao.BD.class);
    private static final String SQL_DROP_CREATE_PAC = "DROP TABLE IF EXISTS PACIENTES; " +
            "CREATE TABLE PACIENTES (" +
            "ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "NOMBRE VARCHAR(100) NOT NULL, " +
            "APELLIDO VARCHAR(100) NOT NULL, " +
            "CEDULA VARCHAR(100) NOT NULL, " +
            "FECHA_INGRESO DATE NOT NULL, " +
            "DOMICILIO_ID INT NOT NULL, " +
            "EMAIL VARCHAR(100) NOT NULL)";
    private static final String SQL_DROP_CREATE_DOM = "DROP TABLE IF EXISTS DOMICILIOS; " +
            "CREATE TABLE DOMICILIOS (" +
            "ID INT AUTO_INCREMENT PRIMARY KEY," +
            "CALLE VARCHAR(100) NOT NULL," +
            "NUMERO INT NOT NULL," +
            "LOCALIDAD VARCHAR(100) NOT NULL," +
            "PROVINCIA VARCHAR(100) NOT NULL)";

    private static final String SQL_DROP_CREATE_ODONT="DROP TABLE IF EXISTS ODONTOLOGOS; " +
            "CREATE TABLE ODONTOLOGOS (" +
            "ID INT AUTO_INCREMENT PRIMARY KEY, " +
            "MATRICULA VARCHAR(100) NOT NULL, " +
            "NOMBRE VARCHAR(100) NOT NULL, " +
            "APELLIDO VARCHAR(100) NOT NULL)";
    private static final String SQL_PRUEBA = "INSERT INTO PACIENTES (NOMBRE, APELLIDO, CEDULA, FECHA_INGRESO, DOMICILIO_ID, EMAIL)" +
            "VALUES ('Jorgito','Pereyra','111111','2024-05-16', 1, 'jorgito@jorgito.com'), ('German','Fraire','22222','2024-05-10', 2, 'german@german.com'); " +
            "INSERT INTO DOMICILIOS (CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES ('Siempre Viva',742,'Springfield','USA'),('Av. Uruguay',345,'Punta del Este','Uruguay');" +
            "INSERT INTO ODONTOLOGOS (MATRICULA, NOMBRE, APELLIDO) VALUES (5678, 'Lola', 'Martinez'), (3456, 'David', 'Suarez'), (9876, 'Marta', 'Gómez')";

    public static void crearTablas() {
        Connection connection = null;

        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQL_DROP_CREATE_DOM);
            statement.execute(SQL_DROP_CREATE_PAC);
            statement.execute(SQL_DROP_CREATE_ODONT);
            statement.execute(SQL_PRUEBA);
            logger.info("tablas creadas con éxito");
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    public static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:~/clinicaOdontologica", "sa", "sa");
    }
}
