package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NoCambiosRestantesException;
import com.tallerwebi.dominio.reto.Reto;
import com.tallerwebi.dominio.reto.ServicioReto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
public class ControladorReto {

    @Autowired
    private ServicioReto servicioReto;

    @Autowired
    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorReto(ServicioReto servicioReto, ServicioLogin servicioLogin) {this.servicioReto = servicioReto;
        this.servicioLogin = servicioLogin;
    }


    @RequestMapping(path = "/empezar-reto", method = RequestMethod.POST)
    public ModelAndView empezarReto(@RequestParam Long retoId,@RequestParam String email, @RequestParam String password, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("retoId", retoId); // Añadir retoId al modelo
        try {
            servicioReto.empezarRetoActualizado(retoId);

            // Añadir itemMasSeleccionado al modelo
            DatosItemRendimiento itemMasSeleccionado = servicioLogin.obtenerItemMasSeleccionado();
            modelAndView.addObject("itemMasSeleccionado", itemMasSeleccionado);

            // Añadir retoDisponible al modelo
            Reto retoDisponible = servicioReto.obtenerRetoPorId(retoId);
            if (retoDisponible != null) {
                modelAndView.addObject("retoDisponible", retoDisponible);
                long minutosRestantes = servicioReto.calcularTiempoRestante(retoDisponible.getId());
                modelAndView.addObject("minutosRestantes", minutosRestantes);
            }


            Usuario usuarioBuscado = servicioLogin.consultarUsuario(email, password);
            if (usuarioBuscado != null) {
                modelAndView.addObject("usuario", usuarioBuscado);
            }

        } catch (Exception e) {
            // Manejar el error mostrando un mensaje de error en la misma página
            modelAndView.addObject("error", "An error occurred while starting the challenge: " + e.getMessage());
        }
        return modelAndView;
    }



    @RequestMapping(path = "/terminar-reto", method = RequestMethod.GET)
    public ModelAndView terminarReto(@RequestParam Long retoId, @RequestParam String email, @RequestParam String password, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("retoId", retoId);
        modelAndView.addObject("usuario", usuario); // Asegúrate de agregar el usuario al modelo
        try {
            DatosItemRendimiento itemMasSeleccionado = servicioLogin.obtenerItemMasSeleccionado();
            modelAndView.addObject("itemMasSeleccionado", itemMasSeleccionado);

            Reto retoDisponible = servicioReto.obtenerRetoDisponible();
            if (retoDisponible != null) {
                modelAndView.addObject("retoDisponible", retoDisponible);
            }

            Usuario usuarioBuscado = servicioLogin.consultarUsuario(email, password);
            if (usuarioBuscado != null) {
                servicioLogin.modificarRachaRetoTerminado(usuarioBuscado, retoId);
                modelAndView.addObject("usuario", usuarioBuscado);
                session.setAttribute("usuario", usuarioBuscado);
            }
        } catch (Exception e) {
            modelAndView.addObject("error", "An error occurred while finishing the challenge: " + e.getMessage());
        }

        // Redirigir a /home después de la ejecución del método
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }


    @RequestMapping(path = "/cambiar-reto", method = RequestMethod.POST)
    public ModelAndView cambiarReto(@RequestParam Long retoId, @RequestParam String email, @RequestParam String password, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("retoId", retoId);

        try {
            // Obtener el item más seleccionado
            DatosItemRendimiento itemMasSeleccionado = servicioLogin.obtenerItemMasSeleccionado();
            modelAndView.addObject("itemMasSeleccionado", itemMasSeleccionado);

            // Consultar al usuario
            Usuario usuarioBuscado = servicioLogin.consultarUsuario(email, password);
            if (usuarioBuscado == null) {
                throw new NoSuchElementException("Usuario no encontrado.");
            }
            modelAndView.addObject("usuario", usuarioBuscado);
            // Cambiar el reto
            Reto nuevoReto = servicioLogin.cambiarReto(retoId, usuarioBuscado);

            if (nuevoReto != null) {
                modelAndView.addObject("retoDisponible", nuevoReto);
            } else {
                throw new NoSuchElementException("No se encontró un nuevo reto.");
            }

        } catch (NoCambiosRestantesException e) {
            modelAndView.addObject("error", e.getMessage());
        } catch (Exception e) {
            modelAndView.addObject("error", "Ocurrió un error inesperado: " + e.getMessage());
        }

        return modelAndView;
    }





}
