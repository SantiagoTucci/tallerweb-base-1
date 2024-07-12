package com.tallerwebi.integracion;

import com.tallerwebi.dominio.calendario.ServicioCalendario;
import com.tallerwebi.integracion.config.HibernateTestIntegracionConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.dominio.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestIntegracionConfig.class})
public class ControladorCalendarioTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Mock
    private ServicioCalendario servicioCalendario;

    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        usuarioMock = new Usuario();
        usuarioMock.setEmail("dami@unlam.com");
    }

    @Test
    public void debeRetornarLaPaginaCalendarioCuandoElUsuarioEstaLogueado() throws Exception {
        HttpSession session = mockMvc.perform(get("/calendario")
                        .sessionAttr("usuario", usuarioMock))
                .andExpect(status().isOk())
                .andReturn()
                .getRequest()
                .getSession();

        assertThat(session.getAttribute("usuario"), is(notNullValue()));

        MvcResult result = this.mockMvc.perform(get("/calendario")
                        .sessionAttr("usuario", usuarioMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("verProgreso"));
        assertThat(modelAndView.getModel().get("message").toString(), containsString("¿Cómo fue tu entrenamiento hoy?"));
    }


    @Test
    public void debeRedirigirALoginCuandoElUsuarioNoEstaLogueado() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/calendario"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }


}
