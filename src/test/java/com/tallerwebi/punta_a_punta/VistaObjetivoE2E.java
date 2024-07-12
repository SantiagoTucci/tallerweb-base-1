package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaHome;
import com.tallerwebi.punta_a_punta.vistas.VistaObjetivo;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
public class VistaObjetivoE2E {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaObjetivo vistaObjetivo;
    VistaLogin vistaLogin;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(300));

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
        vistaObjetivo = new VistaObjetivo(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaDecirMagnusFitEnElNav() {
        String texto = vistaObjetivo.obtenerTextoDeLaBarraDeNavegacion();
        assertThat("MagnusFit", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaDecirDefinirObjetivosComoTitulo() {
        String texto = vistaObjetivo.obtenerTextoDelTitulo();
        assertThat("Definir Objetivos de Entrenamiento", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaHaberUnaTarjetaDePerdidaDePeso() {
        String texto = vistaObjetivo.obtenerTextoDePerdidaDePeso();
        assertThat("Pérdida de Peso", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaHaberUnBotonDeGuardar() {
        String texto = vistaObjetivo.obtenerTextoDeBotonGuardar();
        assertThat("Guardar", equalToIgnoringCase(texto));
    }

    @Test
    void deberiaPoderGuardarLaRutina() {
        vistaObjetivo.darClickEnPerderPeso();
        vistaObjetivo.darClickEnGuardar();
        String url = vistaObjetivo.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/spring/rutinas"));
    }
}
