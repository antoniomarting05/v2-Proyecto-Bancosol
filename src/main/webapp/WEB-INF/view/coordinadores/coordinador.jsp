<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Usuario" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestión de Coordinadores</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/campanyas.css">
    <link rel="stylesheet" href="/css/cadenas.css">
</head>
<body>
<%
    List<Usuario> listaCoordinadores = (List<Usuario>) request.getAttribute("coordinadores");
    Boolean eliminar = (Boolean) request.getAttribute("eliminar");

    if (eliminar == null) {
        eliminar = false;
    }
%>

<jsp:include page="../shared/navbar.jsp"/>

<main class="main-page">
    <form action="/coordinadores/eliminarCoordinadores" method="post">
        <section class="campanya-list-wrapper coordinadores-list-wrapper">
            <div class="campanya-header">
                <div>
                    <h1>Gestión de Coordinadores</h1>
                    <p>Consulta los coordinadores registrados y añade nuevos coordinadores.</p>
                </div>

                <div class="campanya-list-actions">
                    <% if (!eliminar) { %>
                    <a class="btn-outline btn-outline-danger"
                       style="color: #ef4444; border-color: #ef4444;"
                       href="/coordinadores/seleccionCoordinadoresEliminar">
                        Eliminar coordinadores
                    </a>

                    <a href="/coordinadores/crearCoordinador" class="btn-primary">
                        <span class="cadena-create-icon">+</span>
                        <span> Nuevo Coordinador</span>
                    </a>
                    <% } else { %>
                    <button type="submit"
                            class="btn-outline btn-outline-danger"
                            style="color: #ef4444; border-color: #ef4444; font-size: 17px;">
                        Confirmar selección y borrar
                    </button>

                    <a class="btn-outline" href="/coordinadores">Cancelar selección</a>
                    <% } %>
                </div>
            </div>

            <div class="card campanya-table-card coordinadores-table-card">
                <table class="modernTable coordinadores-table">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Coordinador</th>
                        <th>Entidad</th>
                        <th>Área asignada</th>
                        <th>Teléfono</th>
                        <th>Correo electrónico</th>
                        <th>Tiendas</th>
                        <th>Usuario</th>
                    </tr>
                    </thead>

                    <tbody>
                    <% if (listaCoordinadores != null && !listaCoordinadores.isEmpty()) { %>
                    <% for (Usuario coordinador : listaCoordinadores) { %>
                    <tr>
                        <td>
                            <div class="campanya-row-actions">
                                <% if (eliminar) { %>
                                <input class="campanya-delete-checkbox"
                                       type="checkbox"
                                       name="coordinadoresElim"
                                       value="<%= coordinador.getId() %>">
                                <% } %>

                                <a class="edit-campanya-btn"
                                   href="/coordinadores/editarCoordinador?id=<%= coordinador.getId() %>">
                                    <span class="edit-campanya-icon">✎</span>
                                    <span>Editar</span>
                                </a>
                            </div>
                        </td>

                        <td><%= coordinador.getNombre() != null ? coordinador.getNombre() : "" %></td>
                        <td><%= coordinador.getEntidad() != null ? coordinador.getEntidad() : "Sin entidad" %></td>
                        <td><%= coordinador.getZonaAsignada() != null ? coordinador.getZonaAsignada() : "Sin área" %></td>
                        <td><%= coordinador.getTelefono() != null ? coordinador.getTelefono() : "" %></td>
                        <td><%= coordinador.getEmail() != null ? coordinador.getEmail() : "" %></td>
                        <td><%= coordinador.getTiendasCoordinadas() != null ? coordinador.getTiendasCoordinadas().size() : 0 %></td>
                        <td><%= coordinador.getUsuario() != null ? coordinador.getUsuario() : "" %></td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr>
                        <td colspan="9">No hay coordinadores registrados.</td>
                    </tr>
                    <% } %>
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
            const filaCoordinador = checkbox.closest("tr");

            function actualizarEstadoSeleccionado() {
                if (checkbox.checked) {
                    filaCoordinador.classList.add("campanya-row-selected");
                } else {
                    filaCoordinador.classList.remove("campanya-row-selected");
                }
            }

            actualizarEstadoSeleccionado();
            checkbox.addEventListener("change", actualizarEstadoSeleccionado);
        });
    });
</script>

</body>
</html>
