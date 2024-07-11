package com.tallerwebi.dominio.reto;

import com.tallerwebi.dominio.calendario.ItemRendimiento;
import com.tallerwebi.dominio.usuario.Usuario;

import javax.transaction.Transactional;
@Transactional
public interface ServicioReto {

    Reto obtenerRetoDisponible();
    void empezarRetoActualizado(Long retoId);
    Reto obtenerRetoPorId(Long retoId);
    Reto obtenerRetoEnProceso();

    Reto obtenerRetoEnProcesoDeUsuario(Usuario usuario);

    Long terminarReto(Long retoId);

    Long calcularTiempoRestante(Long retoId);

    Reto cambiarReto(Reto reto);

    Reto obtenerRetoPorIdPorUsuario(Long retoId, Usuario usuario);

    Reto obtenerRetoDisponiblePorUsuario(Usuario usuario);

    void empezarRetoActualizadoPorUsuario(Long retoId, Usuario usuario);

    Long terminarRetoPorUsuario(Long retoId, Usuario usuario);

    Long calcularTiempoRestantePorUsuario(Long retoId, Usuario usuario);

    Reto cambiarRetoPorUsuario(Reto reto, Usuario usuario);

    void guardarRetoEnUsuario(Reto reto, Usuario usuario);
}
