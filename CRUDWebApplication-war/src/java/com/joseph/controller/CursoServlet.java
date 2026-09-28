package com.joseph.controller;

import com.joseph.dao.CursoDaoLocal;
import com.joseph.model.Curso;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controlador del componente de manejo de cursos (misma estructura que
 * StudentServlet: Add, Edit, Delete, Search).
 */
@WebServlet(name = "CursoServlet", urlPatterns = {"/CursoServlet"})
public class CursoServlet extends HttpServlet {

    @EJB
    private CursoDaoLocal cursoDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        int creditos = toInt(request.getParameter("creditos"));
        int semestre = toInt(request.getParameter("semestre"));
        int estudiantesAdmitidos = toInt(request.getParameter("estudiantesAdmitidos"));
        if (codigo != null) {
            codigo = codigo.trim().toUpperCase();
        }
        Curso curso = new Curso(codigo, nombre, creditos, semestre, estudiantesAdmitidos);
        String mensaje = null;

        if ("Add".equalsIgnoreCase(action)) {
            mensaje = cursoDao.addCurso(curso)
                    ? "Curso agregado."
                    : "No se pudo agregar: el codigo esta vacio o ya existe.";
        } else if ("Edit".equalsIgnoreCase(action)) {
            cursoDao.editCurso(curso);
        } else if ("Delete".equalsIgnoreCase(action)) {
            cursoDao.deleteCurso(codigo);
        } else if ("Search".equalsIgnoreCase(action)) {
            curso = cursoDao.getCurso(codigo);
            if (curso == null) {
                mensaje = "No existe un curso con codigo " + codigo + ".";
            }
        }
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("curso", curso);
        request.setAttribute("allCursos", cursoDao.getAllCursos());
        request.getRequestDispatcher("cursoinfo.jsp").forward(request, response);
    }

    private int toInt(String valor) {
        if (valor != null && !valor.trim().equals("")) {
            return Integer.parseInt(valor.trim());
        }
        return 0;
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
        return "Manejo de cursos";
    }
}
