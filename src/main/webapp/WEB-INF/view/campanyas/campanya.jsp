/*
Antonio Martín García : 100%
*/
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Campanya" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Cadena" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Gestión de Campañas</title>
        <link rel="stylesheet" href="/css/global.css">
        <link rel="stylesheet" href="/css/campanyas.css">
        <link rel="stylesheet" href="/css/cadenas.css">
    </head>
    <body>
    <%
        List<Campanya> listaCampanyas = (List<Campanya>) request.getAttribute("campanyas");
        Boolean eliminar = (Boolean) request.getAttribute("eliminar");
    %>

    <jsp:include page="../shared/navbar.jsp"/>

    <main class="main-page">
        <form action="/campanyas/eliminarCampanyas" method="post">
            <section class="campanya-list-wrapper">
                <div class="campanya-header">
                    <div>
                        <h1>Gestión de Campañas</h1>
                        <p>Consulta las campañas creadas y genera nuevas campañas.</p>
                    </div>
                    <div class="campanya-list-actions">
                        <%if (!eliminar){%>
                        <a class="btn-outline" href="/campanyas/gestionarCadenas">Gestionar Cadenas</a>
                        <a class="btn-outline btn-outline-danger" style="color: #ef4444; border-color: #ef4444;" href="/campanyas/seleccionCampanyasEliminar">Eliminar campañas</a>
                        <a href="/campanyas/crearCampanya" class="btn-primary">
                            <span class="cadena-create-icon">+</span>
                            <span> Nueva Campaña</span>
                        </a>
                        <%} else {%>
                        <button type="submit" class="btn-outline btn-outline-danger" style="color: #ef4444; border-color: #ef4444; font-size: 17px; ">Confirmar selección y borrar</button>
                        <a class="btn-outline" href="/campanyas">Cancelar selección</a>

                        <%};%>

                    </div>
                </div>


                <div class="card campanya-table-card">
                    <table class="modernTable">
                        <thead>
                        <tr>
                            <th></th>
                            <th>Tipo de campaña</th>
                            <th>Nombre</th>
                            <th>Fecha inicio</th>
                            <th>Fecha fin</th>
                            <th>Cadenas participantes</th>

                        </tr>
                        </thead>
                        <tbody>
                        <% for(Campanya campanya : listaCampanyas) { %>
                        <tr>
                            <td>
                                <div class="campanya-row-actions">
                                    <%if (eliminar){%>
                                    <input class="campanya-delete-checkbox" type="checkbox" name="campanyasElim" value="<%=campanya.getId()%>">
                                    <%};%>
                                    <a class="edit-campanya-btn" href="/campanyas/editarCampanya?id=<%=campanya.getId()%>">
                                        <span class="edit-campanya-icon">✎</span>
                                        <span>Editar</span>
                                    </a>
                                </div>

                            </td>
                            <td><%= campanya.getTipoCampanya().getNombre()%></td>
                            <td><%= campanya.getNombre() %></td>
                            <td><%= campanya.getFechaInicio().toString() %></td>
                            <td><%= campanya.getFechaFin().toString() %></td>
                            <td>
                                <% for (Cadena c: campanya.getCadenasParticipantes()){%>
                                <span class="cadena-chip"><%=c.getNombre()%></span>
                                <%};%>
                                <%=campanya.getCadenasParticipantes().isEmpty() ? "Sin cadenas participantes" : ""%>
                            </td>

                        </tr>
                        <% }; %>
                        </tbody>
                    </table>
                </div>
            </section>
        </form>

    </main>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const checkboxes = document.querySelectorAll(".campanya-delete-checkbox");

            checkboxes.forEach(function (checkbox) {
                const filaCampanya = checkbox.closest("tr");

                function actualizarEstadoSeleccionado() {
                    if (checkbox.checked) {
                        filaCampanya.classList.add("campanya-row-selected");
                    } else {
                        filaCampanya.classList.remove("campanya-row-selected");
                    }
                }

                actualizarEstadoSeleccionado();
                checkbox.addEventListener("change", actualizarEstadoSeleccionado);
            });
        });
    </script>

    </body>
</html>