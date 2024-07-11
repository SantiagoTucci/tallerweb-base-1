package com.tallerwebi.dominio.reto;

import com.tallerwebi.dominio.calendario.ItemRendimiento;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.presentacion.DatosItemRendimiento;

import java.util.List;

public interface RepositorioReto {

    void guardarReto(Reto reto);

    Reto obtenerRetoDisponible();

    Reto obtenerRetoDisponibleParaUsuario(Usuario usuario);

    Reto obtenerRetoPorId(Long retoId);

    Reto obtenerRetoPorIdPorUsuario(Long retoId, Usuario usuario);

    void actualizarReto(Reto reto);

    void actualizarRetoPorUsuario(Reto reto, Usuario usuario);

    Reto obtenerRetoEnProceso();

    Reto obtenerRetoEnProcesoDeUsuario(Usuario usuario);

    List<Reto> obtenerTodosLosRetos();

    List<Reto> obtenerTodosLosRetosDeUsuario(Usuario usuario);

    void guardarRetoUsuario(Reto reto, Usuario usuario);

    Usuario getUsuarioById(Long id);

    List<Reto> getRetosDeUsuario(Usuario usuario);
}
