<%--
/*
Daniel Robles Cantos 80%
IA: 20%
*/
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.entity.*" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.*" %>
<%@ page import="org.springframework.cglib.core.Local" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Listas del desplegable
    List<Cadena> listaCadenas = (List<Cadena>) request.getAttribute("cadenas");
    List<Zona> listaZonas = (List<Zona>) request.getAttribute("zonas");
    List<Municipio> listaMunicipios = (List<Municipio>) request.getAttribute("municipios");
    List<Localidad> listaLocalidades = (List<Localidad>) request.getAttribute("localidades");
    List<Distrito> listaDistritos = (List<Distrito>) request.getAttribute("distritos");

    List<Usuario> listaCoordinadores = (List<Usuario>) request.getAttribute("coordinadores");
    List<Usuario> listaCapitanes = (List<Usuario>) request.getAttribute("capitanes");

    List<Campanya> listaCampanyasDeTienda = (List<Campanya>) request.getAttribute("campanyasDeLaTienda");

    // la logica para editar y ver
    Boolean editando = (Boolean) request.getAttribute("editando");
    if (editando == null) editando = false;

    Boolean viendo = (Boolean) request.getAttribute("viendo");
    if (viendo == null) viendo = false;

    // objeto tienda
    Tienda tiendaActual = (Tienda) request.getAttribute("tiendaActual");

    Integer capitanId = null;
    if ((editando || viendo) && tiendaActual != null) {
        // capitan de la tienda
        if (tiendaActual.getCapitan() != null) {
            capitanId = tiendaActual.getCapitan().getId();
        }
    }
%>

<html>
<head>
    <title><%= viendo ? "Ver" : (editando ? "Editar" : "Crear") %> Tienda </title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/campanyas.css">
</head>

<jsp:include page="../shared/navbar.jsp"/>

