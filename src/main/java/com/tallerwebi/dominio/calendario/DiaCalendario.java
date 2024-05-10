package com.tallerwebi.dominio.calendario;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class DiaCalendario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "tipoRendimiento", nullable = false)
    private TipoRendimiento tipoRendimiento;

    public DiaCalendario() {
    }

    public DiaCalendario(Long id, LocalDate fecha, TipoRendimiento tipoRendimiento) {
        this.id = id;
        this.fecha = fecha;
        this.tipoRendimiento = tipoRendimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaCalendario that = (DiaCalendario) o;
        return Objects.equals(id, that.id) && Objects.equals(fecha, that.fecha) && tipoRendimiento == that.tipoRendimiento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, tipoRendimiento);
    }

    @Override
    public String toString() {
        return "DiaCalendario{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", tipoRendimiento=" + tipoRendimiento +
                '}';
    }

    public TipoRendimiento getRendimiento() {
        return tipoRendimiento;
    }

    public void setRendimiento(TipoRendimiento tipoRendimiento) {
        this.tipoRendimiento = tipoRendimiento;
    }

}
