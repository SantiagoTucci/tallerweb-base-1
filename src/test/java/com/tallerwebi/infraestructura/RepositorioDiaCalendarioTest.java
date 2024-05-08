package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DiaCalendario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.dominio.Usuario;
import org.hamcrest.Matcher;
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
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.tallerwebi.dominio.*;

public class RepositorioDiaCalendarioTest {

    private DiaCalendario diaCalendario;

    @Test
    public void queSePuedaCrearUnDiaCalendario(){
        DiaCalendario diaCalendario = new DiaCalendario(7,5,2024,Rendimiento.NORMAL);
        Integer respuestaObtenida = diaCalendario.getDia();
        Integer respuestaEsperada = 7;
        assertThat(respuestaObtenida,equalTo(respuestaEsperada));
    }

    @Test
    public void queSeRetorneElRendimientoDelDia() {
        // Crear un objeto DiaCalendario simulado
        DiaCalendario diaCalendario = mock(DiaCalendario.class);

        // Definir el comportamiento esperado cuando se llame al método getRendimiento()
        when(diaCalendario.getRendimiento()).thenReturn(Rendimiento.ALTO);

        // Verificar que se obtiene el rendimiento esperado
        Rendimiento rendimientoEsperado = Rendimiento.ALTO;
        Rendimiento rendimientoObtenido = diaCalendario.getRendimiento();

        assertEquals(rendimientoEsperado, rendimientoObtenido);
    }

    @Test
    public void queSiEseDiaNoSeEligioUnaOpcionMuestreDescanso() {
        DiaCalendario diaCalendario = new DiaCalendario(7, 5, 2024, Rendimiento.DESCANSO);

        String rendimientoDia = diaCalendario.getRendimiento().name();

        // Verificar que la representación del rendimiento sea "Descanso"
        assertEquals("DESCANSO", rendimientoDia);
    }

}