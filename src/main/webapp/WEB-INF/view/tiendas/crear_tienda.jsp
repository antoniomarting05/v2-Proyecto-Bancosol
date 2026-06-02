<%--
  Created by IntelliJ IDEA.
  User: dante
  Date: 21/05/2026
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%= (request.getAttribute("editando") != null && (Boolean)request.getAttribute("editando")) ? "Editar" : "Crear" %> Tienda </title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/campanyas.css">
</head>
<%
    // Recuperamos las listas para los desplegables
    List<CadenaEntity> listaCadenas = (List<CadenaEntity>) request.getAttribute("cadenas");
    List<ZonaEntity> listaZonas = (List<ZonaEntity>) request.getAttribute("zonas");
    List<MunicipioEntity> listaMunicipios = (List<MunicipioEntity>) request.getAttribute("municipios");
    List<LocalidadEntity> listaLocalidades = (List<LocalidadEntity>) request.getAttribute("localidades");
    List<DistritoEntity> listaDistritos = (List<DistritoEntity>) request.getAttribute("distritos");

    List<UsuarioEntity> listaCoordinadores = (List<UsuarioEntity>) request.getAttribute("coordinadores");

    // Lógica de edición
    Boolean editando = (Boolean) request.getAttribute("editando");
    if (editando == null) editando = false;

    // Pasamos el objeto completo
    TiendaEntity tiendaActual = (TiendaEntity) request.getAttribute("tiendaActual");

    // Lógica para preseleccionar coordinadores si estamos editando
    Integer coordPrimaveraId = null;
    Integer coordGRId = null;

    if (editando && tiendaActual != null && tiendaActual.getTiendasCampanya() != null) {
        for (TiendaCampanyaEntity tc : tiendaActual.getTiendasCampanya()) {
            if (tc.getCampanya().getTipoCampanya().getId() == 2 && tc.getCoordinador() != null) {
                coordPrimaveraId = tc.getCoordinador().getId(); // Primavera
            } else if (tc.getCampanya().getTipoCampanya().getId() == 1 && tc.getCoordinador() != null) {
                coordGRId = tc.getCoordinador().getId(); // Gran Recogida
            }
        }
    }
%>

<jsp:include page="../shared/navbar.jsp"/>

