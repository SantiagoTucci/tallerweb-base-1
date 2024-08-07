package com.tallerwebi.dominio.reto;

import com.tallerwebi.dominio.excepcion.RetoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ServicioRetoImpl implements ServicioReto{

    private RepositorioReto repositorioReto;

    @Autowired
    public ServicioRetoImpl(RepositorioReto repositorioReto){
        this.repositorioReto = repositorioReto;
    }

    @Override
    public Reto obtenerRetoDisponible() {
        Reto reto = repositorioReto.obtenerRetoDisponible();
        if (reto == null) {
            reiniciarRetos();
            reto = repositorioReto.obtenerRetoDisponible();
        }
        return reto;
    }

    @Override
    public void empezarRetoActualizado(Long retoId) {
        Reto reto = repositorioReto.obtenerRetoPorId(retoId);
        if (reto == null) {
            throw new RetoNoEncontradoException("Reto no encontrado.");
        }
        reto.setSeleccionado(true); // Marcar como seleccionado
        reto.setEnProceso(true);
        reto.setFechaInicio(LocalDate.now());
        repositorioReto.actualizarReto(reto); // Actualizar el reto en el repositorio
    }


    @Override
    public Reto obtenerRetoPorId(Long retoId) {
        Reto reto = repositorioReto.obtenerRetoPorId(retoId);
        if (reto == null) {
            throw new RetoNoEncontradoException("Reto no encontrado.");
        }
        return reto;
    }


    @Override
    public Reto obtenerRetoEnProceso() {
        return repositorioReto.obtenerRetoEnProceso();
    }

    @Override
    public Long terminarReto(Long retoId) {
        Reto reto = repositorioReto.obtenerRetoPorId(retoId);
        long diasPasados = 0;
        if (reto != null) {
            // Calcular la diferencia de días
            LocalDate fechaInicio = reto.getFechaInicio();
            LocalDate fechaActual = LocalDate.now();
            diasPasados = ChronoUnit.DAYS.between(fechaInicio, fechaActual);
            reto.setEnProceso(false);
            reto.setFechaInicio(null);
            repositorioReto.actualizarReto(reto); // Actualizar el reto en el repositorio
        }
        return diasPasados;
    }

    @Override
    public Long calcularTiempoRestante(Long retoId) {
        Reto reto = repositorioReto.obtenerRetoPorId(retoId);
        if (reto == null || reto.getFechaInicio() == null) {
            throw new RetoNoEncontradoException("Reto no encontrado.");
        }
        LocalDateTime fechaInicio = reto.getFechaInicio().atStartOfDay();
        LocalDateTime fechaFin = fechaInicio.plusDays(2); // El reto dura 2 días completos
        LocalDateTime fechaActual = LocalDateTime.now();
        Duration duracion = Duration.between(fechaActual, fechaFin);
        return duracion.toMinutes();
    }



    private void reiniciarRetos() {
        List<Reto> retos = repositorioReto.obtenerTodosLosRetos();
        for (Reto reto : retos) {
            reto.setSeleccionado(false);
            reto.setEnProceso(false);
            repositorioReto.actualizarReto(reto);
        }
    }

    @Override
    public Reto cambiarReto(Reto reto) {
        if (reto == null) {
            throw new IllegalArgumentException("El reto no puede ser nulo.");
        }
        reto.setSeleccionado(true);
        repositorioReto.actualizarReto(reto);
        return reto;
    }



}
