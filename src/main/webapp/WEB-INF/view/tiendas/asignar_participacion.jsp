<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Tienda" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.TiendaCampanya" %>
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Campanya" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Participación en Campañas</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/campanyas.css">
    <link rel="stylesheet" href="/css/asignacion_participacion.css">
</head>
<body>
<%
    Tienda tienda = (Tienda) request.getAttribute("tienda");
    List<TiendaCampanya> tiendasCampanya = (List<TiendaCampanya>) request.getAttribute("tiendasCampanya");
    List<TipoCampanya> tipoCampanyas = (List<TipoCampanya>) request.getAttribute("tipoCampanyas");
    List<Campanya> campanyas = (List<Campanya>) request.getAttribute("campanyas");
%>
<jsp:include page="../shared/navbar.jsp"/>

<main class="main-page">
    <section class="campanya-form-wrapper participacion-wrapper">
        <div class="card campanya-form-card">
            <div class="info-box tienda-info-box">
                <div class="tienda-info-header">
                    <div class="tienda-info-icon">
                        <i class="ri-store-2-fill"></i>
                    </div>
                    <div>
                        <h3 class="tienda-info-title">
                            <%= tienda.getNombre() %>
                        </h3>
                    </div>
                </div>

                <div class="tienda-info-details">
                    <div class="tienda-info-item">
                        <i class="ri-map-pin-2-line tienda-info-item-icon"></i>
                        <span><%= tienda.getDomicilio() != null ? tienda.getDomicilio() : "Domicilio no especificado" %></span>
                    </div>
                    <div class="tienda-info-item">
                        <i class="ri-map-2-line tienda-info-item-icon"></i>
                        <span>C.P. <%= tienda.getCp() != null ? tienda.getCp() : "N/A" %></span>
                    </div>
                </div>
            </div>

            <form method="post" action="/tiendas/guardarParticipacion">
                <input type="hidden" name="idTienda" value="<%= tienda.getId() %>">

                <table class="modernTable participacion-table">
                    <thead>
                    <tr>
                        <% for (TipoCampanya tipo : tipoCampanyas) { %>
                        <th><%= tipo.getNombre() %></th>
                        <% } %>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <% for (TipoCampanya tipo : tipoCampanyas) {
                            Integer campanyaAsignadaId = null;
                            if (tiendasCampanya != null) {
                                for (TiendaCampanya tc : tiendasCampanya) {
                                    if (tc.getCampanya() != null && tc.getCampanya().getTipoCampanya() != null &&
                                            tc.getCampanya().getTipoCampanya().getId().equals(tipo.getId())) {
                                        campanyaAsignadaId = tc.getCampanya().getId();
                                        break;
                                    }
                                }
                            }
                        %>
                        <td>
                            <div class="radio-list">

                                <label class="option-card <%= campanyaAsignadaId == null ? "selected" : "" %>">
                                    <input type="radio" name="tipo_campanya_<%= tipo.getId() %>" value="0" <%= campanyaAsignadaId == null ? "checked" : "" %>>
                                    <span class="default-option-text">No participa</span>
                                </label>

                                <% for (Campanya c : campanyas) {
                                    if (c.getTipoCampanya() != null && c.getTipoCampanya().getId().equals(tipo.getId())) {
                                        boolean isChecked = (campanyaAsignadaId != null && campanyaAsignadaId.equals(c.getId()));
                                %>
                                <label class="option-card <%= isChecked ? "selected" : "" %>">
                                    <input type="radio" name="tipo_campanya_<%= tipo.getId() %>" value="<%= c.getId() %>" <%= isChecked ? "checked" : "" %>>
                                    <span><%= c.getNombre() %></span>
                                </label>
                                <%}}%>
                            </div>
                        </td>
                        <% } %>
                    </tr>
                    </tbody>
                </table>

                <section class="form-actions form-actions-no-border">
                    <a href="/tiendas" class="btn-outline">Cancelar</a>
                    <button type="submit" class="btn-primary btn-save-participacion">Guardar Asignaciones</button>
                </section>
            </form>

        </div>
    </section>
</main>

</body>
</html>