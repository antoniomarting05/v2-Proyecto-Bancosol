<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Colaborador" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador" %><%--
  Created by IntelliJ IDEA.
  User: marin
  Date: 25/05/2026
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulario Colaborador</title>
    <link rel="stylesheet" href="/css/global.css" />
    <link rel="stylesheet" href="/css/formularioColaboradores.css">
</head>
<body>
    <%Colaborador colaborador = (Colaborador) request.getAttribute("colaborador");
    Boolean editando = (Boolean) request.getAttribute("editando");%>
    <jsp:include page="../shared/navbar.jsp"/>
    <main class="main-page">
        <section class="form-wrapper">
            <div class="form-header">
                <div>
                    <h2>Datos del <%=editando ? "colaborador" : "nuevo colaborador"%></h2>
                    <p>Rellena la información del colaborador.</p>
                </div>
            </div>

            <div class="card form-card">
                <form action="/colaboradores/guardar" method="post" class="colaborador-form">
                    <%if(editando){%>
                        <input type="hidden" name="id" value="<%=colaborador.getId()%>">
                    <%}%>

                    <section class="form-section">
                        <div class="form-row">
                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" id="nombre" name="nombre" value="<%=editando ? colaborador.getNombre() : ""%>" required>
                            </div>
                            <div class="form-group">
                                <label for="codigo">Código</label>
                                <input type="text" id="codigo" name="codigo" value="<%=editando ? colaborador.getCodigo() : ""%>" required>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="domicilio">Domicilio</label>
                                <input type="text" id="domicilio" name="domicilio" value="<%=editando? colaborador.getDomicilio() : ""%>">
                            </div>
                            <div class="form-group">
                                <label for="cp">Código Postal</label>
                                <input type="text" id="cp" name="cp" value="<%= editando ? colaborador.getCp() : ""%>">
                            </div>
                        </div>

                        <div class="form-group contact-span">
                            <p>Contactos</p>
                            <table class="contact-table">
                                <thead>
                                    <tr>
                                        <th>Nombre</th>
                                        <th class="columna-centrada">Contacto Principal</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%if (editando) {
                                        for (ContactoColaborador c : colaborador.getContactos()) {%>
                                        <tr>
                                            <td><%=c.getNombre()%></td>
                                            <td class="columna-centrada">
                                                <input type="radio" name="contactoPrincipal" value="<%=c.getNombre()%>" <%=c.getNombre().equals(colaborador.getContactoPrincipal()) ? "checked" : ""%>>
                                            </td>
                                            <td class="columna-centrada">
                                                <a class="btn-outline" href="/colaboradores/editarContacto?id=<%=c.getId()%>"> Editar </a>
                                                <a class="btn-danger-outline" href="/colaboradores/eliminarContacto?id=<%=c.getId()%>"> Eliminar</a>
                                            </td>
                                        </tr>
                                    <%
                                            }
                                        }
                                    %>
                                <tr>
                                    <td class="anadir-contacto">
                                        <a href="/colaboradores/anadirContacto?id=<%=colaborador.getId()%>"> + Añadir contacto</a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                        </div>

                        <div class="form-group">
                            <label for="observaciones">Observaciones</label>
                            <input type="text" id="observaciones" name="observaciones" value="<%= editando ? (colaborador.getObservaciones() != null ? colaborador.getObservaciones() : "" ) : ""%>">
                        </div>
                    </section>

                    <section class="form-actions">
                        <a href="/colaboradores" class="btn-danger-outline">Cancelar</a>
                        <button type="submit" class="btn-primary">Guardar</button>
                    </section>
                </form>
            </div>
        </section>
    </main>
</body>
</html>
