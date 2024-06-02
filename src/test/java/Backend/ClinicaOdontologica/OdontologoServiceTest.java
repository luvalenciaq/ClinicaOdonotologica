package Backend.ClinicaOdontologica;

import Backend.ClinicaOdontologica.dao.BD;
import Backend.ClinicaOdontologica.dao.OdontologoDAOH2;
import Backend.ClinicaOdontologica.model.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Backend.ClinicaOdontologica.service.OdontologoService;

import java.util.List;

public class OdontologoServiceTest {
    @Test
    public void agregarOdontologo() {
        BD.crearTablas();

        OdontologoDAOH2 dao = new OdontologoDAOH2();
        OdontologoService odontologoService = new OdontologoService();

        Odontologo odontologo = new Odontologo("123", "Juan", "Palaceto");
        odontologoService.guardarOdontologo(odontologo); // guarda en bd

        Assertions.assertTrue(odontologo.getId()!=0);
    }

    @Test
    public void buscarTodos() {
        BD.crearTablas();

        OdontologoDAOH2 dao = new OdontologoDAOH2();
        OdontologoService odontologoService = new OdontologoService(dao);

        Odontologo odontologo = new Odontologo("123", "Juan", "Palaceto");
        Odontologo odontologo1 = new Odontologo("124", "Luisa", "Valencia");
        Odontologo odontologo2 = new Odontologo("125", "Jorgito", "Pereyra");

        odontologoService.guardarOdontologo(odontologo);
        odontologoService.guardarOdontologo(odontologo1);
        odontologoService.guardarOdontologo(odontologo2);

        List<Odontologo> odontologoList = odontologoService.listarTodos();

        for (Odontologo o : odontologoList) {
            System.out.println("Id: " + o.getId() + ", Nombre: " + o.getNombre() + ", Apellido: " + o.getApellido());
        }
    }
}
