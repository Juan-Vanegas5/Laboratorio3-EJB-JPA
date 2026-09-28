package com.joseph.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entidad Curso (extension del laboratorio).
 * Columnas: codigo de curso, nombre del curso, numero de creditos, semestre,
 * numero de estudiantes admitidos.
 *
 * Relacion varios estudiantes toman varios cursos: se resuelve con la tabla
 * intermedia STUDENT_CURSO (CODIGOCURSO -> CURSO.CODIGO, STUDENTID -> STUDENT.STUDENTID).
 */
@Entity
@Table(name = "CURSO")
@NamedQueries({@NamedQuery(name = "Curso.getAll", query = "SELECT c FROM Curso c ORDER BY c.semestre, c.codigo")})
public class Curso implements Serializable {

    @Id
    @Column(name = "CODIGO", length = 20)
    private String codigo;
    @Column(name = "NOMBRE", length = 100)
    private String nombre;
    @Column(name = "CREDITOS")
    private int creditos;
    @Column(name = "SEMESTRE")
    private int semestre;
    @Column(name = "ESTUDIANTESADMITIDOS")
    private int estudiantesAdmitidos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "STUDENT_CURSO",
            joinColumns = @JoinColumn(name = "CODIGOCURSO", referencedColumnName = "CODIGO"),
            inverseJoinColumns = @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID"))
    private List<Student> estudiantes = new ArrayList<Student>();

    public Curso() {
    }

    public Curso(String codigo, String nombre, int creditos, int semestre, int estudiantesAdmitidos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.semestre = semestre;
        this.estudiantesAdmitidos = estudiantesAdmitidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getEstudiantesAdmitidos() {
        return estudiantesAdmitidos;
    }

    public void setEstudiantesAdmitidos(int estudiantesAdmitidos) {
        this.estudiantesAdmitidos = estudiantesAdmitidos;
    }

    public List<Student> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Student> estudiantes) {
        this.estudiantes = estudiantes;
    }

    /** Cupos que quedan libres (admitidos - inscritos). */
    public int getCuposDisponibles() {
        return estudiantesAdmitidos - (estudiantes == null ? 0 : estudiantes.size());
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        return this.codigo != null && this.codigo.equals(other.codigo);
    }
}
