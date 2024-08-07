package com.tallerwebi.dominio.calendario;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

@Entity
public class ItemRendimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private TipoRendimiento tipoRendimiento;

    @Column(name = "diaNombre", nullable = false)
    private String diaNombre;

    public ItemRendimiento() {
        this.fecha = LocalDate.now();
        this.diaNombre = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
    }

    public ItemRendimiento(TipoRendimiento tipoRendimiento) {
        this.fecha = LocalDate.now();
        this.tipoRendimiento = tipoRendimiento;
        this.diaNombre = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
    }

    public ItemRendimiento(LocalDate fecha, TipoRendimiento tipoRendimiento) {
        this.fecha = fecha;
        this.tipoRendimiento = tipoRendimiento;
        this.diaNombre = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
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
        this.diaNombre = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
    }

    public String getDiaNombre() {
        return diaNombre;
    }

    public void setDiaNombre() {
        if (this.fecha != null) {
            this.diaNombre = this.fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRendimiento that = (ItemRendimiento) o;
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
                ", diaNombre='" + diaNombre + '\'' +
                '}';
    }

    public TipoRendimiento getTipoRendimiento() {
        return tipoRendimiento;
    }

    public void setTipoRendimiento(TipoRendimiento tipoRendimiento) {
        this.tipoRendimiento = tipoRendimiento;
    }
}
