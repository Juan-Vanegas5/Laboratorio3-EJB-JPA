/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joseph.dao;

import com.joseph.model.Curso;
import com.joseph.model.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Joseph
 */
@Stateless
public class StudentDao implements StudentDaoLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addStudent(Student student) {
        em.persist(student);
    }

    @Override
    public void editStudent(Student student) {
        // Se conservan los cursos ya inscritos para que el merge no los borre
        Student actual = getStudent(student.getStudentId());
        if (actual != null) {
            student.setCursos(actual.getCursos());
        }
        em.merge(student);
    }

    @Override
    public void deleteStudent(int studentId) {
        Student student = getStudent(studentId);
        if (student != null) {
            // Primero se retira de sus cursos (filas de STUDENT_CURSO)
            for (Curso curso : student.getCursos()) {
                curso.getEstudiantes().remove(student);
            }
            em.remove(student);
        }
    }

    @Override
    public Student getStudent(int studentId) {
        return em.find(Student.class, studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        return em.createNamedQuery("Student.getAll").getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
