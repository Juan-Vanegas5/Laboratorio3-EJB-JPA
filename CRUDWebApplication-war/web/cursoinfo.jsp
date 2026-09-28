<%--
    Document   : cursoinfo
    Componente de manejo de cursos (extension del laboratorio)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Course Information</title>
    </head>
    <body>
        <a href="./StudentServlet">Estudiantes</a> |
        <a href="./CursoServlet">Cursos</a> |
        <a href="./InscripcionServlet">Inscripciones</a>
        <h1>Course Information</h1>
        <c:if test="${not empty mensaje}"><p><b>${mensaje}</b></p></c:if>
        <form action="./CursoServlet" method="POST">
            <table>
                <tr>
                    <td>Código del curso</td>
                    <td><input type="text" name="codigo" value="${curso.codigo}" /></td>
                </tr>
                <tr>
                    <td>Nombre del curso</td>
                    <td><input type="text" name="nombre" value="${curso.nombre}" /></td>
                </tr>
                <tr>
                    <td>Número de créditos</td>
                    <td><input type="text" name="creditos" value="${curso.creditos}" /></td>
                </tr>
                <tr>
                    <td>Semestre</td>
                    <td><input type="text" name="semestre" value="${curso.semestre}" /></td>
                </tr>
                <tr>
                    <td>Número de estudiantes admitidos</td>
                    <td><input type="text" name="estudiantesAdmitidos" value="${curso.estudiantesAdmitidos}" /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="action" value="Add" />
                        <input type="submit" name="action" value="Edit" />
                        <input type="submit" name="action" value="Delete" />
                        <input type="submit" name="action" value="Search" />
                    </td>
                </tr>
            </table>
        </form>
        <br>
        <table border="1">
            <th>Código</th>
            <th>Nombre</th>
            <th>Créditos</th>
            <th>Semestre</th>
            <th>Admitidos</th>
            <th>Inscritos</th>
            <th>Estudiantes</th>
            <c:forEach items="${allCursos}" var="cur">
                <tr>
                    <td>${cur.codigo}</td>
                    <td>${cur.nombre}</td>
                    <td>${cur.creditos}</td>
                    <td>${cur.semestre}</td>
                    <td>${cur.estudiantesAdmitidos}</td>
                    <td>${cur.estudiantes.size()}</td>
                    <td>
                        <c:forEach items="${cur.estudiantes}" var="stud" varStatus="st">
                            ${stud.firstname} ${stud.lastname}<c:if test="${!st.last}">, </c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
