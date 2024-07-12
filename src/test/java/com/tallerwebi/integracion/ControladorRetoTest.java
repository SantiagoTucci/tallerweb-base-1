package com.tallerwebi.integracion;

import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.integracion.config.HibernateTestIntegracionConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestIntegracionConfig.class})
public class ControladorRetoTest {

    private Usuario usuarioMock;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.usuarioMock = new Usuario();
        usuarioMock.setEmail("dami@unlam.com");
        usuarioMock.setPassword("password");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void debeRedirigirALoginCuandoElUsuarioNoEstaLogueadoAlEmpezarReto() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/empezar-reto")
                        .param("retoId", "1")
                        .param("email", "dami@unlam.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }


    @Test
    public void debeRedirigirALoginCuandoElUsuarioNoEstaLogueadoAlTerminarReto() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/terminar-reto")
                        .param("retoId", "1")
                        .param("email", "dami@unlam.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/login", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }

    @Test
    public void debeTerminarRetoCuandoElUsuarioEstaLogueado() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/terminar-reto")
                        .sessionAttr("usuario", usuarioMock)
                        .param("retoId", "1")
                        .param("email", "dami@unlam.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/home", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }


    @Test
    public void debeCambiarRetoCuandoElUsuarioEstaLogueado() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/cambiar-reto")
                        .sessionAttr("usuario", usuarioMock)
                        .param("retoId", "1")
                        .param("email", "dami@unlam.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/home", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
    }
}
