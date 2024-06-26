package com.tallerwebi.dominio.reto;

import com.tallerwebi.dominio.calendario.ItemRendimiento;

import javax.transaction.Transactional;
@Transactional
public interface ServicioReto {

    Reto obtenerRetoDisponible();
    void empezarRetoActualizado(Long retoId);
    Reto obtenerRetoPorId(Long retoId);
    Reto obtenerRetoEnProceso();
    Long terminarReto(Long retoId);

    Long calcularTiempoRestante(Long retoId);

    Reto cambiarReto(Reto reto);
}
