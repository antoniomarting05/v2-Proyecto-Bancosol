<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Entidad" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Zona" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestión de coordinadores</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/campanyas.css">
</head>

<body>
<%
    List<Entidad> listaEntidades = (List<Entidad>) request.getAttribute("entidades");
    List<Zona> listaZonas = (List<Zona>) request.getAttribute("zonas");

    Boolean editando = (Boolean) request.getAttribute("editando");

    if (editando == null) {
        editando = false;
    }

    Integer idCoordinadorActual = (Integer) request.getAttribute("idCoordinador");
    String nombreCoordinadorActual = (String) request.getAttribute("nombreCoordinador");
    String usuarioCoordinadorActual = (String) request.getAttribute("usuarioCoordinador");
    String telefonoCoordinadorActual = (String) request.getAttribute("telefonoCoordinador");
    String emailCoordinadorActual = (String) request.getAttribute("emailCoordinador");

    Integer idEntidadActual = (Integer) request.getAttribute("idEntidadActual");
    Integer idZonaActual = (Integer) request.getAttribute("idZonaActual");
%>

<jsp:include page="../shared/navbar.jsp"/>

<main class="campanya-page">
    <section class="campanya-form-wrapper">
        <div class="campanya-header">
            <div>
                <h2>Datos del <%= editando ? "" : "nuevo" %> coordinador</h2>
                <p>Completa los datos básicos del coordinador.</p>
            </div>
        </div>

        <div id="formularioCoordinador" class="card campanya-form-card">
            <form class="campanya-form" method="post" action="/coordinadores/guardarCoordinador">

                <% if (editando) { %>
                <input type="hidden" name="id" value="<%= idCoordinadorActual %>">
                <% } %>

                <section class="form-section">
                    <h3 class="form-section-title">Información principal</h3>

                    <div class="form-group">
                        <label for="nombre">Coordinador</label>
                        <input id="nombre"
                               type="text"
                               name="nombre"
                               value="<%= editando && nombreCoordinadorActual != null ? nombreCoordinadorActual : "" %>"
                               required
                               placeholder="Nombre del coordinador">
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="usuario">Usuario</label>
                            <input id="usuario"
                                   type="text"
                                   name="usuario"
                                   value="<%= editando && usuarioCoordinadorActual != null ? usuarioCoordinadorActual : "" %>"
                                   required
                                   placeholder="usuario">
                        </div>

                        <div class="form-group">
                            <label for="contrasenya">Contraseña</label>
                            <input id="contrasenya"
                                   type="password"
                                   name="contrasenya"
                                <%= editando ? "" : "required" %>
                                   placeholder="<%= editando ? "Dejar vacío para no cambiarla" : "Contraseña" %>">
                        </div>
                    </div>
                </section>

                <section class="form-section">
                    <h3 class="form-section-title">Datos de contacto</h3>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="telefono">Teléfono</label>
                            <input id="telefono"
                                   type="text"
                                   name="telefono"
                                   value="<%= editando && telefonoCoordinadorActual != null ? telefonoCoordinadorActual : "" %>"
                                   placeholder="Teléfono">
                        </div>

                        <div class="form-group">
                            <label for="email">Correo electrónico</label>
                            <input id="email"
                                   type="email"
                                   name="email"
                                   value="<%= editando && emailCoordinadorActual != null ? emailCoordinadorActual : "" %>"
                                   placeholder="correo@ejemplo.com">
                        </div>
                    </div>
                </section>

                <section class="form-section">
                    <h3 class="form-section-title">Asignación</h3>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="idEntidad">Entidad</label>
                            <select id="idEntidad" name="idEntidad" class="campanya-select">
                                <option value="">Selecciona una entidad</option>

                                <% if (listaEntidades != null) { %>
                                <% for (Entidad entidad : listaEntidades) { %>
                                <option value="<%= entidad.getId() %>"
                                        <%= editando && idEntidadActual != null && idEntidadActual.equals(entidad.getId()) ? "selected" : "" %>>
                                    <%= entidad.getNombre() %>
                                </option>
                                <% } %>
                                <% } %>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="idZona">Área asignada</label>
                            <select id="idZona" name="idZona" class="campanya-select">
                                <option value="">Selecciona un área</option>

                                <% if (listaZonas != null) { %>
                                <% for (Zona zona : listaZonas) { %>
                                <option value="<%= zona.getId() %>"
                                        <%= editando && idZonaActual != null && idZonaActual.equals(zona.getId()) ? "selected" : "" %>>
                                    <%= zona.getNombre() %>
                                </option>
                                <% } %>
                                <% } %>
                            </select>
                        </div>
                    </div>

                </section>

                <section class="form-actions">
                    <% if (editando) { %>
                    <a href="/coordinadores" class="btn-outline">Salir sin guardar</a>
                    <% } else { %>
                    <a href="/coordinadores" class="btn-outline">Cancelar</a>
                    <% } %>

                    <a class="btn-primary">
                        <button type="submit"
                                class="btn-primary"
                                style="font-size: 15px; padding: 0px">
                            Guardar
                        </button>
                    </a>
                </section>

            </form>
        </div>
    </section>
</main>

</body>
</html>
