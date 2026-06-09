<%--
/*
Daniel Robles Cantos 80%
IA: 20%
*/
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestión de Tiendas</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/tiendas.css">
</head>
<body>
<%
    List<Tienda> tiendas = (List<Tienda>) request.getAttribute("tiendas");

    List<Cadena> cadenas = (List<Cadena>) request.getAttribute("cadenas");
    List<Zona> zonas = (List<Zona>) request.getAttribute("zonas");
    List<Localidad> localidades = (List<Localidad>) request.getAttribute("localidades");

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
                            <% for(Cadena c : cadenas){ %>
                            <option value="<%=c.getId()%>" <%= c.getId() == cadenaMarcada ? "selected" : ""%> > <%=c.getNombre()%></option>
                            <% } %>
                        </select>

                    </div>

                    <div class="filtro-group">
                        <label for="zona-tienda">Filtrar por Zona</label>
                        <select name="zona-tienda" id="zona-tienda" class="btn-outline" style="padding: 5px 15px;">
                            <option value="">Sin Filtro</option>
                            <% for(Zona z : zonas){ %>
                            <option value="<%=z.getId()%>" <%= z.getId() == zonaMarcada ? "selected" : ""%> ><%=z.getNombre()%></option>
                            <% } %>
                        </select>

                    </div>

                    <div class="filtro-group">
                        <label for="localidad-tienda">Filtrar por Localidad</label>
                        <select name="localidad-tienda" id="localidad-tienda" class="btn-outline" style="padding: 5px 15px;">
                            <option value="">Sin Filtro</option>
                            <% for(Localidad l : localidades){ %>
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
                            <th>Tienda</th>
                            <th>Cadena</th>
                            <th>Lineales</th>
                            <th>Domicilio</th>
                            <th>Zona</th>
                            <th>Localidad</th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>

                        <tbody id="table-body-tiendas">
                        <% for(Tienda tienda : tiendas){ %>
                        <tr data-id="<%=tienda.getId()%>">
                            <td class="font-medium text-blue"><%= tienda.getNombre() %></td>
                            <td><%=tienda.getCadena().getNombre()%></td>
                            <td><%=tienda.getLineales()%></td>
                            <td class="small-td"><%= tienda.getDomicilio() %></td>
                            <td><%= tienda.getLocalidad().getMunicipio().getZona().getNombre() %></td>
                            <td><%= tienda.getLocalidad().getNombre() %></td>



                            <%-- Botones de acción --%>
                            <td><a href="/tiendas/crearTienda?id=<%=tienda.getId()%>" class="interact-tienda-btn editar-btn">Editar</a> </td>
                            <td><a href="/tiendas/eliminarTienda?id=<%= tienda.getId() %>"
                                   class="interact-tienda-btn eliminar-btn"
                                   onclick="return confirm('¿Estás seguro de que deseas eliminar la tienda «<%= tienda.getNombre() %>»?\n\nEsta acción también borrará sus asignaciones en las campañas y no se puede deshacer.');">
                                Eliminar
                            </a> </td>
                            <td><a href="/tiendas/verTienda?id=<%=tienda.getId()%>" class="interact-tienda-btn ver-btn">Ver</a> </td>
                            <td><a href="/tiendas/asignarParticipacion?id=<%=tienda.getId()%>" class="interact-tienda-btn asignacion-btn">Asignar Participación</a> </td>

                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
</main>

<jsp:include page="../shared/footer.jsp"/>


</body>
</html>