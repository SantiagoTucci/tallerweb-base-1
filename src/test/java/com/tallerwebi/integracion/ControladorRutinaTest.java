package com.tallerwebi.integracion;

import com.tallerwebi.dominio.calendario.ItemRendimiento;
import com.tallerwebi.dominio.calendario.ServicioCalendario;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.rutina.EstadoEjercicio;
import com.tallerwebi.dominio.rutina.Rendimiento;
import com.tallerwebi.dominio.usuario.ServicioLogin;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.objetivo.Objetivo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.rutina.Rutina;
import com.tallerwebi.dominio.rutina.ServicioRutina;

import com.tallerwebi.presentacion.ControladorRutina;
import com.tallerwebi.presentacion.DatosRutina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ControladorRutinaTest {
    @Mock
    private Usuario usuarioMock;
    private MockMvc mockMvc;
    @Mock
    private Rutina rutinaMock;
    @Mock
    private DatosRutina datosRutinaMock;
    @Mock
    private HttpSession session;
    @Mock
    private ServicioRutina servicioRutina;

    @Mock
    private ServicioLogin servicioLogin;

    @Mock
    private ServicioCalendario servicioCalendario;

    @InjectMocks
    private ControladorRutina controladorRutina;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controladorRutina).build();
        session = new MockHttpSession();
        usuarioMock = new Usuario();
        usuarioMock.setNombre("Lautaro");
        usuarioMock.setObjetivo(Objetivo.PERDIDA_DE_PESO);
    }

    @Test
    public void queRedirijaAVistaObjetivosSiUsuarioNoTieneObjetivoDefinido() throws Exception {
        // Preparación
        Usuario usuarioSinObjetivo = new Usuario();
        usuarioSinObjetivo.setObjetivo(null);

        // Configuración de la sesión
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuario", usuarioSinObjetivo);

        // Ejecución
        mockMvc.perform(get("/mi-rutina").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/objetivo"));
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
    public void queRedirijaAVerProgresoCuandoUsuarioNoPuedeCargarRutinaHoy() {
        // Preparación
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario();
        usuario.setObjetivo(Objetivo.PERDIDA_DE_PESO);
        session.setAttribute("usuario", usuario);

        when(servicioCalendario.validarSiElUsuarioPuedeCargarRutinaHoy(usuario)).thenReturn(false);

        // Ejecución
        ModelAndView modelAndView = controladorRutina.irAMiRutina(session);

        // Verificación
        assertEquals("redirect:/verProgreso", modelAndView.getViewName());
        assertEquals("El usuario ya cargo el resultado de su rutina hoy.", modelAndView.getModel().get("info"));
    }

    @Test
    public void queRedirijaARutinasCuandoOcurreUnaExcepcion() throws UsuarioNoExisteException, RutinaNoEncontradaException {
        // Preparación
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario();
        usuario.setObjetivo(Objetivo.PERDIDA_DE_PESO);
        session.setAttribute("usuario", usuario);

        when(servicioCalendario.validarSiElUsuarioPuedeCargarRutinaHoy(usuario)).thenReturn(true);
        when(servicioRutina.getRutinaActualDelUsuario(usuario)).thenThrow(new RuntimeException());

        // Ejecución
        ModelAndView modelAndView = controladorRutina.irAMiRutina(session);

        // Verificación
        assertEquals("redirect:/rutinas", modelAndView.getViewName());
    }

    @Test
    public void queMuestreRutinaCuandoUsuarioPuedeCargarRutinaHoy() throws UsuarioNoExisteException, RutinaNoEncontradaException {
        // Preparación
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario();
        usuario.setObjetivo(Objetivo.PERDIDA_DE_PESO);
        session.setAttribute("usuario", usuario);

        DatosRutina rutina = new DatosRutina();
        List<EstadoEjercicio> estadosEjercicios = Collections.singletonList(new EstadoEjercicio());

        when(servicioCalendario.validarSiElUsuarioPuedeCargarRutinaHoy(usuario)).thenReturn(true);
        when(servicioRutina.getRutinaActualDelUsuario(usuario)).thenReturn(rutina);
        when(servicioRutina.getEstadosEjercicios(usuario, rutina)).thenReturn(estadosEjercicios);

        // Ejecución
        ModelAndView modelAndView = controladorRutina.irAMiRutina(session);

        // Verificación
        assertEquals("rutina", modelAndView.getViewName());
        assertEquals(usuario, modelAndView.getModel().get("usuario"));
        assertEquals(rutina, modelAndView.getModel().get("rutina"));
        assertEquals(estadosEjercicios, modelAndView.getModel().get("estadosEjercicios"));
    }


    @Test
    @Transactional
    @Rollback
    public void queAsigneRutinaAlUsuarioYRedirijaAVistaDeRutina() throws Exception {
        // Preparación
        Long rutinaId = 1L;
        Usuario usuarioMock = new Usuario("Lautaro", Objetivo.PERDIDA_DE_PESO);
        usuarioMock.setId(1L);
        Rutina rutinaMock = new Rutina("Rutina de correr", Objetivo.PERDIDA_DE_PESO);
        DatosRutina datosRutinaMock = new DatosRutina(rutinaMock);

        when(servicioRutina.getRutinaById(rutinaId)).thenReturn(rutinaMock);
        when(servicioRutina.getDatosRutinaById(rutinaId)).thenReturn(datosRutinaMock);

        // Configuración de la sesión
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuario", usuarioMock);

        // Ejecución
        MvcResult result = mockMvc.perform(get("/asignar-rutina")
                        .param("id", rutinaId.toString())
                        .session(session))
                .andExpect(view().name("redirect:/mi-rutina"))
                .andReturn();

        // Verificación
        ModelAndView modelAndView = result.getModelAndView();
        DatosRutina rutinaObtenida = (DatosRutina) modelAndView.getModel().get("rutina");

        assertThat(rutinaObtenida.getNombre(), equalTo(datosRutinaMock.getNombre()));
        verify(servicioRutina).asignarRutinaAUsuario(rutinaMock, usuarioMock);
    }

    @Test
    @Transactional
    @Rollback
    public void queAlQuererAsignarRutinaRedirijaAVistaRutinasSiLaRutinaNoExiste() throws Exception {
        // Preparación
        Long rutinaId = 1L;
        Usuario usuarioMock = new Usuario("Lautaro", Objetivo.PERDIDA_DE_PESO);
        usuarioMock.setId(1L);

        when(servicioRutina.getRutinaById(rutinaId)).thenReturn(null);

        // Configuración de la sesión
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuario", usuarioMock);

        // Ejecución
        mockMvc.perform(get("/asignar-rutina")
                        .param("id", rutinaId.toString())
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rutinas"));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlQuererAsignarUnaRutinaAUsuarioRedirijaAVistaRutinasSiOcurreUnaExcepcion() throws Exception {
        // Preparación
        Long rutinaId = 1L;
        Usuario usuarioMock = new Usuario("Lautaro", Objetivo.PERDIDA_DE_PESO);
        usuarioMock.setId(1L);

        when(servicioRutina.getRutinaById(rutinaId)).thenThrow(new RuntimeException());

        // Configuración de la sesión
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuario", usuarioMock);

        // Ejecución
        mockMvc.perform(get("/asignar-rutina")
                        .param("id", rutinaId.toString())
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rutinas"));
    }



    @Test
    @Transactional
    @Rollback
    public void queAlQuererLiberarRutinaMuestreErrorSiOcurreUnaExcepcion() throws Exception {
        // Preparación
        Long rutinaId = 1L;
        Usuario usuarioMock = new Usuario("Lautaro", Objetivo.PERDIDA_DE_PESO);
        usuarioMock.setId(1L);

        when(servicioRutina.getRutinaById(rutinaId)).thenThrow(new RuntimeException());

        // Configuración de la sesión
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuario", usuarioMock);

        // Ejecución
        MvcResult result = mockMvc.perform(post("/liberar-rutina")
                        .param("id", rutinaId.toString())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("rutina"))
                .andReturn();

        // Verificación
        ModelAndView modelAndView = result.getModelAndView();
        assertThat(modelAndView.getModel().get("error"), equalTo("Error al liberar la rutina."));
    }


    @Test
    public void queAlIntentarAsignarUnaRutinaRedirijaALoginPorqueElUsuarioNoEstaAutenticado() {
        // Inicialización de Mockito
        MockitoAnnotations.openMocks(this);

        // Simular que no hay usuario en la sesión
        when(session.getAttribute("usuario")).thenReturn(null);

        // Ejecutar el método bajo prueba
        ModelAndView modelAndView = controladorRutina.asignarRutina(1L, session);

        // Verificar la redirección a login
        assertEquals("redirect:/login", modelAndView.getViewName());
    }

    @Test
    public void asignarRutina_RutinaNoExiste() throws RutinaNoEncontradaException {
        // Inicialización de Mockito
        MockitoAnnotations.openMocks(this);

        // Simular que hay un usuario en la sesión
        Usuario usuarioMock = new Usuario(); // Puedes crear un mock de Usuario si es necesario
        when(session.getAttribute("usuario")).thenReturn(usuarioMock);

        // Simular que no se encuentra la rutina por el ID
        when(servicioRutina.getRutinaById(1L)).thenReturn(null);

        // Ejecutar el método bajo prueba
        ModelAndView modelAndView = controladorRutina.asignarRutina(1L, session);

        // Verificar la redirección a rutinas
        assertEquals("redirect:/rutinas", modelAndView.getViewName());
    }

    @Test
    public void asignarRutina_RutinaExiste() throws RutinaNoEncontradaException, RutinaYaExisteException {
        // Inicialización de Mockito
        MockitoAnnotations.openMocks(this);

        // Simular que hay un usuario en la sesión
        when(session.getAttribute("usuario")).thenReturn(usuarioMock);

        usuarioMock.setId(1L);
        rutinaMock.setIdRutina(1L);

        // Simular que se encuentra la rutina y datos de rutina por el ID
        when(servicioRutina.getRutinaById(1L)).thenReturn(rutinaMock);
        when(servicioRutina.getDatosRutinaById(1L)).thenReturn(datosRutinaMock);

        // Ejecutar el método bajo prueba
        ModelAndView modelAndView = controladorRutina.asignarRutina(1L, session);


        // Verificar la redirección esperada
        assertEquals("redirect:/rutinas", modelAndView.getViewName());
    }

    @Test
    public void asignarRutina_Exception() throws RutinaYaExisteException {
        // Inicialización de Mockito
        MockitoAnnotations.openMocks(this);

        // Simular que hay un usuario en la sesión
        when(session.getAttribute("usuario")).thenReturn(usuarioMock);

        // Simular que se lanza una excepción al asignar la rutina
        doThrow(new RutinaYaExisteException()).when(servicioRutina).asignarRutinaAUsuario(any(Rutina.class), any(Usuario.class));

        // Ejecutar el método bajo prueba
        ModelAndView modelAndView = controladorRutina.asignarRutina(1L, session);

        // Verificar la redirección esperada
        assertEquals("redirect:/rutinas", modelAndView.getViewName());
    }





    @Test
    @Transactional
    @Rollback
    public void queAlQuererLiberarRutinaMuestreErrorSiRutinaNoExiste() throws Exception {
        // Preparación
        Long rutinaId = 1L;
        Usuario usuarioMock = new Usuario("Lautaro", Objetivo.PERDIDA_DE_PESO);
        usuarioMock.setId(1L);

        when(servicioRutina.getRutinaById(rutinaId)).thenReturn(null);

        // Configuración de la sesión
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuario", usuarioMock);

        // Ejecución
        MvcResult result = mockMvc.perform(post("/liberar-rutina")
                        .param("id", rutinaId.toString())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("rutina"))
                .andReturn();

        // Verificación
        ModelAndView modelAndView = result.getModelAndView();
        assertThat(modelAndView.getModel().get("error"), equalTo("Error al liberar la rutina."));
    }

    @Test
    public void quePodamosCalcularElRendimientoDeLaRutinaConUsuarioAutenticadoDeUnaRutinaExistente() throws Exception {
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);
        DatosRutina datosRutina = new DatosRutina();
        List<EstadoEjercicio> estadosEjercicios = List.of(new EstadoEjercicio(), new EstadoEjercicio());
        Rendimiento rendimiento = Rendimiento.ALTO;
        ItemRendimiento itemRendimiento = new ItemRendimiento();

        when(servicioRutina.getDatosRutinaById(1L)).thenReturn(datosRutina);
        when(servicioRutina.getEstadosEjercicios(usuario, datosRutina)).thenReturn(estadosEjercicios);
        when(servicioRutina.calcularRendimiento(estadosEjercicios)).thenReturn(rendimiento);
        when(servicioCalendario.convertirRendimientoAItemRendimiento(rendimiento)).thenReturn(itemRendimiento);

        ModelAndView resultado = controladorRutina.calcularRendimiento(1L, session);
        assertEquals("redirect:/verProgreso", resultado.getViewName());
        verify(servicioCalendario, times(1)).guardarItemRendimientoEnUsuario(itemRendimiento, usuario);
    }

    @Test
    public void quePodamosCalcularElRendimientoDeLaRutinaConUsuarioAutenticadoDeUnaRutinaNoEncontrada() throws Exception {
        // Caso de prueba 3
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);

        when(servicioRutina.getDatosRutinaById(1L)).thenThrow(new RutinaNoEncontradaException("Rutina no encontrada"));

        assertThrows(RutinaNoEncontradaException.class, () -> {
            controladorRutina.calcularRendimiento(1L, session);
        });
    }

    @Test
    public void queAlQuererCalcularRendimientoDeUnaRutinaConUsuarioAutenticadoUnRendimientoYaSeHayaCargadoEnElMismoDia() throws Exception {
        // Caso de prueba 4
        Usuario usuario = new Usuario();
        session.setAttribute("usuario", usuario);
        DatosRutina datosRutina = new DatosRutina();

        when(servicioRutina.getDatosRutinaById(1L)).thenReturn(datosRutina);
        when(servicioRutina.getEstadosEjercicios(usuario, datosRutina)).thenReturn(List.of(new EstadoEjercicio()));
        when(servicioRutina.calcularRendimiento(anyList())).thenReturn(Rendimiento.ALTO);
        when(servicioCalendario.convertirRendimientoAItemRendimiento(any(Rendimiento.class))).thenReturn(new ItemRendimiento());
        doThrow(new UsuarioYaCargoSuRendimientoDelDiaException("Usuario ya cargó su rendimiento del día")).when(servicioCalendario).guardarItemRendimientoEnUsuario(any(ItemRendimiento.class), eq(usuario));

        assertThrows(UsuarioYaCargoSuRendimientoDelDiaException.class, () -> {
            controladorRutina.calcularRendimiento(1L, session);
        });
    }

}
