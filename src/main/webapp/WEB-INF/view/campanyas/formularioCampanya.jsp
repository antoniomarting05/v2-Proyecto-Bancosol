/*
Antonio Martín García : 100%
*/
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.TipoCampanyaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CampanyaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Cadena" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Gestión de campañas</title>
        <link rel="stylesheet" href="/css/global.css">
        <link rel="stylesheet" href="/css/campanyas.css">
    </head>
    <body>
    <%
        List<Cadena> listaCadenas = (List<Cadena>) request.getAttribute("cadenas");
        List<TipoCampanya> tiposCapanyas = (List<TipoCampanya>) request.getAttribute("tiposCampanya");

        Boolean editando = (Boolean) request.getAttribute("editando");

        String nombreCampanyaActual = (String) request.getAttribute("nombreCampanya");
        Integer idCampanyaActual = (Integer) request.getAttribute("idCampanya");
        Object fechaInicioActual = request.getAttribute("fechaInicio");
        Object fechaFinActual = request.getAttribute("fechaFin");
        TipoCampanya tipoCampanyaActual = (TipoCampanya) request.getAttribute("tipoCampanyaActual");
        List<Cadena> cadenasCampanyaActual = (List<Cadena>) request.getAttribute("cadenasCampanyaActual");

    %>
    <jsp:include page="../shared/navbar.jsp"/>
        <main class="campanya-page">
            <section class="campanya-form-wrapper">
                <div class="campanya-header">
                    <div>
                        <h2>Datos de la <%= editando ? "" : "nueva" %> campaña</h2>
                        <p>Completa los datos básicos y selecciona las cadenas participantes.</p>
                    </div>
                </div>
                <div id="formularioCampanya" class="card campanya-form-card">
                    <form class="campanya-form" method="post" action="/campanyas/guardarCampanya">
                        <% if (editando) { %>
                            <input type="hidden" name="id" value="<%= idCampanyaActual %>">
                        <% } %>
                        <section class="form-section">
                            <h3 class="form-section-title">Información principal</h3>
                            <div class="form-group">
                                <label for="nombreCampanya">Nombre de la Campaña</label>
                                <input id="nombreCampanya" type="text" name="nombreCampanya" value="<%= editando ? nombreCampanyaActual : "" %>" required placeholder="Nueva campaña">
                            </div>
                        </section>
                        <section class="form-section">
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="fechaInicio">Fecha inicio</label>
                                    <input id="fechaInicio" type="date" name="fechaInicio" value="<%= editando && fechaInicioActual != null ? fechaInicioActual : "" %>" required>
                                </div>
                                <div class="form-group">
                                    <label for="fechaFin">Fecha fin</label>
                                    <input id="fechaFin" type="date" name="fechaFin" value="<%= editando && fechaFinActual != null ? fechaFinActual : "" %>" required>
                                </div>
                            </div>
                        </section>
                        <section class="form-section">
                            <h3 class="form-section-title">Tipo de Campaña</h3>

                            <div class="form-group">
                                <select id="tipoCampanya" name="tipoCampanya" class="campanya-select" required>
                                    <option value="">Selecciona un tipo</option>

                                    <% for (TipoCampanya t : tiposCapanyas) { %>
                                    <option value="<%= t.getId() %>"
                                            <%= editando && tipoCampanyaActual != null && tipoCampanyaActual.getId().equals(t.getId()) ? "selected" : "" %>>
                                        <%= t.getNombre() %>
                                    </option>
                                    <% }; %>
                                </select>
                            </div>
                        </section>
                        <section class="form-section">
                            <h3 class="form-section-title">Cadenas participantes</h3>
                            <div class="info-box">
                                Selecciona las cadenas que participarán en esta campaña.
                            </div>
                            <div class="checkbox-grid">
                                <% for (Cadena c: listaCadenas){%>
                                    <label class="checkbox-card">
                                        <input type="checkbox" name="cadenaParticipa" value="<%=c.getId()%>" <%= editando && cadenasCampanyaActual != null && cadenasCampanyaActual.stream().anyMatch(cad -> cad.getId().equals(c.getId())) ? "checked" : ""%>>
                                        <span><%= c.getNombre()%></span>
                                    </label>
                                <%};%>
                            </div>

                            <section class="cadenas-btns">

                            </section>
                        </section>
                        <section class="form-actions">
                            <%if (editando){%>
                                <a href="/campanyas" class="btn-outline">Salir sin guardar</a>
                            <%} else {%>
                                <a href="/campanyas" class="btn-outline">Cancelar</a>
                           <%};%>
                            <a class="btn-primary">
                                <button type="submit" class="btn-primary" style="font-size: 15px ; padding: 0px" >Guardar</button>
                            </a>
                        </section>

                    </form>
                </div>
            </section>
        </main>
    </body>
</html>