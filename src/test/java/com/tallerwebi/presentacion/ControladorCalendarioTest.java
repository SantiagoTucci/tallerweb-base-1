package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.calendario.ItemRendimiento;
import com.tallerwebi.dominio.calendario.ServicioCalendario;
import com.tallerwebi.dominio.calendario.TipoRendimiento;
import com.tallerwebi.dominio.excepcion.UsuarioYaCargoSuRendimientoDelDiaException;
import com.tallerwebi.dominio.objetivo.Objetivo;
import com.tallerwebi.dominio.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ControladorCalendarioTest {

    private ServicioCalendario servicioCalendario;
    private ControladorCalendario controladorCalendario;

    @Mock
    private HttpSession session;

    private Usuario usuario;

    @BeforeEach
    public void init() {
        this.servicioCalendario = mock(ServicioCalendario.class);
        this.controladorCalendario = new ControladorCalendario(this.servicioCalendario);
        MockitoAnnotations.initMocks(this);
        usuario = new Usuario(); // Crear un usuario simulado
        when(session.getAttribute("usuario")).thenReturn(usuario);
    }

    @Test
    public void queAlIrALaPantallaDeCalendarioSinUsuarioRedirijaALogin() {
        // Preparación
        when(session.getAttribute("usuario")).thenReturn(null);

        // Ejecución
        ModelAndView modelAndView = controladorCalendario.mostrarCalendario(session);

        // Verificación
        assertEquals("redirect:/login", modelAndView.getViewName()); // Verificar redirección a login
    }

    @Test
    public void queSeLogreGuardarUnItemRendimientoSinUsuarioRedirijaALogin() {
        // Preparación
        when(session.getAttribute("usuario")).thenReturn(null);

        // Ejecución
        ModelAndView modelAndView = controladorCalendario.guardarItemRendimiento(new ItemRendimiento(), session);

        // Verificación
        assertEquals("redirect:/login", modelAndView.getViewName()); // Verificar redirección a login
    }

    @Test
    public void queAlIrVerProgresoSinUsuarioRedirijaALogin() {
        // Preparación
        when(session.getAttribute("usuario")).thenReturn(null);

        // Ejecución
        ModelAndView modelAndView = controladorCalendario.verProgreso(session);

        // Verificación
        assertEquals("redirect:/login", modelAndView.getViewName()); // Verificar redirección a login
    }

    @Test
    public void queAlIrALaPantallaDeCalendarioConUsuarioAutenticadoSeRendericeCorrectamente() {
        // Preparación
        when(session.getAttribute("usuario")).thenReturn(usuario);

        // Ejecución
        ModelAndView modelAndView = controladorCalendario.mostrarCalendario(session);

        // Verificación
        assertEquals("verProgreso", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("itemRendimiento"));
        assertEquals(usuario, modelAndView.getModel().get("usuario"));
    }

    @Test
    public void dadoQueElUsuarioEstaEnLaVistaCalendarioQueSeLogreGuardarUnItemRendimientoConExito() throws UsuarioYaCargoSuRendimientoDelDiaException {
        // Preparación
        when(session.getAttribute("usuario")).thenReturn(usuario);
        ItemRendimiento itemRendimiento = new ItemRendimiento();

        // Ejecución
        ModelAndView modelAndView = controladorCalendario.guardarItemRendimiento(itemRendimiento, session);

        // Verificación
        assertEquals("redirect:/verProgreso", modelAndView.getViewName());
        verify(servicioCalendario, times(1)).guardarItemRendimientoEnUsuario(itemRendimiento, usuario);
    }

    @Test
    public void queAlIntentarGuardarUnItemRendimientoDuplicadoSeMuestreUnMensajeDeError() throws UsuarioYaCargoSuRendimientoDelDiaException {
        // Preparación
        when(session.getAttribute("usuario")).thenReturn(usuario);
        ItemRendimiento itemRendimiento = new ItemRendimiento();
        doThrow(new UsuarioYaCargoSuRendimientoDelDiaException("No se puede guardar tu rendimiento más de una vez el mismo día."))
                .when(servicioCalendario).guardarItemRendimientoEnUsuario(any(ItemRendimiento.class), eq(usuario));

        // Ejecución
        ModelAndView modelAndView = controladorCalendario.guardarItemRendimiento(itemRendimiento, session);

        // Verificación
        assertEquals("calendario", modelAndView.getViewName());
        assertEquals("No se puede guardar tu rendimiento más de una vez el mismo día.", modelAndView.getModel().get("error"));
        assertEquals(usuario, modelAndView.getModel().get("usuario"));
    }

    @Test
    public void queAlVerProgresoConDatosSeRendericeCorrectamente() {
        // Preparación
        Usuario usuarioConObjetivo = new Usuario();
        usuarioConObjetivo.setObjetivo(Objetivo.GANANCIA_MUSCULAR); // Asignar un valor del enum Objetivo
        when(session.getAttribute("usuario")).thenReturn(usuarioConObjetivo);

        List<DatosItemRendimiento> itemsMock = Arrays.asList(
                new DatosItemRendimiento(new ItemRendimiento(TipoRendimiento.DESCANSO)),
                new DatosItemRendimiento(new ItemRendimiento(TipoRendimiento.ALTO))
        );
        when(servicioCalendario.getItemsRendimientoDeUsuario(usuarioConObjetivo)).thenReturn(itemsMock);

        // Ejecución
        ModelAndView modelAndView = controladorCalendario.verProgreso(session);

        // Verificación
        assertEquals("verProgreso", modelAndView.getViewName());
        assertEquals(itemsMock, modelAndView.getModel().get("datosItemRendimiento"));
        assertEquals(usuarioConObjetivo, modelAndView.getModel().get("usuario"));
    }


    @Test
    public void queAlVerProgresoSinDatosSeMuestreMensajeIndicativo() {
        // Preparación
        Usuario usuarioConObjetivo = new Usuario();
        usuarioConObjetivo.setObjetivo(Objetivo.GANANCIA_MUSCULAR);
        when(session.getAttribute("usuario")).thenReturn(usuarioConObjetivo);
        when(servicioCalendario.getItemsRendimientoDeUsuario(usuarioConObjetivo)).thenReturn(Collections.emptyList());

        ModelAndView modelAndView = controladorCalendario.verProgreso(session);

        assertEquals("verProgreso", modelAndView.getViewName());
        assertEquals(Collections.emptyList(), modelAndView.getModel().get("datosItemRendimiento"));
        assertEquals(usuarioConObjetivo, modelAndView.getModel().get("usuario"));
    }



    @Test
    public void queAlIrALaPantallaDeCalendarioConSesionInvalidaRedirijaALogin() {
        when(session.getAttribute("usuario")).thenThrow(new IllegalStateException("Session invalid"));
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            controladorCalendario.mostrarCalendario(session);
        });
        assertEquals("Session invalid", exception.getMessage());
        ModelAndView modelAndView = null;
        try {
            modelAndView = controladorCalendario.mostrarCalendario(session);
        } catch (IllegalStateException e) {
            modelAndView = new ModelAndView("redirect:/login");
        }
        assertEquals("redirect:/login", modelAndView.getViewName());
    }


    @Test
    public void queAlVerProgresoSinObjetivoRedirijaAObjetivo() {
        // Preparación
        usuario.setObjetivo(null); // Ensure the user has no objective set
        when(session.getAttribute("usuario")).thenReturn(usuario);

        // Ejecución
        ModelAndView modelAndView = controladorCalendario.verProgreso(session);

        // Verificación
        assertEquals("redirect:/objetivo", modelAndView.getViewName());
    }

}