<main class="main-page">
    <section class="campanya-form-wrapper">
        <div class="campanya-header">
            <div>
                <h2>Datos de la <%= viendo ? "tienda (Modo Lectura)" : (editando ? "tienda" : "nueva tienda") %></h2>
                <p>Información básica de la tienda.</p>
            </div>
        </div>

        <div class="card campanya-form-card">
            <form class="campanya-form" method="post" action="/tiendas/guardarTienda">

                <%-- Si estamos editando, enviamos el ID oculto para que haga  UPDATE en vez de un INSERT --%>
                <% if (editando && tiendaActual != null) { %>
                <input type="hidden" name="id" value="<%= tiendaActual.getId() %>">
                <% } %>

                <section class="form-section">
                    <h3 class="form-section-title">Información Principal</h3>

                    <div class="form-row">
                        <div class="form-group" style="flex: 2;">
                            <label for="nombre">Nombre de la Tienda</label>
                            <input id="nombre" type="text" name="nombre"
                                   value="<%= tiendaActual != null ? tiendaActual.getNombre() : "" %>" <%= viendo ? "disabled" : "" %>
                                   required placeholder="Ej. Mercadona Centro">
                        </div>

                        <div class="form-group" style="flex: 1;">
                            <label for="lineales">Lineales</label>
                            <input id="lineales" type="number" name="lineales"
                                   value="<%= tiendaActual != null ? tiendaActual.getLineales() : "" %>" <%= viendo ? "disabled" : "" %>
                                   placeholder="Ej. 5">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group" style="flex: 2;">
                            <label for="domicilio">Domicilio / Dirección</label>
                            <input id="domicilio" type="text" name="domicilio"
                                   value="<%= tiendaActual != null && tiendaActual.getDomicilio() != null ? tiendaActual.getDomicilio() : "" %>" <%= viendo ? "disabled" : "" %>
                                   required placeholder="Calle, número...">
                        </div>

                        <div class="form-group" style="flex: 1;">
                            <label for="codigoPostal">Código Postal</label>
                            <input id="codigoPostal" type="number" name="codigoPostal"
                                   value="<%= tiendaActual != null && tiendaActual.getCp() != null ? tiendaActual.getCp() : "" %>" <%= viendo ? "disabled" : "" %>
                                   required placeholder="Ej. 29010">
                        </div>

                        <div class="form-group" id="contenedorDistrito" style="flex: 1; display: none;">
                            <label for="distrito">Distrito</label>
                            <select id="distrito" name="distritoId" class="campanya-select" <%= viendo ? "disabled" : "" %>>
                                <option value="">Selecciona un distrito</option>
                                <% if(listaDistritos != null) { for (Distrito d : listaDistritos) { %>
                                <option value="<%= d.getId() %>" <%= viendo ? "disabled" : "" %>
                                        <%= (tiendaActual != null && tiendaActual.getDistrito() != null && tiendaActual.getDistrito().getId().equals(d.getId())) ? "selected" : "" %>>
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
                            <select id="cadena" name="cadenaId" class="campanya-select" required <%= viendo ? "disabled" : "" %>>
                                <option value="">Selecciona una cadena</option>
                                <% for (Cadena c : listaCadenas) { %>
                                <option value="<%= c.getId() %>"
                                        <%= (tiendaActual != null && tiendaActual.getCadena().getId().equals(c.getId())) ? "selected" : "" %>>
                                    <%= c.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <%-- Select Zona --%>
                        <div class="form-group">
                            <label for="zona">Zona</label>
                            <select id="zona" name="zonaId" class="campanya-select" required <%= viendo ? "disabled" : "" %>>
                                <option value="">Selecciona una zona</option>
                                <% for (Zona z : listaZonas) { %>
                                <option value="<%= z.getId() %>"
                                        <%= (tiendaActual != null && tiendaActual.getLocalidad().getMunicipio().getZona().getId().equals(z.getId())) ? "selected" : "" %>>
                                    <%= z.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <%-- Select Municipio (bloqueado por defecto) --%>
                        <div class="form-group">
                            <label for="municipio">Municipio</label>
                            <select id="municipio" name="municipioId" class="campanya-select" required disabled <%= viendo ? "disabled" : "" %>>
                                <option value="">Primero selecciona una zona</option>
                                <% if(listaMunicipios != null) { for (Municipio m : listaMunicipios) { %>
                                <option value="<%= m.getId() %>" data-zona="<%= m.getZona().getId() %>"
                                        <%= (tiendaActual != null && tiendaActual.getLocalidad().getMunicipio().getId().equals(m.getId())) ? "selected" : "" %>>
                                    <%= m.getNombre() %>
                                </option>
                                <% } } %>
                            </select>
                        </div>

                        <%-- Select Localidad (bloqueado por defecto) --%>
                        <div class="form-group">
                            <label for="localidad">Localidad</label>
                            <select id="localidad" name="localidadId" class="campanya-select" required disabled <%= viendo ? "disabled" : "" %>>
                                <option value="">Primero selecciona un municipio</option>
                                <% for (Localidad l : listaLocalidades) { %>
                                <option value="<%= l.getId() %>" data-municipio="<%= l.getMunicipio().getId() %>"
                                        <%= (tiendaActual != null && tiendaActual.getLocalidad().getId().equals(l.getId())) ? "selected" : "" %>>
                                    <%= l.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                </section>


                <%--  Coordinadores y Capitanes para seleccionarr --%>

                <section class="form-section">
                    <h3 class="form-section-title">Coordinadores de Campañas</h3>

                    <% if (listaCampanyasDeTienda != null && !listaCampanyasDeTienda.isEmpty()) { %>
                    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px;">

                        <%
                            for (Campanya c : listaCampanyasDeTienda) {
                                // Busco si esta campanya ya tiene coordinador o no
                                Integer coordAsignadoId = null;
                                if (tiendaActual != null && tiendaActual.getTiendasCampanya() != null) {
                                    for (TiendaCampanya tc : tiendaActual.getTiendasCampanya()) {
                                        if (tc.getCampanya().getId().equals(c.getId())) {
                                            coordAsignadoId = (tc.getCoordinador() != null) ? tc.getCoordinador().getId() : null;
                                            break;
                                        }
                                    }
                                }
                        %>
                        <div class="form-group" style="width: 100%;">
                            <label for="coord_<%= c.getId() %>" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: block;" title="<%= c.getNombre() %>">
                                <%= c.getNombre() %>
                            </label>

                            <input type="hidden" name="campanyaIds" value="<%= c.getId() %>">

                            <select id="coord_<%= c.getId() %>" name="coordinadorIds" class="campanya-select" <%= viendo ? "disabled" : "" %> style="width: 100%;">
                                <option value="">Sin asignar</option>
                                <% for (Usuario u : listaCoordinadores) { %>
                                <option value="<%= u.getId() %>" <%= (coordAsignadoId != null && coordAsignadoId.equals(u.getId())) ? "selected" : "" %>>
                                    <%= u.getNombre() %>
                                </option>
                                <% } %>
                            </select>
                        </div>
                        <% } %> </div> <% } else { %>
                    <p style="font-size: 0.9rem; color: var(--text-muted); padding: 10px 0;">
                        <%= editando
                                ? "Esta tienda no participa en ninguna campaña actualmente. Asígnale participación primero desde TIENDAS."
                                : "Guarda la tienda primero para poder asignarle participaciones y coordinadores." %>
                    </p>
                    <% } %>
                </section>

                <section class="form-section">
                    <h3 class="form-section-title">Capitán de la tienda</h3>

                    <div class="form-row">
                        <%-- Select Capitan Único --%>
                        <div class="form-group" style="flex: 1;">
                            <label for="capitanId">Capitán de Tienda</label>
                            <select id="capitanId" name="capitanId" class="campanya-select" <%= viendo ? "disabled" : "" %>>
                                <option value="">Sin asignar</option>
                                <% if(listaCapitanes != null) { for (Usuario u : listaCapitanes) { %>
                                <option value="<%= u.getId() %>"
                                        <%= (capitanId != null && capitanId.equals(u.getId())) ? "selected" : "" %>>
                                    <%= u.getNombre() %>
                                </option>
                                <% } } %>
                            </select>
                        </div>
                    </div>

                </section>

                    <section class="form-actions">
                        <a href="/tiendas" class="btn-outline">
                            <%= viendo ? "Volver al listado" : (editando ? "Salir sin guardar" : "Cancelar") %>
                        </a>

                        <% if (!viendo) { %>
                        <button type="submit" class="btn-primary" style="font-size: 15.5px">Guardar Tienda</button>
                        <% } %>
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

        const estaViendo = <%= viendo %>;

        // Guardamos copias originales
        const todosLosMunicipios = Array.from(municipioSelect.querySelectorAll('option'));
        const todasLasLocalidades = Array.from(localidadSelect.querySelectorAll('option'));

        function comprobarCapital() {
            // Cogemos la opción que está seleccionada ahora mismo
            const opcionSeleccionada = zonaSelect.options[zonaSelect.selectedIndex];

            // Comprobamos si el texto dice malaga capital
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

            if (!estaViendo) {
                municipioSelect.disabled = false;
            }
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

            if (!estaViendo) {
                localidadSelect.disabled = false;
            }

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

        // --- Lógica para modo editar
        if (zonaSelect.value) {
            filtrarMunicipios();
            <% if ((editando || viendo) && tiendaActual != null) { %>
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