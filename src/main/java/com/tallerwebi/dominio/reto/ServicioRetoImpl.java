package com.tallerwebi.dominio.reto;

import com.tallerwebi.dominio.excepcion.RetoNoEncontradoException;
import com.tallerwebi.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Reto cambiarReto(Reto reto) {
        if (reto == null) {
            throw new IllegalArgumentException("El reto no puede ser nulo.");
        }
        reto.setSeleccionado(true);
        repositorioReto.actualizarReto(reto);
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
    public Reto obtenerRetoEnProcesoDeUsuario(Usuario usuario) {
        return repositorioReto.obtenerRetoEnProcesoDeUsuario(usuario);
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
    public Reto obtenerRetoPorIdPorUsuario(Long retoId, Usuario usuario) {
        Reto reto = repositorioReto.obtenerRetoPorIdPorUsuario(retoId, usuario);
        if (reto == null) {
            throw new RetoNoEncontradoException("Reto no encontrado.");
        }
        return reto;
    }

    @Override
    public Reto obtenerRetoDisponiblePorUsuario(Usuario usuario) {
        Reto reto = repositorioReto.obtenerRetoDisponibleParaUsuario(usuario);
        if (reto == null) {
            reiniciarRetosPorUsuario(usuario);
            reto = repositorioReto.obtenerRetoDisponibleParaUsuario(usuario);
        }
        return reto;
    }

    @Override
    public void empezarRetoActualizadoPorUsuario(Long retoId, Usuario usuario) {
        Reto reto = repositorioReto.obtenerRetoPorIdPorUsuario(retoId, usuario);
        if (reto == null) {
            throw new RetoNoEncontradoException("Reto no encontrado.");
        }
        reto.setSeleccionado(true); // Marcar como seleccionado
        reto.setEnProceso(true);
        reto.setFechaInicio(LocalDate.now());
        repositorioReto.actualizarRetoPorUsuario(reto, usuario); // Actualizar el reto en el repositorio
    }

    @Override
    public Long terminarRetoPorUsuario(Long retoId, Usuario usuario) {
        Reto reto = repositorioReto.obtenerRetoPorIdPorUsuario(retoId, usuario);
        long diasPasados = 0;
        if (reto != null) {
            // Calcular la diferencia de días
            LocalDate fechaInicio = reto.getFechaInicio();
            LocalDate fechaActual = LocalDate.now();
            diasPasados = ChronoUnit.DAYS.between(fechaInicio, fechaActual);
            reto.setEnProceso(false);
            reto.setFechaInicio(null);
            repositorioReto.actualizarRetoPorUsuario(reto, usuario); // Actualizar el reto en el repositorio
        }
        return diasPasados;
    }

    @Override
    public Long calcularTiempoRestantePorUsuario(Long retoId, Usuario usuario) {
        Reto reto = repositorioReto.obtenerRetoPorIdPorUsuario(retoId, usuario);
        if (reto == null || reto.getFechaInicio() == null) {
            throw new RetoNoEncontradoException("Reto no encontrado.");
        }
        LocalDateTime fechaInicio = reto.getFechaInicio().atStartOfDay();
        LocalDateTime fechaFin = fechaInicio.plusDays(2); // El reto dura 2 días completos
        LocalDateTime fechaActual = LocalDateTime.now();
        Duration duracion = Duration.between(fechaActual, fechaFin);
        return duracion.toMinutes();
    }

    public void reiniciarRetosPorUsuario(Usuario usuario) {
        List<Reto> retos = repositorioReto.obtenerTodosLosRetosDeUsuario(usuario);
        for (Reto reto : retos) {
            reto.setSeleccionado(false);
            reto.setEnProceso(false);
            repositorioReto.actualizarRetoPorUsuario(reto, usuario);
        }
    }

    @Override
    public Reto cambiarRetoPorUsuario(Reto reto, Usuario usuario) {
        if (reto == null) {
            throw new IllegalArgumentException("El reto no puede ser nulo.");
        }
        reto.setSeleccionado(true);
        repositorioReto.actualizarRetoPorUsuario(reto, usuario);
        return reto;
    }

    @Override
    public void guardarRetoEnUsuario(Reto reto, Usuario usuario) {
            this.repositorioReto.guardarRetoUsuario(reto, usuario);
    }


}
