<%--
Marina Fernández Serrano: 100%
--%>

<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Colaborador" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador" %><%--
  Created by IntelliJ IDEA.
  User: marin
  Date: 29/04/2026
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/colaboradores.css" />
</head>
<body>
<%Colaborador colaborador = (Colaborador) request.getAttribute("colaborador");
ContactoColaborador contactoPrincipal = (ContactoColaborador) request.getAttribute("contactoPrincipal");%>

<div id="colaborador-container">
    <div id="colaborador-localization">
        <p id="lbl-colaborador"><%=colaborador.getNombre()%></p>
        <p id="lbl-domicilio"><%=colaborador.getDomicilio()%></p>
        <p class="text-muted"><%=colaborador.getCodigo()%>, <%=colaborador.getLocalidadSede().getNombre()%></p>
        <p class="text-muted">Colabora en: <%=colaborador.getColaboraEn().getNombre()%></p>
    </div>
    <div id="colaborador-schedule">
        <div id="contactosCard">
                <div class="info-card">
                    <div class="info-header">
                        <div class="info-main">
                            <p class="lbl-capitan"><%=contactoPrincipal.getNombre()%></p>
                        </div>
                        <div class="info-side">
                            <div>TELÉFONO</div>
                            <div><%=contactoPrincipal.getTelefono() != null ? contactoPrincipal.getTelefono() : "--"%></div>
                        </div>
                    </div>
                    <div class="info-body">
                        <p><strong>Email: </strong><%=contactoPrincipal.getEmail() != null ? contactoPrincipal.getEmail() : "--"%></p>
                    </div>
                </div>
        </div>
        <div id="colaborador-observaciones">
            <p class="section-title">Observaciones</p>
            <div class="info-card">
                <div class="info-body">
                    <p><%=colaborador.getObservaciones() != null && !colaborador.getObservaciones().isEmpty() ? colaborador.getObservaciones() : "Sin observaciones"%></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
