package com.tallerwebi.integracion;

import com.tallerwebi.dominio.objetivo.Objetivo;
import com.tallerwebi.integracion.config.HibernateTestIntegracionConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import com.tallerwebi.dominio.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestIntegracionConfig.class})
public class ControladorAlimentacionTest {

    private Usuario usuarioMock;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        usuarioMock = new Usuario();
        usuarioMock.setEmail("dami@unlam.com");
    }

    @Test
    public void debeRedirigirALoginCuandoElUsuarioNoEstaLogueado() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/alimentacion"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }

    @Test
    public void debeRedirigirAObjetivoCuandoElUsuarioNoTieneObjetivo() throws Exception {
        usuarioMock.setObjetivo(null);

        MvcResult result = this.mockMvc.perform(get("/alimentacion")
                        .sessionAttr("usuario", usuarioMock))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/objetivo", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }

    @Test
    public void debeMostrarPaginaAlimentacionCuandoElUsuarioTieneObjetivoPerderPeso() throws Exception {
        usuarioMock.setObjetivo(Objetivo.PERDIDA_DE_PESO);

        MvcResult result = this.mockMvc.perform(get("/alimentacion")
                        .sessionAttr("usuario", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("alimentacion"));
        assertThat(modelAndView.getModel().get("usuario"), is(notNullValue()));
        assertThat(modelAndView.getModel().get("usuario"), is(equalTo(usuarioMock)));
    }

    @Test
    public void debeMostrarPaginaAlimentacionCuandoElUsuarioTieneObjetivoGanarMusculo() throws Exception {
        usuarioMock.setObjetivo(Objetivo.GANANCIA_MUSCULAR);

        MvcResult result = this.mockMvc.perform(get("/alimentacion")
                        .sessionAttr("usuario", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("alimentacion"));
        assertThat(modelAndView.getModel().get("usuario"), is(notNullValue()));
        assertThat(modelAndView.getModel().get("usuario"), is(equalTo(usuarioMock)));
    }

    @Test
    public void debeMostrarPaginaAlimentacionCuandoElUsuarioTieneObjetivoDefinicion() throws Exception {
        usuarioMock.setObjetivo(Objetivo.DEFINICION);

        MvcResult result = this.mockMvc.perform(get("/alimentacion")
                        .sessionAttr("usuario", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("alimentacion"));
        assertThat(modelAndView.getModel().get("usuario"), is(notNullValue()));
        assertThat(modelAndView.getModel().get("usuario"), is(equalTo(usuarioMock)));
    }
}
