package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaAlimentacion extends VistaWeb {

    public VistaAlimentacion(Page page) {
        super(page);
        page.navigate("localhost:8081/spring/alimentacion");
    }

    public String obtenerTextoDeLaBarraDeNavegacion() {
        return this.obtenerTextoDelElemento("h1.logo-text");
    }

    public String obtenerTextoDelTitulo() {
        return this.obtenerTextoDelElemento("p.title");
    }

    public String obtenerTextoDeDescripcion() {
        return this.obtenerTextoDelElemento("#p-descripcion");
    }
}

