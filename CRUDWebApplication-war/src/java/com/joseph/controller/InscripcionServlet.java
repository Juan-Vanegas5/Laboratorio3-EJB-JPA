package com.joseph.controller;

import com.joseph.dao.CursoDaoLocal;
import com.joseph.dao.StudentDaoLocal;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Relaciona estudiantes con cursos (varios estudiantes toman varios cursos).
 */
@WebServlet(name = "InscripcionServlet", urlPatterns = {"/InscripcionServlet"})
public class InscripcionServlet extends HttpServlet {

    @EJB
    private StudentDaoLocal studentDao;
    @EJB
    private CursoDaoLocal cursoDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String codigoCurso = request.getParameter("codigoCurso");
        String studentIdStr = request.getParameter("studentId");
        int studentId = 0;
        if (studentIdStr != null && !studentIdStr.equals("")) {
            studentId = Integer.parseInt(studentIdStr);
        }
        String mensaje = null;

        if ("Inscribir".equalsIgnoreCase(action)) {
            mensaje = cursoDao.inscribirEstudiante(codigoCurso, studentId);
        } else if ("Retirar".equalsIgnoreCase(action)) {
            mensaje = cursoDao.retirarEstudiante(codigoCurso, studentId);
        }
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("allStudents", studentDao.getAllStudents());
        request.setAttribute("allCursos", cursoDao.getAllCursos());
        request.getRequestDispatcher("inscripcion.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Inscripcion de estudiantes en cursos";
    }
}
