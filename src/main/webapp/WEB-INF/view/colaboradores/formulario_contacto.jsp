<%--
Marina Fernández Serrano: 100%
--%>

<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Colaborador" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: marin
  Date: 07/06/2026
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulario Contacto</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/formularioContactos.css">
</head>
<body>
<%
    ContactoColaborador contacto = (ContactoColaborador) request.getAttribute("contacto");
    Colaborador colaborador = (Colaborador) request.getAttribute("colaborador");
    List<Colaborador> colaboradores = (List<Colaborador>) request.getAttribute("colaboradores");
    Boolean editando = (Boolean) request.getAttribute("editando");%>
<jsp:include page="../shared/navbar.jsp"/>
<main class="main-page">
    <section class="form-wrapper">
        <div class="form-header">
            <div>
                <h2>Datos del <%=editando ? "contacto" : "nuevo contacto"%></h2>
                <p>Rellena la información del contacto.</p>
            </div>
        </div>

        <div class="card form-card">
            <form action="/colaboradores/guardarContacto" method="post" class="colaborador-form">
                <%if(editando){%>
                    <input type="hidden" name="id" value="<%=contacto.getId()%>">
                <%}%>

                <section class="form-section">
                    <div class="form-row">
                        <div class="form-group">
                            Es contacto de:
                            <select name="colaborador" >
                                <%for(Colaborador c : colaboradores) { %>
                                    <option value="<%=c.getId()%>" <%=contacto.getColaborador().getId() == c.getId() ? "selected" : ""%>><%=c.getNombre()%></option>
                                <% } %>
                            </select>
                        </div>

                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="nombre">Nombre</label>
                            <input type="text" id="nombre" name="nombre" value="<%=editando ? contacto.getNombre() : ""%>" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="<%=editando ? contacto.getEmail() : ""%>" required>
                        </div>

                        <div class="form-group">
                            <label for="telefono">Telefono</label>
                            <input type="tel" id="telefono" name="telefono" value="<%=editando ? contacto.getTelefono() : ""%>" required>
                        </div>
                    </div>
                </section>

                <section class="form-actions">
                    <a href="/colaboradores/editar?id=<%=colaborador.getId()%>" class="btn-danger-outline">Cancelar</a>
                    <button type="submit" class="btn-primary">Guardar</button>
                </section>
            </form>
        </div>
    </section>
</main>
</body>
</html>
