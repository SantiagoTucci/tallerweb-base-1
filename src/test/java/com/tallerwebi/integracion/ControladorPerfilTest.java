package com.tallerwebi.integracion;

import com.tallerwebi.dominio.objetivo.Objetivo;
import com.tallerwebi.dominio.perfil.Perfil;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.integracion.config.HibernateTestIntegracionConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestIntegracionConfig.class})
public class ControladorPerfilTest {

    private Usuario usuarioMock;
    private Perfil perfilMock;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        usuarioMock = new Usuario();
        usuarioMock.setEmail("test@unlam.edu.ar");
    }

    @Test
    public void debeRedirigirALoginCuandoElUsuarioNoEstaLogueado() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/perfil"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }

    @Test
    public void debeCerrarSesionYRedirigirALogin() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/cerrar-sesion")
                        .sessionAttr("usuario", usuarioMock))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }

    @Test
    public void debeMostrarPerfilCuandoUsuarioEstaLogueadoYTienePerfil() throws Exception {
        Perfil perfilExistente = new Perfil();
        perfilExistente.setEdad(30);
        perfilExistente.setPeso(75.0);
        perfilExistente.setAltura(180);
        perfilExistente.setGenero("Masculino");
        perfilExistente.setObjetivoFitness("PERDIDA_DE_PESO");
        perfilExistente.setExperienciaEjercicio("Intermedio");
        usuarioMock.setPerfil(perfilExistente);
        usuarioMock.setObjetivo(Objetivo.valueOf("GANANCIA_MUSCULAR"));

        MvcResult result = this.mockMvc.perform(get("/perfil")
                        .sessionAttr("usuario", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("perfil", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat((Perfil) modelAndView.getModel().get("perfil"), equalTo(perfilExistente));
    }


}
