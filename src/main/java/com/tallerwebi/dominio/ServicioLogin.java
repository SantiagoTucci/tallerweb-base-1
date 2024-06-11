package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.reto.Reto;
import com.tallerwebi.presentacion.DatosItemRendimiento;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;

//    Usuario sumarRachaReto(Usuario usuario);

    DatosItemRendimiento obtenerItemMasSeleccionado();

    Reto obtenerRetoDisponible();
    Reto obtenerRetoEnProceso();

    Usuario modificarRachaRetoTerminado(Usuario usuario, long retoId);
}
