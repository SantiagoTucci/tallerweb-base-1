package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.usuario.ServicioLogin;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.excepcion.NoCambiosRestantesException;
import com.tallerwebi.dominio.reto.Reto;
import com.tallerwebi.dominio.reto.ServicioReto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public ModelAndView empezarReto(@RequestParam Long retoId, @RequestParam String email, @RequestParam String password, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("retoId", retoId); // Añadir retoId al modelo
        try {
            servicioReto.empezarRetoActualizadoPorUsuario(retoId, usuario);

            // Añadir itemMasSeleccionado al modelo
            DatosItemRendimiento itemMasSeleccionado = servicioLogin.obtenerItemMasSeleccionadoPorUsuario(usuario);
            if (itemMasSeleccionado == null) {
                modelAndView.addObject("mensaje", "¿Cómo fue tu entrenamiento hoy?");
            } else {
                modelAndView.addObject("itemMasSeleccionado", itemMasSeleccionado);
            }


            // Añadir retoDisponible al modelo
            Reto retoDisponible = servicioReto.obtenerRetoPorIdPorUsuario(retoId, usuario);
            if (retoDisponible != null) {
                modelAndView.addObject("retoDisponible", retoDisponible);
                long minutosRestantes = servicioReto.calcularTiempoRestantePorUsuario(retoDisponible.getId(), usuario);
                modelAndView.addObject("minutosRestantes", minutosRestantes);
            }

            Usuario usuarioBuscado = servicioLogin.consultarUsuario(email, password);
            if (usuarioBuscado != null) {
                modelAndView.addObject("usuario", usuarioBuscado);
            }

        } catch (NoSuchElementException e) {
            modelAndView.addObject("error", "El reto no existe: " + e.getMessage());
        } catch (RuntimeException e) {
            // Manejar el error mostrando un mensaje de error en la misma página
            modelAndView.addObject("error", "Ocurrió un error al iniciar el reto: " + e.getMessage());
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
            // Añadir itemMasSeleccionado al modelo
            DatosItemRendimiento itemMasSeleccionado = servicioLogin.obtenerItemMasSeleccionadoPorUsuario(usuario);
            if (itemMasSeleccionado == null) {
                modelAndView.addObject("mensaje", "¿Cómo fue tu entrenamiento hoy?");
            } else {
                modelAndView.addObject("itemMasSeleccionado", itemMasSeleccionado);
            }

            session.removeAttribute("retoDisponible");

            Reto retoDisponible = servicioReto.obtenerRetoDisponiblePorUsuario(usuario);
            if (retoDisponible != null) {
                modelAndView.addObject("retoDisponible", retoDisponible);
            }

            Usuario usuarioBuscado = servicioLogin.consultarUsuario(email, password);
            if (usuarioBuscado != null) {
                servicioLogin.modificarRachaRetoTerminadoPorUsuario(usuarioBuscado, retoId);
                modelAndView.addObject("usuario", usuarioBuscado);
                session.setAttribute("usuario", usuarioBuscado);
            }
        } catch (NoSuchElementException e) {
            modelAndView.addObject("error", "El reto no existe: " + e.getMessage());
        } catch (RuntimeException e) {
            modelAndView.addObject("error", "Ocurrió un error al terminar el reto: " + e.getMessage());
        }
        // Redirigir a /home después de la ejecución del método
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @RequestMapping(path = "/cambiar-reto", method = RequestMethod.POST)
    public ModelAndView cambiarReto(@RequestParam Long retoId, @RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            usuario = servicioLogin.consultarUsuario(email, password);
            if (usuario == null) {
                return new ModelAndView("redirect:/login");
            }
        }
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("retoId", retoId);
        try {
            Reto nuevoReto = servicioLogin.cambiarRetoPorUsuario(retoId, usuario);
            if (nuevoReto != null) {
                redirectAttributes.addFlashAttribute("retoDisponible", nuevoReto);
                session.setAttribute("retoDisponible", nuevoReto);
            } else {
                redirectAttributes.addFlashAttribute("error", "No se encontró un nuevo reto disponible.");
            }
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", "No se encontró un nuevo reto disponible: " + e.getMessage());
        } catch (NoCambiosRestantesException e) {
            redirectAttributes.addFlashAttribute("error", "No te quedan cambios. Debes terminar los retos.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al cambiar el reto: " + e.getMessage());
        }

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }









}
