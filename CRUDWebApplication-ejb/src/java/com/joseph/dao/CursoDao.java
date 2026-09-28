package com.joseph.dao;

import com.joseph.model.Curso;
import com.joseph.model.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB de sesion sin estado para el manejo de cursos y de la relacion
 * estudiantes <-> cursos (varios a varios).
 */
@Stateless
public class CursoDao implements CursoDaoLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean addCurso(Curso curso) {
        if (curso.getCodigo() == null || curso.getCodigo().trim().isEmpty()
                || getCurso(curso.getCodigo()) != null) {
            return false;
        }
        em.persist(curso);
        return true;
    }

    @Override
    public void editCurso(Curso curso) {
        Curso actual = getCurso(curso.getCodigo());
        if (actual != null) {
            // Se actualizan solo los datos del curso, no los inscritos
            actual.setNombre(curso.getNombre());
            actual.setCreditos(curso.getCreditos());
            actual.setSemestre(curso.getSemestre());
            actual.setEstudiantesAdmitidos(curso.getEstudiantesAdmitidos());
        }
    }

    @Override
    public void deleteCurso(String codigo) {
        Curso curso = getCurso(codigo);
        if (curso != null) {
            for (Student student : curso.getEstudiantes()) {
                student.getCursos().remove(curso);
            }
            em.remove(curso);
        }
    }

    @Override
    public Curso getCurso(String codigo) {
        if (codigo == null) {
            return null;
        }
        return em.find(Curso.class, codigo);
    }

    @Override
    public List<Curso> getAllCursos() {
        return em.createNamedQuery("Curso.getAll").getResultList();
    }

    @Override
    public String inscribirEstudiante(String codigoCurso, int studentId) {
        Curso curso = getCurso(codigoCurso);
        Student student = em.find(Student.class, studentId);
        if (curso == null || student == null) {
            return "Debe seleccionar un estudiante y un curso existentes.";
        }
        if (curso.getEstudiantes().contains(student)) {
            return "El estudiante ya esta inscrito en " + curso.getNombre() + ".";
        }
        if (curso.getCuposDisponibles() <= 0) {
            return "El curso " + curso.getNombre() + " no tiene cupos disponibles.";
        }
        // Se actualizan los dos lados de la relacion
        curso.getEstudiantes().add(student);
        student.getCursos().add(curso);
        return "Estudiante " + student.getFirstname() + " inscrito en " + curso.getNombre() + ".";
    }

    @Override
    public String retirarEstudiante(String codigoCurso, int studentId) {
        Curso curso = getCurso(codigoCurso);
        Student student = em.find(Student.class, studentId);
        if (curso == null || student == null || !curso.getEstudiantes().contains(student)) {
            return "El estudiante no esta inscrito en ese curso.";
        }
        curso.getEstudiantes().remove(student);
        student.getCursos().remove(curso);
        return "Estudiante " + student.getFirstname() + " retirado de " + curso.getNombre() + ".";
    }
}
