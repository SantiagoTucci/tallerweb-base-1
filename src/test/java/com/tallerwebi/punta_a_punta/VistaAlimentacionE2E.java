package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaAlimentacion;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;


public class VistaAlimentacionE2E {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaAlimentacion vistaAlimentacion;
    VistaLogin vistaLogin;


    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
//        browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(400));

    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaLogin = new VistaLogin(page);
        vistaLogin.escribirEMAIL("test@unlam.edu.ar");
        vistaLogin.escribirClave("test");
        vistaLogin.darClickEnIniciarSesion();
        vistaAlimentacion = new VistaAlimentacion(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaDecirMagnusFitEnElNav() {
        String texto = vistaAlimentacion.obtenerTextoDeLaBarraDeNavegacion();
        assertThat("MagnusFit", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaHaberUnTitulo() {
        String texto = vistaAlimentacion.obtenerTextoDelTitulo();
        assertThat("Te presentamos nuestro apartado de alimentación!", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaHaberUnaDescripcion() {
        String texto = vistaAlimentacion.obtenerTextoDeDescripcion();
        assertThat("Debido a tu elección de objetivo te proponemos distintos platos para que agregues a tu dieta. Tené en cuenta ajustar las porciones de acuerdo a tu objetivo. Si querés cambiarlo haga click aquí", equalToIgnoringCase(texto));
    }
}
