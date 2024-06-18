package com.tallerwebi.dominio.objetivo;

import com.tallerwebi.dominio.objetivo.TipoObjetivo;
import com.tallerwebi.dominio.rutina.Rutina;
import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.usuarioRutina.UsuarioRutina;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "objetivo")
public class Objetivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObjetivo;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "tipoObjetivo")
    private TipoObjetivo tipoObjetivo;

    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;

    @Column(name = "seleccionado", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean seleccionado = false;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rutina> rutinas;

    public Objetivo() {
    }

    public Objetivo(Long idObjetivo, String nombre, String descripcion, TipoObjetivo tipoObjetivo, LocalDate fechaInicio, Boolean seleccionado) {
        this.idObjetivo = idObjetivo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoObjetivo = tipoObjetivo;
        this.fechaInicio = fechaInicio;
        this.seleccionado = seleccionado;
    }

    public Objetivo(Long idObjetivo, TipoObjetivo tipoObjetivo) {
        this.idObjetivo = idObjetivo;
        this.tipoObjetivo = tipoObjetivo;
    }

    public Objetivo(long l, String perdidaDePeso, String perderPeso, TipoObjetivo tipoObjetivo) {
    }

    public void setIdObjetivo(Long idObjetivo) {
        this.idObjetivo = idObjetivo;
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

    public TipoObjetivo getTipoObjetivo() {
        return tipoObjetivo;
    }

    public void setTipoObjetivo(TipoObjetivo tipoObjetivo) {
        this.tipoObjetivo = tipoObjetivo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objetivo objetivo = (Objetivo) o;
        return Objects.equals(idObjetivo, objetivo.idObjetivo) && Objects.equals(nombre, objetivo.nombre) && Objects.equals(descripcion, objetivo.descripcion) && tipoObjetivo == objetivo.tipoObjetivo && Objects.equals(fechaInicio, objetivo.fechaInicio) && Objects.equals(seleccionado, objetivo.seleccionado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idObjetivo, nombre, descripcion, tipoObjetivo, fechaInicio, seleccionado);
    }
}

