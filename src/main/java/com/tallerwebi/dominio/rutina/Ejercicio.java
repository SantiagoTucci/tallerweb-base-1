package com.tallerwebi.dominio.rutina;

import com.tallerwebi.dominio.objetivo.GrupoMuscularObjetivo;
import com.tallerwebi.dominio.objetivo.TipoObjetivo;

import javax.persistence.*;

@Entity
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicio;

    private String nombre;

    private String descripcion;
    @Enumerated(EnumType.STRING)
    private GrupoMuscularObjetivo grupoMuscularObjetivo;
    @Enumerated(EnumType.STRING)
    private TipoObjetivo tipoObjetivo;
    @Enumerated(EnumType.STRING)
    private TipoEjercicio tipoEjercicio;

    private Integer series;

    private Integer repeticiones;

    public Ejercicio(String nombre, TipoObjetivo tipoObjetivo, GrupoMuscularObjetivo grupoMuscularObjetivo, Integer series, Integer repeticiones){
        this.nombre = nombre;
        this.tipoObjetivo = tipoObjetivo;
        this.grupoMuscularObjetivo = grupoMuscularObjetivo;
        this.series = series;
        this.repeticiones = repeticiones;
    }
    public Ejercicio(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public GrupoMuscularObjetivo getGrupoMuscularObjetivo() {
        return grupoMuscularObjetivo;
    }

    public void setGrupoMuscularObjetivo(GrupoMuscularObjetivo grupoMuscularObjetivo) {
        this.grupoMuscularObjetivo = grupoMuscularObjetivo;
    }

    public TipoEjercicio getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(TipoEjercicio tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }


    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }


    @Override
    public String toString() {
        return "Ejercicio{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", grupoMuscularObjetivo='" + grupoMuscularObjetivo + '\'' +
                ", tipoEjercicio='" + tipoEjercicio + '\'' +
                ", series=" + series +
                ", repeticiones=" + repeticiones +
                '}';
    }

    public void setIdEjercicio(Long idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Long getIdEjercicio() {
        return idEjercicio;
    }

    public TipoObjetivo getObjetivo() {
        return tipoObjetivo;
    }

    public void setObjetivo(TipoObjetivo tipoObjetivo) {
        this.tipoObjetivo = tipoObjetivo;
    }
}