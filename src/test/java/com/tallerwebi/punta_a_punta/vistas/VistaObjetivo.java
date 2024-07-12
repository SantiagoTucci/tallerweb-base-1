package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaObjetivo extends VistaWeb{

    public VistaObjetivo(Page page) {
        super(page);
        page.navigate("localhost:8081/spring/objetivo");
    }

    public String obtenerTextoDeLaBarraDeNavegacion() {
        return this.obtenerTextoDelElemento("h1.logo-text");
    }

    public String obtenerTextoDelTitulo() {
        return this.obtenerTextoDelElemento("h2");
    }

    public String obtenerTextoDePerdidaDePeso() {
        return this.obtenerTextoDelElemento("#perder-peso");
    }

    public String obtenerTextoDeBotonGuardar() {
        return this.obtenerTextoDelElemento("button.custom-btn-primary");
    }

    public void darClickEnPerderPeso() {
        this.darClickEnElElemento("#card-perder-peso");
    }

    public void darClickEnGuardar() {
        this.darClickEnElElemento("button.custom-btn-primary");
    }
}
