<%--
Marina Fernández Serrano: 100%
--%>

<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.ColaboradorEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.ColaboradorEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Colaborador" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Localidad" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.UsuarioEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Usuario" %><%--
  Created by IntelliJ IDEA.
  User: marin
  Date: 20/04/2026
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Colaboradores</title>
    <link rel="stylesheet" href="/css/global.css" />
    <link rel="stylesheet" href="/css/colaboradores.css">
</head>
<body>
<%
    List<Colaborador> colaboradores = (List<Colaborador>) request.getAttribute("colaboradores");
    List<Localidad> localidades = (List<Localidad>) request.getAttribute("localidades");
    List<Usuario> coordinadores = (List<Usuario>) request.getAttribute("coordinadores");
    Integer localidadSeleccionada = (Integer) request.getAttribute("localidadSeleccionada");
    Integer coordinadorSeleccionado = (Integer) request.getAttribute("coordinadorSeleccionado");
%>
<jsp:include page="../shared/navbar.jsp"/>

<main>
    <div class="page-body">
        <div class="page-header">
            <div class="left-header">
                <h1>Colaboradores</h1>
                <div class="text-muted">
                    Consulta los colaboradores y edita sus datos.
                </div>
            </div>
            <div class="right-header">
                <a href="/colaboradores/anadir" class="btn-primary" id="anadir-colaborador">
                        + Añadir colaborador
                </a>
            </div>
        </div>
        <div class="filtros-container">

            <div class="filtro-group">
                <label for="colaboraEn">Colabora en:</label>
                <select name="colaboraEn" id="colaboraEn" class="btn-outline" style="padding: 5px 15px;">
                    <option value="">Sin Filtro</option>
                    <% for(Localidad l : localidades){ %>
                    <option value="<%=l.getId()%>" <%= l.getId().equals(localidadSeleccionada) ? "selected" : ""%> > <%=l.getNombre()%></option>
                    <% } %>
                </select>
            </div>

            <div class="filtro-group">
                <label for="coordinador">Filtrar por Coordinador</label>
                <select name="coordinador" id="coordinador" class="btn-outline" style="padding: 5px 15px;">
                    <option value="">Sin Filtro</option>
                    <% for(Usuario u : coordinadores){ %>
                    <option value="<%=u.getId()%>" <%= u.getId().equals(coordinadorSeleccionado) ? "selected" : ""%> ><%=u.getNombre()%></option>
                    <% } %>
                </select>
            </div>

        </div>


        <div class="page-wrapper">

            <div class="left-column">
                <div class="table-container card">
                    <table class="modernTable">
                        <thead>
                        <tr>
                            <th>Colaborador</th>
                            <th>Domicilio</th>
                            <th>Localidad</th>
                            <th>Colabora en</th>
                            <th>Coordinador</th>
                            <th>Contacto Principal</th>
                            <th>Observaciones</th>
                        </tr>
                        </thead>
                        <tbody id="table-body">
                            <%
                                for (Colaborador colaborador : colaboradores) {

                            %>
                            <tr data-id="<%=colaborador.getId()%>">
                                <td class="font-medium text-blue"><%=colaborador.getNombre()%></td>
                                <td><%=colaborador.getDomicilio()%></td>
                                <td><%=colaborador.getLocalidadSede() != null ? colaborador.getLocalidadSede().getNombre() : ""%></td>
                                <td><%=colaborador.getColaboraEn() != null ? colaborador.getColaboraEn().getNombre() : ""%></td>
                                <td><%=colaborador.getCoordinador() != null ? colaborador.getCoordinador().getNombre() : ""%></td>
                                <td><%=colaborador.getContactoPrincipal() != null ? colaborador.getContactoPrincipal() : ""%></td>
                                <td><%=colaborador.getObservaciones() != null ? colaborador.getObservaciones() : ""%></td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="right-column">
                <div class="side-panel-unified">
                    <div id="info-container" class="card side-panel">
                    </div>
                    <div class="buttons-wrapper">
                        <jsp:include page="buttons-colaboradores.jsp"/>
                    </div>
                </div>

            </div>
        </div>
    </div>
</main>
<script>
    const table = document.querySelector("#table-body");
    const infoContainer = document.querySelector("#info-container");
    const rightColumn = document.querySelector(".right-column");

    let id = null;

    function fetchColaboradorData(){
        if(!id) return;

        const params = new URLSearchParams();
        params.append("id", id);

        fetch("/colaboradores/buscarColaborador", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: params.toString()
        })
            .then(response => response.text())
            .then(html => {
                infoContainer.innerHTML = html;
                rightColumn.classList.add("open");
            })
            .catch(error => console.error("Error: ", error))
    }

    table.addEventListener("click", (e) => {
        const row = e.target.closest("tr");
        if (!row) return;

        if(row.classList.contains("selected")) {
            row.classList.remove("selected");
            rightColumn.classList.remove("open");
            id = null;
            return;
        }

        table.querySelectorAll("tr").forEach(r => r.classList.remove("selected"));

        row.classList.add("selected");
        id = row.dataset.id;

        fetchColaboradorData();
    });

    infoContainer.addEventListener("click", (e) => {
        if (e.target.id === "cancel-button"){
            rightColumn.classList.remove("open");
            table.querySelectorAll("tr").forEach(r => r.classList.remove("selected"));
        }
    })

    const buttonSection = document.querySelector(".buttons-section");

    buttonSection.addEventListener("click", (e) => {
        if(e.target.id === "modificar-b") {
            if(!id){
                return;
            }
            window.location.href = `/colaboradores/editar?id=` + id;
        }

        if(e.target.id === "eliminar-b") {
            if(!id){
                return;
            }
            window.location.href = `/colaboradores/eliminar?id=` + id;
        }

        if(e.target.id === "asignar-b") {
            if(!id){
                return;
            }
            window.location.href = `/colaboradores/asignar?id=` + id;
        }
    })

    // Lógica del filtrado de arriba
    const colaboraEnSelect = document.querySelector("#colaboraEn");
    const coordinadorSelect = document.querySelector("#coordinador");

    function fetchFiltros() {
        const idLocalidad = colaboraEnSelect.value;
        const idCoordinador = coordinadorSelect.value;

        window.location.href = "/colaboradores/filtrar?colaboraEn=" + idLocalidad + "&coordinador=" + idCoordinador;
    }

    colaboraEnSelect.addEventListener("change", fetchFiltros);
    coordinadorSelect.addEventListener("change", fetchFiltros);
</script>
</body>
</html>