<main class="main-page">
    <section class="campanya-form-wrapper">
        <div class="campanya-header">
            <div>
                <h2>Datos de la <%= editando ? "tienda" : "nueva tienda" %></h2>
                <p>Completa la información básica y asigna la tienda a su zona y localidad.</p>
            </div>
        </div>

        <div class="card campanya-form-card">
            <form class="campanya-form" method="post" action="/tiendas/guardarTienda">

                <%-- Si estamos editando, enviamos el ID oculto para que haga un UPDATE en vez de un INSERT --%>
                <% if (editando && tiendaActual != null) { %>
                <input type="hidden" name="id" value="<%= tiendaActual.getId() %>">
                <% } %>

                <section class="form-section">
                    <h3 class="form-section-title">Información Principal</h3>

                    <div class="form-row">
                        <div class="form-group" style="flex: 2;">
                            <label for="nombre">Nombre de la Tienda</label>
                            <input id="nombre" type="text" name="nombre"
                                   value="<%= editando && tiendaActual != null ? tiendaActual.getNombre() : "" %>"
                                   required placeholder="Ej. Mercadona Centro">
                        </div>

                        <div class="form-group" style="flex: 1;">
                            <label for="lineales">Lineales</label>
                            <input id="lineales" type="number" name="lineales"
                                   value="<%= editando && tiendaActual != null ? tiendaActual.getLineales() : "" %>"
                                   placeholder="Ej. 5">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group" style="flex: 2;">
                            <label for="domicilio">Domicilio / Dirección</label>
                            <input id="domicilio" type="text" name="domicilio"
                                   value="<%= editando && tiendaActual != null && tiendaActual.getDomicilio() != null ? tiendaActual.getDomicilio() : "" %>"
                                   required placeholder="Calle, número...">
                        </div>

                        <div class="form-group" style="flex: 1;">
                            <label for="codigoPostal">Código Postal</label>
                            <input id="codigoPostal" type="number" name="codigoPostal"
                                   value="<%= editando && tiendaActual != null && tiendaActual.getCp() != null ? tiendaActual.getCp() : "" %>"
                                   required placeholder="Ej. 29010">
                        </div>

                        <div class="form-group" id="contenedorDistrito" style="flex: 1; display: none;">
                            <label for="distrito">Distrito</label>
                            <select id="distrito" name="distritoId" class="campanya-select">
                                <option value="">Selecciona un distrito</option>
                                <% if(listaDistritos != null) { for (DistritoEntity d : listaDistritos) { %>
                                <option value="<%= d.getId() %>"
                                        <%= (editando && tiendaActual != null && tiendaActual.getDistrito() != null && tiendaActual.getDistrito().getId().equals(d.getId())) ? "selected" : "" %>>
                                    <%= d.getNombre() %>
                                </option>
                                <% } } %>
                            </select>
                        </div>
                    </div>
                </section>

                <section class="form-section">
                    <h3 class="form-section-title">Clasificación y Ubicación</h3>

                    <div class="form-row">
                        <%-- Select Cadena --%>
                        <div class="form-group">
                            <label for="cadena">Cadena</label>
                            <select id="cadena" name="cadenaId" class="campanya-select" required>
                                <option value="">Selecciona una cadena</option>
                                <% for (CadenaEntity c : listaCadenas) { %>
                                <option value="<%= c.getId() %>"
                                        <%= (editando && tiendaActual != null && tiendaActual.getCadena().getId().equals(c.getId())) ? "selected" : "" %>>
                                    <%= c.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <%-- Select Zona --%>
                        <div class="form-group">
                            <label for="zona">Zona</label>
                            <select id="zona" name="zonaId" class="campanya-select" required>
                                <option value="">Selecciona una zona</option>
                                <% for (ZonaEntity z : listaZonas) { %>
                                <option value="<%= z.getId() %>"
                                        <%= (editando && tiendaActual != null && tiendaActual.getLocalidad().getMunicipio().getZona().getId().equals(z.getId())) ? "selected" : "" %>>
                                    <%= z.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <%-- Select Municipio (Bloqueado por defecto) --%>
                        <div class="form-group">
                            <label for="municipio">Municipio</label>
                            <select id="municipio" name="municipioId" class="campanya-select" required disabled>
                                <option value="">Primero selecciona una zona</option>
                                <% if(listaMunicipios != null) { for (MunicipioEntity m : listaMunicipios) { %>
                                <option value="<%= m.getId() %>" data-zona="<%= m.getZona().getId() %>"
                                        <%= (editando && tiendaActual != null && tiendaActual.getLocalidad().getMunicipio().getId().equals(m.getId())) ? "selected" : "" %>>
                                    <%= m.getNombre() %>
                                </option>
                                <% } } %>
                            </select>
                        </div>

                        <%-- Select Localidad (Bloqueado por defecto) --%>
                        <div class="form-group">
                            <label for="localidad">Localidad</label>
                            <select id="localidad" name="localidadId" class="campanya-select" required disabled>
                                <option value="">Primero selecciona un municipio</option>
                                <% for (LocalidadEntity l : listaLocalidades) { %>
                                <option value="<%= l.getId() %>" data-municipio="<%= l.getMunicipio().getId() %>"
                                        <%= (editando && tiendaActual != null && tiendaActual.getLocalidad().getId().equals(l.getId())) ? "selected" : "" %>>
                                    <%= l.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                </section>


                <%--  FALTAN LOS COORDINADORESSSSS SELECCIONARLOS --------------------------- --%>

                <section class="form-section">
                    <h3 class="form-section-title">Coordinadores</h3>

                    <div class="form-row">
                        <%-- Select Coord Primavera --%>
                        <div class="form-group">
                            <label for="coordPrimavera">Coordinador Primavera</label>
                            <select id="coordPrimavera" name="coordinadorPrimaveraId" class="campanya-select">
                                <option value="">Sin asignar</option>
                                <% for (UsuarioEntity u : listaCoordinadores) { %>
                                <option value="<%= u.getId() %>"
                                        <%= (coordPrimaveraId != null && coordPrimaveraId.equals(u.getId())) ? "selected" : "" %>>
                                    <%= u.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <%-- Select Coord Gran Recogida --%>
                        <div class="form-group">
                            <label for="coordGR">Coordinador Gran Recogida</label>
                            <select id="coordGR" name="coordinadorGRId" class="campanya-select">
                                <option value="">Sin asignar</option>
                                <% for (UsuarioEntity u : listaCoordinadores) { %>
                                <option value="<%= u.getId() %>"
                                        <%= (coordGRId != null && coordGRId.equals(u.getId())) ? "selected" : "" %>>
                                    <%= u.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                    </div>

                </section>

                <section class="form-actions">
                    <a href="/tiendas" class="btn-outline">
                        <%= editando ? "Salir sin guardar" : "Cancelar" %>
                    </a>

                    <button type="submit" class="btn-primary" style="font-size: 15.5px"> Guardar Tienda</button>
                </section>

            </form>
        </div>
    </section>
</main>

<jsp:include page="../shared/footer.jsp"/>



<body></body>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const zonaSelect = document.getElementById('zona');
        const municipioSelect = document.getElementById('municipio');
        const localidadSelect = document.getElementById('localidad');

        //Para verificar si es MALAGA CAPITAL o no
        const contenedorDistrito = document.getElementById('contenedorDistrito');

        // Guardamos copias originales
        const todosLosMunicipios = Array.from(municipioSelect.querySelectorAll('option'));
        const todasLasLocalidades = Array.from(localidadSelect.querySelectorAll('option'));

        function comprobarCapital() {
            // Cogemos la opción que está seleccionada ahora mismo
            const opcionSeleccionada = zonaSelect.options[zonaSelect.selectedIndex];

            // Comprobamos si el texto dice "Málaga" (ignorando mayúsculas)
            if (opcionSeleccionada && opcionSeleccionada.text.trim().toLowerCase() === 'málaga capital') {
                contenedorDistrito.style.display = 'block'; // Lo mostramos
            } else {
                contenedorDistrito.style.display = 'none';  // Lo ocultamos
            }
        }

        function filtrarMunicipios() {
            const zonaId = zonaSelect.value;

            municipioSelect.innerHTML = '';
            municipioSelect.appendChild(todosLosMunicipios[0]);

            if (!zonaId) {
                municipioSelect.disabled = true;
                todosLosMunicipios[0].textContent = "Primero selecciona una zona";
                filtrarLocalidades(); // Si se borra la zona, también se bloquea la localidad
                comprobarCapital();
                return;
            }

            municipioSelect.disabled = false;
            todosLosMunicipios[0].textContent = "Selecciona un municipio";

            todosLosMunicipios.forEach(opcion => {
                if (opcion.getAttribute('data-zona') === zonaId) {
                    municipioSelect.appendChild(opcion);
                }
            });

            // Cada vez que cambiamos la zona, reseteamos las localidades
            filtrarLocalidades();
            comprobarCapital();
        }

        function filtrarLocalidades() {
            const municipioId = municipioSelect.value;

            localidadSelect.innerHTML = '';
            localidadSelect.appendChild(todasLasLocalidades[0]);

            if (!municipioId) {
                localidadSelect.disabled = true;
                todasLasLocalidades[0].textContent = "Primero selecciona un municipio";
                return;
            }

            localidadSelect.disabled = false;
            todasLasLocalidades[0].textContent = "Selecciona una localidad";

            todasLasLocalidades.forEach(opcion => {
                if (opcion.getAttribute('data-municipio') === municipioId) {
                    localidadSelect.appendChild(opcion);
                }
            });

            comprobarCapital();
        }

        // Asignamos los eventos
        zonaSelect.addEventListener('change', filtrarMunicipios);
        municipioSelect.addEventListener('change', function() {
            filtrarLocalidades();
            comprobarCapital();
        });

        // --- Lógica para modo "Editar" ---
        if (zonaSelect.value) {
            filtrarMunicipios();
            <% if (editando && tiendaActual != null) { %>
            // Forzamos la selección del municipio y cargamos sus localidades
            municipioSelect.value = "<%= tiendaActual.getLocalidad().getMunicipio().getId() %>";
            filtrarLocalidades();
            // Forzamos la selección de la localidad
            localidadSelect.value = "<%= tiendaActual.getLocalidad().getId() %>";
            comprobarCapital();
            <% } %>
        }
    });
</script>
</html>