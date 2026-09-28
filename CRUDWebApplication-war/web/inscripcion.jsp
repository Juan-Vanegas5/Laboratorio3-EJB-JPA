<%--
    Document   : inscripcion
    Relacion varios estudiantes toman varios cursos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inscripciones</title>
    </head>
    <body>
        <a href="./StudentServlet">Estudiantes</a> |
        <a href="./CursoServlet">Cursos</a> |
        <a href="./InscripcionServlet">Inscripciones</a>
        <h1>Inscripción de estudiantes en cursos</h1>
        <c:if test="${not empty mensaje}"><p><b>${mensaje}</b></p></c:if>
        <form action="./InscripcionServlet" method="POST">
            <table>
                <tr>
                    <td>Estudiante</td>
                    <td>
                        <select name="studentId">
                            <c:forEach items="${allStudents}" var="stud">
                                <option value="${stud.studentId}" ${param.studentId == stud.studentId ? 'selected' : ''}>${stud.studentId} - ${stud.firstname} ${stud.lastname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Curso</td>
                    <td>
                        <select name="codigoCurso">
                            <c:forEach items="${allCursos}" var="cur">
                                <option value="${cur.codigo}" ${param.codigoCurso == cur.codigo ? 'selected' : ''}>${cur.codigo} - ${cur.nombre} (cupos: ${cur.cuposDisponibles})</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="action" value="Inscribir" />
                        <input type="submit" name="action" value="Retirar" />
                    </td>
                </tr>
            </table>
        </form>
        <br>
        <table border="1">
            <th>ID</th>
            <th>Estudiante</th>
            <th>Cursos que toma</th>
            <th>Total créditos</th>
            <c:forEach items="${allStudents}" var="stud">
                <c:set var="totalCreditos" value="0" />
                <tr>
                    <td>${stud.studentId}</td>
                    <td>${stud.firstname} ${stud.lastname}</td>
                    <td>
                        <c:forEach items="${stud.cursos}" var="cur" varStatus="st">
                            <c:set var="totalCreditos" value="${totalCreditos + cur.creditos}" />
                            ${cur.codigo} - ${cur.nombre}<c:if test="${!st.last}">, </c:if>
                        </c:forEach>
                    </td>
                    <td>${totalCreditos}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
