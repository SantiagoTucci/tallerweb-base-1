package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.objetivo.Objetivo;
import com.tallerwebi.dominio.objetivo.ServicioObjetivo;
import com.tallerwebi.dominio.perfil.ServicioPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorObjetivo {

    private ServicioObjetivo servicioObjetivo;

    @Autowired
    public ControladorObjetivo(ServicioObjetivo servicioObjetivo) {
        this.servicioObjetivo = servicioObjetivo;
    }

    @RequestMapping(path = "/objetivo", method = RequestMethod.GET)
    public ModelAndView irObjetivo() {
        ModelAndView modelAndView = new ModelAndView("objetivo");

        List<Objetivo> objetivos = servicioObjetivo.listarObjetivos();
        if (objetivos == null || objetivos.isEmpty()) {
            objetivos = new ArrayList<>(); // Asegurarse de que la lista no sea nula
        }
        modelAndView.addObject("objetivos", objetivos);

        return modelAndView;
    }

}
