<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.AsignacionTurno" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.TipoCampanya" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Campanya" %><%-- Created by IntelliJ IDEA. User: javie Date: 18/04/2026 Time: 23:35 To
change this template use File | Settings | File Templates. --%> <%@ page
contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Asignación</title>
  <link rel="stylesheet" href="/css/global.css" />
  <link rel="stylesheet" href="/css/asignacion_turno.css" />
</head>
<body>
  <%
    List<AsignacionTurno> tiendas = (List<AsignacionTurno>) request.getAttribute("asignacionesTurno");
    List<TipoCampanya> tipoCampanyas = (List<TipoCampanya>) request.getAttribute("tipoCampanyas");
    List<Campanya> campanyas = (List<Campanya>) request.getAttribute("campanyas");

    Integer tipoCampanyaActual = (Integer) request.getAttribute("tipoCampanyaActual");
    Integer campanyaActual = (Integer) request.getAttribute("campanyaActual");

  %>
<jsp:include page="../shared/navbar.jsp"/>

  <div class="filtros-container">
    <div class="filtro-group">
      <label for="selectTipoCampanya">Tipo de Campaña</label>
      <select name="tipoCampanya" id="selectTipoCampanya">
        <option value="0" <%=tipoCampanyaActual == 0 ? "selected" : ""%>>Sin Filtro</option>
        <%for (TipoCampanya tipo: tipoCampanyas){%>
        <option value="<%=tipo.getId()%>" <%=tipoCampanyaActual == tipo.getId() ? "selected" : ""%>><%=tipo.getNombre()%></option>
        <%}%>
      </select>
    </div>

    <div class="filtro-group">
      <label for="selectCampanya">Campaña</label>
      <select name="campaña" id="selectCampanya">
        <option value="0" <%=campanyaActual == 0 ? "selected" : ""%>>Sin Filtro</option>
        <%for (Campanya campanya: campanyas){%>
        <option value="<%=campanya.getId()%>" <%=campanyaActual == campanya.getId() ? "selected" : ""%>><%=campanya.getNombre()%></option>
        <%}%>
      </select>
    </div>
  </div>

