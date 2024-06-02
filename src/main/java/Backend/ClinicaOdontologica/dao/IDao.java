package Backend.ClinicaOdontologica.dao;

import java.util.List;

public interface IDao<T> {
    T guardar(T t);
    T buscarPorId(int id);
    void eliminar(int id);
    void actualizar(T t);
    List<T> buscarTodos();
    T buscarPorString(String string);
}
