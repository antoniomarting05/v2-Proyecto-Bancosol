<%--
Antonio Martín García : 100%
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Cadena" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Gestión de Cadenas</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/campanyas.css">
    <link rel="stylesheet" href="/css/cadenas.css">
</head>


<body>
<%
    List<Cadena> listaCadenas = (List<Cadena>) request.getAttribute("cadenasSistema");
    Boolean eliminar = (Boolean) request.getAttribute("eliminar");
%>

<jsp:include page="../shared/navbar.jsp"/>

<main class="main-page">

    <section class="cadena-list-wrapper">
        <form class="cadena-delete-form" action="/campanyas/eliminarCadenasSistema" method="post">

        <div class="cadena-list-header">
            <div class="cadena-list-title">
                <h2>Gestión de Cadenas</h2>
                <p>Modifica, añade u elimina datos de cadenas registradas en el sistema.</p>
            </div>

            <div class="cadena-header-actions">
                <%if (!eliminar){%>
                <a class="btn-outline btn-outline-danger" style="color: #ef4444; border-color: #ef4444; text-decoration: none" href="/campanyas/seleccionCadenasEliminar">Eliminar cadenas</a>
                <a class="cadena-create-btn" href="/campanyas/crearCadena">
                    <span class="cadena-create-icon">+</span>
                    <span>Nueva cadena</span>
                </a>
                <%} else {%>
                <button type="submit" class="btn-outline btn-outline-danger" style="color: #ef4444; border-color: #ef4444; font-size: 17px">Confirmar y eliminar</button>
                <a class="btn-outline" href="/campanyas/gestionarCadenas" style="text-decoration: none">Cancelar selección</a>
                <%};%>
            </div>
        </div>



            <div class="card cadena-table-card">
                <table class="modernTable cadena-table">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Nombre</th>
                        <th>Código</th>
                    </tr>
                    </thead>

                    <tbody>
                    <% for (Cadena cadena : listaCadenas) { %>
                    <tr>
                        <td>
                            <div class="cadena-row-actions">
                                <%if (eliminar){%>
                                <input class="cadena-delete-checkbox" type="checkbox" name="cadenas" value="<%= cadena.getId() %>">
                                <%};%>
                                <a class="cadena-edit-btn" href="/campanyas/editarCadena?id=<%= cadena.getId() %>">
                                    <span class="cadena-edit-icon">✎</span>
                                    <span>Editar</span>
                                </a>

                            </div>
                        </td>
                        <td><%= cadena.getNombre() %></td>
                        <td><%= cadena.getCodigo() %></td>

                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>

        </form>

    </section>
 </main>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const checkboxes = document.querySelectorAll(".cadena-delete-checkbox");

        checkboxes.forEach(function (checkbox) {
            const filaCadena = checkbox.closest("tr");

            function actualizarEstadoSeleccionado() {
                if (checkbox.checked) {
                    filaCadena.classList.add("cadena-row-selected");
                } else {
                    filaCadena.classList.remove("cadena-row-selected");
                }
            }

            actualizarEstadoSeleccionado();
            checkbox.addEventListener("change", actualizarEstadoSeleccionado);
        });
    });
</script>

</body>
</html>