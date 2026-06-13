<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Turno" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.TiendaCampanya" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer turno = (Integer) request.getAttribute("turno");
    Integer lineales = (Integer) request.getAttribute("lineales");
    Integer linealActual = (Integer) request.getAttribute("linealActual");
    TiendaCampanya tienda = (TiendaCampanya) request.getAttribute("tienda");
    Turno asignacionTurno = (Turno) request.getAttribute("asignacionTurno");
    Usuario usuario = (Usuario) request.getAttribute("user");


%>
<div id="volunteer-container">
    <div id="volunteer-localization">
        <div><p id="lbl-tienda"><%= (tienda != null && tienda.getTienda() != null) ? tienda.getTienda().getNombre() : "Seleccione una tienda" %></p></div>
        <div><p id="lbl-domicilio"><%= tienda.getTienda().getDomicilio() %></p></div>
    </div>
    <div id="volunteer-schedule">
        <label for="viernes_manana">
            <input type="radio" id="viernes_manana" name="schedule" value="1" <%= turno == 1 ? "checked" : "" %> />
            Viernes Mañana
        </label>
        <label for="viernes_tarde">
            <input type="radio" id="viernes_tarde" name="schedule" value="2" <%= turno == 2 ? "checked" : "" %> />
            Viernes Tarde
        </label>
        <label for="sabado_manana">
            <input type="radio" id="sabado_manana" name="schedule" value="3" <%= turno == 3 ? "checked" : "" %> />
            Sabado Mañana
        </label>
        <label for="sabado_tarde">
            <input type="radio" id="sabado_tarde" name="schedule" value="4" <%= turno == 4 ? "checked" : "" %> />
            Sabado Tarde
        </label>
    </div>
    <%
        if (lineales > 1) {
    %>

    <div id="volunteer-lineal">
        <%
            for (int i = 1; i <= lineales; i++) {
        %>
        <label for="L<%= i %>">
            <input type="radio" id="L<%= i %>" name="lineal" value="<%=i%>" <%= linealActual == i ? "checked" : "" %> />
            L<%=i%>
        </label>
        <%
            }
        %>
    </div>
    <%
        }
    %>
    <div id="volunteer-info">
        <% if (asignacionTurno != null) { %>
        <div id="volunteer-name">
            <div>
                <p id="lbl-capitan"><%= asignacionTurno.getColaborador().getNombre()%></p>
            </div>
            <div class="volunteer-date">
                <div>COMIENZO</div>
                <div><%= asignacionTurno.getHoraInicio()%></div>
            </div>
            <div class="volunteer-date">
                <div>FIN</div>
                <div><%= asignacionTurno.getHoraFin()%></div>
            </div>
        </div>
        <div id="volunteer-observations">
            <p><%= asignacionTurno.getObservaciones()%></p>
        </div>
        <% } else { %>
        <div id="volunteer-name">
            <div>
                <p id="lbl-capitan" style="color: gray; font-style: italic;">Sin asignar</p>
            </div>
            <div class="volunteer-date">
                <div>COMIENZO</div>
                <div>--:--</div>
            </div>
            <div class="volunteer-date">
                <div>FIN</div>
                <div>--:--</div>
            </div>
        </div>
        <div id="volunteer-observations">
            <p style="color: gray; font-style: italic;">No hay información para este turno.</p>
        </div>
        <% } %>
    </div>
</div>
<div id="button-container">
    <%
        boolean puedeEditar = usuario != null && usuario.getRol() != null &&
                (usuario.getRol().equals("ROLE_ADMIN") || usuario.getRol().equals("ROLE_COORD"));
    %>
    <button id="create-button" <%=puedeEditar ? "" : "disabled"%>>
        <%=asignacionTurno == null ? "Crear" : "Editar"%>
    </button>
    <button id="cancel-button">Cancelar</button>
</div>