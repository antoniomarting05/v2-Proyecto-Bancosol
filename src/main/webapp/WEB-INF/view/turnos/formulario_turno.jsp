<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.TurnoEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.TurnoEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.*" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Colaborador" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.TiendaCampanya" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Tienda" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Turno" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Turno turno = (Turno) request.getAttribute("asignacionTurno");
    List<Colaborador> colaboradores = (List<Colaborador>) request.getAttribute("colaboradores");
    Colaborador colaboradorModal = (Colaborador) request.getAttribute("colaborador");
    Tienda tienda = (Tienda) request.getAttribute("tienda");
    String errorModal = (String) request.getAttribute("errorModal");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Asignación de Turno</title>
    <link rel="stylesheet" href="/css/global.css" />
    <link rel="stylesheet" href="/css/formularioTurno.css" />
    <link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
</head>
<body class="formulario-turno-page">

<jsp:include page="../shared/navbar.jsp"/>
<div style="display: flex; flex-direction: row" >
    <div class="form-turno">
        <div class="turno-info">
            <div class="turno-info-row">
                <span><%=tienda.getNombre()%> - <span class="text-mutex"><%=tienda.getDomicilio()%></span></span>
                <span><%=turno.getTipoTurno().getNombre()%></span>
            </div>
            <div class="turno-info-row">
                <span></span>
                <span>Lineal <%=turno.getLineal()%></span>
            </div>
        </div>

        <form action="/turnos/guardarTurno" method="post">
            <input type="hidden" value="<%=turno.getId() != null ? turno.getId() : ""%>" name="id">
            <input type="hidden" value="<%=turno.getTiendaCampanya().getId()%>" name="tiendaCampanyaId">
            <input type="hidden" value="<%=turno.getTipoTurno().getId()%>" name="tipoTurnoId">
            <input type="hidden" value="<%=turno.getLineal()%>" name="lineal">
            <input type="hidden" id="errorModal" value="<%=errorModal != null ? errorModal : ""%>" name="errorModal">


            <div class="form-group">
                <label for="input_colaboradores">Colaborador:</label>
                <select id="input_colaboradores" name="idColaborador">
                    <option value="0">Seleccione un colaborador...</option>
                    <%for (Colaborador colaborador : colaboradores) {%>
                    <option value="<%=colaborador.getId()%>" <%=turno.getColaborador() != null && turno.getColaborador().getId().equals(colaborador.getId()) ? "selected" : "" %>>
                        <%=colaborador.getNombre()%>
                    </option>
                    <%}%>
                </select>
            </div>

            <div class="form-group">
                <label for="input_num_voluntarios">Número de voluntarios:</label>
                <input id="input_num_voluntarios" type="number" name="numVoluntarios"
                       value="<%=turno.getNumVoluntarios() != null ? turno.getNumVoluntarios() : ""%>">
            </div>

            <div class="form-group-row">
                <div class="form-group">
                    <label for="input_hora_inicio">Hora Inicio:</label>
                    <input id="input_hora_inicio" type="time" name="horaInicio"
                           value="<%=turno.getHoraInicio() != null ? turno.getHoraInicio() : ""%>">
                </div>

                <div class="form-group">
                    <label for="input_hora_fin">Hora Fin:</label>
                    <input id="input_hora_fin" type="time" name="horaFin"
                           value="<%=turno.getHoraFin() != null ? turno.getHoraFin() : ""%>">
                </div>
            </div>

            <div class="form-group">
                <label for="input_observaciones">Observaciones:</label>
                <textarea id="input_observaciones" name="observaciones" rows="4"><%=turno.getObservaciones() != null ? turno.getObservaciones() : ""%></textarea>
            </div>

            <button type="submit" class="btn-submit">Guardar Turno</button>
        </form>
    </div>
    <div id="colaborador_container" class="card side-panel" style="margin: 30px 0">
        <%if (colaboradorModal != null){%>
            <jsp:include page="../colaboradores/info_colaboradores.jsp"/>
        <%}%>
    </div>
</div>
</body>
<script>
    const colaboradorContainer = document.querySelector("#colaborador_container");
    const inputColaboradores = document.querySelector("#input_colaboradores");
    let colaboradorActual = inputColaboradores.value;
    let errorModal = document.querySelector("#errorModal").value;

    if (errorModal != "") {
        alert(errorModal);
    }

    function fetchColaboradorData() {
        if (colaboradorActual == 0) {
            colaboradorContainer.innerHTML = "";
            return;
        }

        const params = new URLSearchParams();
        params.append("id", colaboradorActual);

        fetch("/colaboradores/buscarColaborador", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: params.toString()
        })
            .then(response => response.text())
            .then(html => {colaboradorContainer.innerHTML = html})
            .catch(error => console.error("Error:", error));
    }

    inputColaboradores.addEventListener("change", (e) => {
        colaboradorActual = e.target.value;
        fetchColaboradorData();
    })


</script>
</html>