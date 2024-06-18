package com.tallerwebi.dominio.rutina;

import com.tallerwebi.dominio.objetivo.Objetivo;
import com.tallerwebi.dominio.objetivo.TipoObjetivo;
import com.tallerwebi.dominio.usuarioRutina.UsuarioRutina;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRutina;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "objetivo_id", referencedColumnName = "idObjetivo")
    private Objetivo objetivo;

//    @Enumerated(EnumType.STRING)
//    private TipoObjetivo tipoObjetivo;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Ejercicio> ejercicios;

    public Rutina() {
        this.ejercicios = new ArrayList<>();
    }

//    public Rutina(String nombre, TipoObjetivo tipoObjetivo) {
//        this.nombre = nombre;
//        this.tipoObjetivo = tipoObjetivo;
//        this.ejercicios = new ArrayList<>();
//    }


    public Rutina(String nombre, Objetivo objetivo) {
        this.nombre = nombre;
        this.objetivo = objetivo;
    }

    public Rutina(Long idRutina, String nombre, Objetivo objetivo, List<Ejercicio> ejercicios, List<UsuarioRutina> usuarioRutinas) {
        this.idRutina = idRutina;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.ejercicios = ejercicios;
        this.usuarioRutinas = usuarioRutinas;
    }

    public Rutina(String adelgazar, TipoObjetivo tipoObjetivo) {
    }

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
    private List<UsuarioRutina> usuarioRutinas;

    public Long getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(Long idRutina) {
        this.idRutina = idRutina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public List<UsuarioRutina> getUsuarioRutinas() {
        return usuarioRutinas;
    }

    public void setUsuarioRutinas(List<UsuarioRutina> usuarioRutinas) {
        this.usuarioRutinas = usuarioRutinas;
    }

    @Override
    public String toString() {
        return "Rutina{" +
                "nombre='" + nombre + '\'' +
                ", objetivo=" + objetivo +
                ", ejercicios=" + ejercicios +
                '}';
    }


}