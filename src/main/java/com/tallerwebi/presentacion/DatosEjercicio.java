package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.objetivo.GrupoMuscularObjetivo;
import com.tallerwebi.dominio.objetivo.TipoObjetivo;
import com.tallerwebi.dominio.rutina.Ejercicio;

public class DatosEjercicio {

    private String nombre;

    private TipoObjetivo tipoObjetivo;

    private GrupoMuscularObjetivo grupoMuscularObjetivo;

    private Integer repeticiones;

    private Integer series;

    private String descripcion;


    public DatosEjercicio(Ejercicio ejercicio){
        this.nombre = ejercicio.getNombre();
        this.tipoObjetivo = ejercicio.getObjetivo();
        this.repeticiones = ejercicio.getRepeticiones();
        this.series = ejercicio.getSeries();
        this.descripcion = ejercicio.getDescripcion();
        this.grupoMuscularObjetivo = ejercicio.getGrupoMuscularObjetivo();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoObjetivo getObjetivo() {
        return tipoObjetivo;
    }

    public void setObjetivo(TipoObjetivo tipoObjetivo) {
        this.tipoObjetivo = tipoObjetivo;
    }

    public GrupoMuscularObjetivo getGrupoMuscularObjetivo() {
        return grupoMuscularObjetivo;
    }

    public void setGrupoMuscularObjetivo(GrupoMuscularObjetivo grupoMuscularObjetivo) {
        this.grupoMuscularObjetivo = grupoMuscularObjetivo;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
