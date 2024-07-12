package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.perfil.Perfil;
import com.tallerwebi.dominio.perfil.RepositorioPerfil;
import com.tallerwebi.dominio.reto.RepositorioReto;
import com.tallerwebi.dominio.reto.Reto;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestInfraestructuraConfig.class})
public class RepositorioPerfilTest {

    private RepositorioPerfil repositorioPerfil;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void init() {
        this.repositorioPerfil = new RepositorioPerfilImpl(this.sessionFactory);
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void dadoQueExisteUnPerfilEnLaBaseDeDatosQueSeLogreObtenerPorIdPerfil() {
//        Perfil perfilGuardado = dadoQueExisteUnPerfilEnLaBaseDeDatos();
//        Perfil perfilObtenido = repositorioPerfil.obtenerPerfilPorId(perfilGuardado.getIdPerfil());
//        assertNotNull(perfilObtenido);
//        assertEquals(perfilGuardado.getIdPerfil(), perfilObtenido.getIdPerfil());
//        assertEquals(perfilGuardado.getEdad(), perfilObtenido.getEdad());
//        assertEquals(perfilGuardado.getPeso(), perfilObtenido.getPeso());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void dadoQueSeGuardaUnPerfilEnLaBaseDeDatos() {
//        Perfil perfil = new Perfil();
//        perfil.setEdad(28);
//        perfil.setPeso(65.0);
//        perfil.setAltura(170);
//        perfil.setGenero("Femenino");
//        perfil.setObjetivoFitness("Perder Peso");
//        perfil.setCondicionesAlternas("Asma");
//        perfil.setExperienciaEjercicio("Principiante");
//        perfil.setSuplementos("Ninguno");
//
//        repositorioPerfil.guardarPerfil(perfil);
//        Perfil perfilGuardado = repositorioPerfil.obtenerPerfilPorId(perfil.getIdPerfil());
//        assertNotNull(perfilGuardado);
//        assertEquals(perfil.getEdad(), perfilGuardado.getEdad());
//        assertEquals(perfil.getPeso(), perfilGuardado.getPeso());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void dadoQueSeActualizaUnPerfilEnLaBaseDeDatos() {
//        Perfil perfilGuardado = dadoQueExisteUnPerfilEnLaBaseDeDatos();
//        perfilGuardado.setEdad(35);
//        perfilGuardado.setPeso(75.0);
//        perfilGuardado.setAltura(180);
//        perfilGuardado.setGenero("Masculino");
//        perfilGuardado.setObjetivoFitness("Resistencia");
//        perfilGuardado.setCondicionesAlternas("Diabetes");
//        perfilGuardado.setExperienciaEjercicio("Avanzado");
//        perfilGuardado.setSuplementos("Creatina");
//
//        repositorioPerfil.actualizarPerfil(perfilGuardado);
//        Perfil perfilActualizado = repositorioPerfil.obtenerPerfilPorId(perfilGuardado.getIdPerfil());
//        assertNotNull(perfilActualizado);
//        assertEquals(perfilGuardado.getEdad(), perfilActualizado.getEdad());
//        assertEquals(perfilGuardado.getPeso(), perfilActualizado.getPeso());
//        assertEquals(perfilGuardado.getAltura(), perfilActualizado.getAltura());
//        assertEquals(perfilGuardado.getGenero(), perfilActualizado.getGenero());
//        assertEquals(perfilGuardado.getObjetivoFitness(), perfilActualizado.getObjetivoFitness());
//        assertEquals(perfilGuardado.getCondicionesAlternas(), perfilActualizado.getCondicionesAlternas());
//        assertEquals(perfilGuardado.getExperienciaEjercicio(), perfilActualizado.getExperienciaEjercicio());
//        assertEquals(perfilGuardado.getSuplementos(), perfilActualizado.getSuplementos());
//    }
//
//    private Perfil dadoQueExisteUnPerfilEnLaBaseDeDatos() {
//        Perfil perfil = new Perfil();
//        perfil.setEdad(30);
//        perfil.setPeso(70.0);
//        perfil.setAltura(175);
//        perfil.setGenero("Masculino");
//        perfil.setObjetivoFitness("Ganancia Muscular");
//        perfil.setCondicionesAlternas("Ninguna");
//        perfil.setExperienciaEjercicio("Intermedio");
//        perfil.setSuplementos("Proteína");
//        repositorioPerfil.guardarPerfil(perfil);
//        return perfil;
//    }
}