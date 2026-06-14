<%--
Antonio Martín García : 100%
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edición de Cadena</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/campanyas.css">
    <link rel="stylesheet" href="/css/cadenas.css">
</head>

<body>
<%
    String nombreCadena = (String) request.getAttribute("nombreCadena");
    String codigoCadena = (String) request.getAttribute("codigoCadena");

    Boolean editando = (Boolean) request.getAttribute("editando");
    Integer idCadena = (Integer) request.getAttribute("idCadena");
%>

<jsp:include page="../shared/navbar.jsp"/>

<main class="main-page">
    <section class="cadena-form-wrapper">

        <div class="cadena-form-header">
            <div>
                <h2>Datos de la <%= editando ? "" : "nueva" %> cadena</h2>
                <p>Rellenar la información de la cadena</p>
            </div>
        </div>

        <div id="formularioCadena" class="card cadena-form-card">
            <form class="cadena-form" method="post" action="/campanyas/guardarCadena">

                <% if (editando) { %>
                <input type="hidden" name="id" value="<%= idCadena %>">
                <% } %>

                <section class="cadena-form-section">
                    <div class="cadena-form-row">

                        <div class="cadena-form-group">
                            <label for="nombreCadena">Nombre de la cadena</label>
                            <input
                                    id="nombreCadena"
                                    type="text"
                                    name="nombre"
                                    value="<%= editando ? nombreCadena : "" %>"
                                    required
                                    placeholder="Nueva cadena de supermercados">
                        </div>

                        <div class="cadena-form-group">
                            <label for="codigoCadena">Código de la cadena</label>
                            <input
                                    id="codigoCadena"
                                    type="text"
                                    name="codigo"
                                    value="<%= editando ? codigoCadena : "" %>"
                                    required
                                    placeholder="Mercadona -> MERC">
                        </div>

                    </div>
                </section>

                <section class="cadena-form-actions">
                    <% if (editando) { %>
                    <a href="/campanyas/gestionarCadenas" class="btn-outline">Salir sin guardar</a>
                    <% } else { %>
                    <a href="/campanyas/gestionarCadenas" class="btn-outline">Cancelar</a>
                    <% } %>

                    <button type="submit" class="btn-primary">Guardar</button>
                </section>

            </form>
        </div>

    </section>
</main>

</body>
</html>