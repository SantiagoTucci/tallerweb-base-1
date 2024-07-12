package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.ObjetivoNoExisteException;
import com.tallerwebi.dominio.excepcion.RutinaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoExisteException;
import com.tallerwebi.dominio.excepcion.UsuarioYaCargoSuRendimientoDelDiaException;
import com.tallerwebi.dominio.objetivo.Objetivo;
import com.tallerwebi.dominio.rutina.EstadoEjercicio;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.integracion.config.HibernateTestIntegracionConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import java.util.Objects;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.tallerwebi.dominio.rutina.ServicioRutina;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestIntegracionConfig.class})
public class ControladorRutinaTest {

    @Mock
    private ServicioRutina servicioRutina;

    @InjectMocks
    private ControladorRutina controladorRutina;

    private Usuario usuarioMock;
    private MockHttpSession session;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void debeRedirigirALoginCuandoElUsuarioNoEstaLogueado() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/rutinas"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }

    @Test
    public void queRedirijaALoginCuandoUsuarioEsNullEnIrAMiRutina() {
        // Preparación
        MockHttpSession session = new MockHttpSession();

        // Ejecución
        ModelAndView modelAndView = controladorRutina.irAMiRutina(session);

        // Verificación
        assertEquals("redirect:/login", modelAndView.getViewName());
    }

    @Test
    public void queRedirijaAObjetivoCuandoObjetivoDelUsuarioEsNullEnIrAMiRutina() {
        // Preparación
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario();
        usuario.setObjetivo(null);  // Usuario sin objetivo
        session.setAttribute("usuario", usuario);

        // Ejecución
        ModelAndView modelAndView = controladorRutina.irAMiRutina(session);

        // Verificación
        assertEquals("redirect:/objetivo", modelAndView.getViewName());
    }

    @Test
    public void queAlQuererActualizarElEstadoDeUnEjercicioRedirijaAlLoginPorqueElUsuarioNoEstaAutenticado() {
        // Caso de prueba 1
        String resultado = controladorRutina.actualizarEstadoEjercicio(1L, EstadoEjercicio.Estado.COMPLETO, session);
        assertEquals("redirect:/login", resultado);
    }

    @Test
    public void queAlGuardarObjetivoConElUsuarioAutenticadoYObjetivoValidoNosRedirijaARutinas() throws UsuarioNoExisteException, ObjetivoNoExisteException {
        // Caso de prueba 2
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);

        ModelAndView resultado = controladorRutina.guardarObjetivo("GANANCIA_MUSCULAR", session);
        assertEquals("redirect:/rutinas", resultado.getViewName());
        verify(servicioRutina, times(1)).guardarObjetivoEnUsuario(Objetivo.GANANCIA_MUSCULAR, usuario);
    }

    @Test
    public void queAlQuererCalcularElRendimientoDeLaRutinaConUsuarioNoAutenticadoNosRedirijaAlLogin() throws RutinaNoEncontradaException, UsuarioYaCargoSuRendimientoDelDiaException {
        ModelAndView resultado = controladorRutina.calcularRendimiento(1L, session);
        assertEquals("redirect:/login", resultado.getViewName());
    }

    @Test
    public void queAlGuardarObjetivoConElUsuarioAutenticadoSeLanceUnaExcepcionPorObjetivoInvalido() throws UsuarioNoExisteException, ObjetivoNoExisteException {
        // Caso de prueba 3
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);

        ModelAndView resultado = controladorRutina.guardarObjetivo("OBJETIVO_INVALIDO", session);
        assertEquals("objetivo", resultado.getViewName());
        assertTrue(resultado.getModel().containsKey("Excepcion:"));
        verify(servicioRutina, times(0)).guardarObjetivoEnUsuario(any(Objetivo.class), eq(usuario));
    }

    @Test
    public void queAlQuererVerLaVistaObjetivosRedirijaAlLoginSiElUsuarioNoEstaAutenticado() {
        ModelAndView resultado = controladorRutina.mostrarVistaObjetivos(session);
        assertEquals("redirect:/login", resultado.getViewName());
    }

    @Test
    public void queAlQuererGuardarUnObjetivoSinElUsuarioAutenticadoNosRedirijaAlLogin() {
        // Caso de prueba 1
        ModelAndView resultado = controladorRutina.guardarObjetivo("GANANCIA_MUSCULAR", session);
        assertEquals("redirect:/login", resultado.getViewName());
    }

    @Test
    public void queSeMuestreLaVistaObjetivoSiElUsuarioEstaAutenticado() {
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);

        ModelAndView resultado = controladorRutina.mostrarVistaObjetivos(session);
        assertEquals("objetivo", resultado.getViewName());
        assertEquals(usuario, resultado.getModel().get("usuario"));
    }

    @Test
    public void queAlActualizarUnEstadoEjercicioRedirijaAMiRutinaSiSePudoActualizar() {
        // Caso de prueba 2
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);

        String resultado = controladorRutina.actualizarEstadoEjercicio(1L, EstadoEjercicio.Estado.COMPLETO, session);
        assertEquals("redirect:/mi-rutina", resultado);

        verify(servicioRutina, times(1)).actualizarEstadoEjercicio(usuario, 1L, EstadoEjercicio.Estado.COMPLETO);
    }

    @Test
    public void queAlIntentarAsignarUnaRutinaRedirijaALoginPorqueElUsuarioNoEstaAutenticado() {
        ModelAndView modelAndView = controladorRutina.asignarRutina(1L, session);
        assertEquals("redirect:/login", modelAndView.getViewName());
    }
}
