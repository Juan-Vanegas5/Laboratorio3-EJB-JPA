package com.joseph.dao;

import com.joseph.model.Curso;
import java.util.List;
import javax.ejb.Local;

/**
 * Componente de manejo de cursos (extension del laboratorio).
 */
@Local
public interface CursoDaoLocal {

    boolean addCurso(Curso curso);

    void editCurso(Curso curso);

    void deleteCurso(String codigo);

    Curso getCurso(String codigo);

    List<Curso> getAllCursos();

    String inscribirEstudiante(String codigoCurso, int studentId);

    String retirarEstudiante(String codigoCurso, int studentId);
}