<main class="page-wrapper">

  <div class="left-column">

    <div class="table-container card">

      <table class="modernTable">
        <thead>
        <tr>
          <th>Tienda</th>
          <th>Domicilio</th>
          <th>Localidad</th>
          <th>Capitán</th>
          <th>Viernes M.</th>
          <th>Viernes T.</th>
          <th>Sábado M.</th>
          <th>Sábado T.</th>
        </tr>
        </thead>
        <tbody id="table-body">
        <% for(AsignacionTurno tienda: tiendas ) { %>
        <tr data-id="<%= tienda.getIdTiendaCampanya() %>" data-li="<%= tienda.getLineales()%>">
          <td class="font-medium text-blue"><%= tienda.getTienda() %></td>
          <td><%= tienda.getDomicilio() %> </td>
          <td><%= tienda.getLocalidad() %></td>
          <td><%= tienda.getCapitan() %> </td>
          <td class="small-td"><%= tienda.getViernesManana() != null ? tienda.getViernesManana().replaceAll("\\s+(L\\d+)", "<br>$1") : "" %></td>
          <td class="small-td"><%= tienda.getViernesTarde() != null ? tienda.getViernesTarde().replaceAll("\\s+(L\\d+)", "<br>$1") : ""  %></td>
          <td class="small-td"><%= tienda.getSabadoManana() != null ? tienda.getSabadoManana().replaceAll("\\s+(L\\d+)", "<br>$1") : ""  %></td>
          <td class="small-td"><%= tienda.getSabadoTarde() != null ? tienda.getSabadoTarde().replaceAll("\\s+(L\\d+)", "<br>$1") : "" %></td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>
  </div>

  <div class="right-column">
    <div id="info-container" class="card side-panel">
    </div>
  </div>
</main>

  <jsp:include page="../shared/footer.jsp"/>

  <script>
    // Lógica del desplegable de la derecha:
	const table = document.querySelector("#table-body");
	const form = document.querySelector("#volunteer-container");
    const infoContainer = document.querySelector("#info-container");
    const rightColumn = document.querySelector(".right-column");
    infoContainer.innerHTML = "";

    let id;
    let lineales = 1;
    let linealActual = 1;
    let turno = 1;


    function fetchTurnoData() {
      if (!id) return;

      const params = new URLSearchParams();
      params.append("id", id);
      params.append("turno", turno);
      params.append("lineales", lineales);
      params.append("linealActual", linealActual);


      fetch("/turnos/buscarTurno", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        body: params.toString()
      })
              .then(response => response.text())
              .then(html => {
                // Reemplazamos el HTML con la nueva información
                infoContainer.innerHTML = html;

                // Hacemos visible el formulario
                const form = document.querySelector("#volunteer-container");
                if(form) form.style.visibility = 'visible';
              })
              .catch(error => console.error("Error:", error));
    }

	table.addEventListener("click", (e) => {
		const row = e.target.closest("tr");
		if (!row) return; // Se ha pinchado en otro lado

        if (row.classList.contains("selected")) {
          row.classList.remove("selected");
          rightColumn.classList.remove("open");
          id = null;
          return;
        }

        // Cambiamos el id y los lineales de la tienda
        id = row.dataset.id;
        lineales = row.dataset.li;

        // Ponemos como turno por defecto el Viernes por la mañana y el lineal 1
        turno = 1;
        linealActual = 1;

		// Limpiamos la clase selected de las otras filas
		table.querySelectorAll("tr").forEach(row => row.classList.remove("selected"));

		// Se la metemos a la clase seleccionada
		row.classList.add("selected");

        rightColumn.classList.add("open");

        // Hacemos la petición para obtener los datos de la tienda y el turno 1
        fetchTurnoData();
	})

    // Usamos delegación de eventos en #info-container para los radio buttons
    // ya que su HTML se destruye y se vuelve a crear con cada petición fetch
    infoContainer.addEventListener("change", (e) => {
      if (e.target.name === "schedule") {
        turno = e.target.value;
        fetchTurnoData(); // Volvemos a pedir los datos con el nuevo turno
      }
    });

    infoContainer.addEventListener("change", (e) => {
      if (e.target.name === "lineal") {
        linealActual = e.target.value;
        fetchTurnoData(); // Volvemos a pedir los datos con el nuevo turno
      }
    })

    // Delegación de eventos para el botón de cancelar
    infoContainer.addEventListener("click", (e) => {
      if (e.target.id === "create-button") {

        if (id && turno && linealActual) {
          const params = new URLSearchParams();
          params.append("id", id);
          params.append("turno", turno);
          params.append("lineal", linealActual);

          // Usamos backticks (`) para que la variable se evalúe correctamente
          window.location.href = `/turnos/crearTurno?\${params.toString()}`;
        } else {
          console.error("Faltan datos para navegar");
        }
      }

      if (e.target.id === "cancel-button") {
        e.preventDefault();

        rightColumn.classList.remove("open");

        // Retrasamos un poquito el borrado del contenido para que no desaparezca a mitad de la animación
        setTimeout(() => {
          infoContainer.innerHTML = "";
        }, 300);

        // Limpiamos los datos
        turno = 1;
        id = null;
        lineales = 1;
        table.querySelectorAll("tr").forEach(row => row.classList.remove("selected"));
      }
    });




    // Lógica del filtrado de arriba
    const tipoCampanyaSelect = document.querySelector("#selectTipoCampanya");
    const campanyaSelect = document.querySelector("#selectCampanya");

    function fetchFiltros() {
      const tipoCampanyaId = tipoCampanyaSelect.value;
      const campanyaId = campanyaSelect.value;

      window.location.href = "/turnos/filtrar?tipoCampanyaId="+tipoCampanyaId + "&campanyaId=" + campanyaId;
    }

    tipoCampanyaSelect.addEventListener("change", fetchFiltros);

    campanyaSelect.addEventListener("change", fetchFiltros);
  </script>
</body>
</html>
