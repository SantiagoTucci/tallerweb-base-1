package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.objetivo.Objetivo;
import com.tallerwebi.dominio.perfil.Perfil;
import com.tallerwebi.dominio.reto.Reto;
import com.tallerwebi.presentacion.DatosItemRendimiento;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;

    DatosItemRendimiento obtenerItemMasSeleccionado();

    DatosItemRendimiento obtenerItemMasSeleccionadoPorUsuario(Usuario usuario);

    Reto obtenerRetoDisponible();

    Reto obtenerRetoDisponiblePorUsuario(Usuario usuario);

    Reto obtenerRetoEnProceso();

    Reto obtenerRetoEnProcesoDeUsuario(Usuario usuario);

    Usuario modificarRachaRetoTerminado(Usuario usuario, long retoId);

    Usuario modificarRachaRetoTerminadoPorUsuario(Usuario usuario, long retoId);

    long calcularTiempoRestante(Long id);

    long calcularTiempoRestantePorUsuario(Long id, Usuario usuario);

    Reto cambiarReto(Long retoId, Usuario usuario);

    Reto cambiarRetoPorUsuario(Long retoId, Usuario usuario);

    void guardarPerfil(Usuario usuario, Perfil perfil);

    void guardarObjetivo(Usuario usuario, Objetivo objetivo);
}
