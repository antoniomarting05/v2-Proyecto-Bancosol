<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.TiendaCampanyaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.TiendaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.CadenaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.ZonaEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.LocalidadEntity" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.ColaboradorEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestión de Tiendas</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/tiendas.css">
</head>
<body>
<%
    List<TiendaEntity> tiendas = (List<TiendaEntity>) request.getAttribute("tiendas");

    List<CadenaEntity> cadenas = (List<CadenaEntity>) request.getAttribute("cadenas");
    List<ZonaEntity> zonas = (List<ZonaEntity>) request.getAttribute("zonas");
    List<LocalidadEntity> localidades = (List<LocalidadEntity>) request.getAttribute("localidades");

    Integer cadenaMarcada = (Integer) request.getAttribute("cadenaMarcada");
    Integer zonaMarcada = (Integer) request.getAttribute("zonaMarcada");
    Integer localidadMarcada = (Integer) request.getAttribute("localidadMarcada");
%>

<jsp:include page="../shared/navbar.jsp"/>

<main>
    <div class="page-wrapper">

        <div class="left-column">
            <div class="header-principal">
                <div>
                    <h1>Gestión de Tiendas</h1>
                    <p>Consulta, filtra y crea tiendas</p>
                </div>

                <div class="btn-header-principal">
                    <a href="/tiendas/crearTienda" class="btn-primary">
                        <span>+</span>
                        <span> Crear Tienda</span>
                    </a>
                </div>
            </div>


            <div class="header-actions">


                <form id="filtrado-tiendas" action="/tiendas/filtrarTiendas" method="post">

                    <div class="filtro-group">
                        <label for="cadena-tienda">Filtrar por Cadena</label>
                        <select name="cadena-tienda" id="cadena-tienda" class="btn-outline" style="padding: 5px 15px;">
                            <option value="">Sin Filtro</option>
                            <% for(CadenaEntity c : cadenas){ %>
                            <option value="<%=c.getId()%>" <%= c.getId() == cadenaMarcada ? "selected" : ""%> > <%=c.getNombre()%></option>
                            <% } %>
                        </select>

                    </div>

                    <div class="filtro-group">
                        <label for="zona-tienda">Filtrar por Zona</label>
                        <select name="zona-tienda" id="zona-tienda" class="btn-outline" style="padding: 5px 15px;">
                            <option value="">Sin Filtro</option>
                            <% for(ZonaEntity z : zonas){ %>
                            <option value="<%=z.getId()%>" <%= z.getId() == zonaMarcada ? "selected" : ""%> ><%=z.getNombre()%></option>
                            <% } %>
                        </select>

                    </div>

                    <div class="filtro-group">
                        <label for="localidad-tienda">Filtrar por Localidad</label>
                        <select name="localidad-tienda" id="localidad-tienda" class="btn-outline" style="padding: 5px 15px;">
                            <option value="">Sin Filtro</option>
                            <% for(LocalidadEntity l : localidades){ %>
                            <option value="<%=l.getId()%>" <%= l.getId() == localidadMarcada ? "selected" : ""%>><%=l.getNombre()%></option>
                            <% } %>
                        </select>
                    </div>




                    <div class="filtro-group boton-container">
                        <span class="label-spacer"></span>
                        <button type="submit" class="btn-outline btn-filtrar">Filtrar</button>
                    </div>
                </form>
            </div>


            <section class="tiendas-container">
                <div class="table-container card">
                    <table class="modernTable">
                        <thead>
                        <tr>
                            <th rowspan="2">Tienda</th>
                            <th rowspan="2">Lineales</th>
                            <th rowspan="2">Domicilio</th>
                            <th rowspan="2">Zona</th>
                            <th rowspan="2">Localidad</th>
                            <th colspan="2" style="text-align: center;">Coordinadores</th>
                            <th rowspan="2"></th>
                            <th rowspan="2"></th>
                            <th rowspan="2"></th>
                        </tr>
                        <tr>
                            <th>Primavera</th>
                            <th>Gran Recogida</th>
                        </tr>
                        </thead>

                        <tbody id="table-body-tiendas">
                        <% for(TiendaEntity tienda : tiendas){ %>
                        <tr data-id="<%=tienda.getId()%>">
                            <td class="font-medium text-blue"><%= tienda.getNombre() %></td>
                            <td><%=tienda.getLineales()%></td>
                            <td class="small-td"><%= tienda.getDomicilio() %></td>
                            <td><%= tienda.getLocalidad().getMunicipio().getZona().getNombre() %></td>
                            <td><%= tienda.getLocalidad().getNombre() %></td>

                            <%
                                String coordPrimavera = "Sin asignar";
                                String coordGR = "Sin asignar";

                                if (tienda.getTiendasCampanya() != null) {
                                    for(TiendaCampanyaEntity c : tienda.getTiendasCampanya()) {
                                        if(c.getCampanya().getTipoCampanya().getId() == 2 && c.getCoordinador() != null) {
                                            coordPrimavera = c.getCoordinador().getNombre();
                                        } else if (c.getCampanya().getTipoCampanya().getId() == 1 && c.getCoordinador() != null) {
                                            coordGR = c.getCoordinador().getNombre();
                                        }
                                    }
                                }
                            %>

                            <%-- Primavera (Fijo: 1 sola columna) --%>
                            <td>
                                <div style="display: flex; justify-content: space-between; align-items: center; gap: 5px;">
                                    <span class="small-td"><%= coordPrimavera %></span>
                                </div>
                            </td>

                            <%-- Gran Recogida (Fijo: 1 sola columna) --%>
                            <td>
                                <div style="display: flex; justify-content: space-between; align-items: center; gap: 5px;">
                                    <span class="small-td"><%= coordGR %></span>
                                </div>
                            </td>

                            <%-- Botones de acción --%>
                            <td><a href="/tiendas/crearTienda?id=<%=tienda.getId()%>" class="interact-tienda-btn editar-btn">Editar</a> </td>
                            <td><a class="interact-tienda-btn eliminar-btn">Eliminar</a> </td>
                            <td><a class="interact-tienda-btn ver-btn">Ver</a> </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>

        <div class="right-column">
            <div class="card side-panel" id="info-container-tienda">
            </div>
        </div>
    </div>
</main>

<jsp:include page="../shared/footer.jsp"/>


</body>
</html>