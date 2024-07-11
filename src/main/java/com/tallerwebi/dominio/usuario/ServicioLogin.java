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
    Reto obtenerRetoEnProceso();

    Usuario modificarRachaRetoTerminado(Usuario usuario, long retoId);

    long calcularTiempoRestante(Long id);

    Reto cambiarReto(Long retoId, Usuario usuario);

    void guardarPerfil(Usuario usuario, Perfil perfil);

    void guardarObjetivo(Usuario usuario, Objetivo objetivo);
}
