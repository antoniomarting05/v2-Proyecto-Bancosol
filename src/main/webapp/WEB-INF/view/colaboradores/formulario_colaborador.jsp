<%--
Marina Fernández Serrano: 100%
--%>

<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Colaborador" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.ContactoColaborador" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Localidad" %>
<%@ page import="java.util.List" %>
<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Usuario" %><%--
  Created by IntelliJ IDEA.
  User: marin
  Date: 25/05/2026
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulario Colaborador</title>
    <link rel="stylesheet" href="/css/global.css" />
    <link rel="stylesheet" href="/css/formularioColaboradores.css">
</head>
<body>
    <%Colaborador colaborador = (Colaborador) request.getAttribute("colaborador");
    Boolean editando = (Boolean) request.getAttribute("editando");
    List<Localidad> localidades = (List<Localidad>) request.getAttribute("localidades");
    List<Usuario> coordinadores = (List<Usuario>) request.getAttribute("coordinadores");
    %>
    <jsp:include page="../shared/navbar.jsp"/>
    <main class="main-page">
        <section class="form-wrapper">
            <div class="form-header">
                <div>
                    <h2>Datos del <%=editando ? "colaborador" : "nuevo colaborador"%></h2>
                    <p>Rellena la información del colaborador.</p>
                </div>
            </div>

            <div class="card form-card">
                <form action="/colaboradores/guardar" method="post" class="colaborador-form" id="formColaborador">
                    <%if(editando) {%>
                        <input type="hidden" name="id" value="<%=colaborador.getId()%>">
                    <%}%>


                    <section class="form-section">
                        <div class="form-row">
                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" id="nombre" name="nombre" value="<%=editando ? colaborador.getNombre() : ""%>" required>
                            </div>
                            <div class="form-group">
                                <label for="codigo">Código</label>
                                <input type="text" id="codigo" name="codigo" value="<%=editando ? colaborador.getCodigo() : ""%>" required>
                            </div>
                            <div class="form-group">
                                <label>¿Es temporal?</label>
                                <div class="radio-options">
                                    <input type="radio" name="temporal" value="true" <%=editando && colaborador.getTemporal() ? "checked" : "" %>> Sí
                                    <input type="radio" name="temporal" value="false" <%=editando && !colaborador.getTemporal() ? "checked" : "" %>> No
                                </div>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="domicilio">Domicilio</label>
                                <input type="text" id="domicilio" name="domicilio" value="<%=editando? colaborador.getDomicilio() : ""%>">
                            </div>
                            <div class="form-group">
                                <label for="cp">Código Postal</label>
                                <input type="text" id="cp" name="cp" value="<%= editando ? colaborador.getCp() : ""%>">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="localidadSede">Localidad de la sede:</label>
                                <select name="localidadSede" id="localidadSede">
                                    <%for(Localidad l : localidades) {%>
                                        <option value="<%=l.getId()%>" <%=editando && colaborador.getLocalidadSede().getId() == l.getId() ? "selected" : ""%>><%=l.getNombre()%></option>
                                    <%}%>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="colaboraEn">Colabora en:</label>
                                <select name="colaboraEn" id="colaboraEn">
                                    <%for(Localidad l : localidades) {%>
                                        <option value="<%=l.getId()%>" <%=editando && colaborador.getColaboraEn().getId() == l.getId() ? "selected" : ""%>><%=l.getNombre()%></option>
                                    <%}%>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="coordinador">Coordinador:</label>
                                <select name="coordinador" id="coordinador">
                                    <%for(Usuario u : coordinadores) {%>
                                        <option value="<%=u.getId()%>" <%=editando && colaborador.getCoordinador().getId() == u.getId() ? "selected" : ""%>><%=u.getNombre()%></option>
                                    <%}%>
                                </select>
                            </div>
                        </div>

                        <div class="form-group contact-span">
                            <p>Contactos</p>
                            <table class="contact-table">
                                <%if (editando) { %>
                                <thead>
                                    <tr>
                                        <th>Nombre</th>
                                        <th class="columna-centrada">Contacto Principal</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <%
                                    for (ContactoColaborador c : colaborador.getContactos()) {%>

                                        <tr>
                                            <td><%=c.getNombre()%></td>
                                            <td class="columna-centrada">
                                                <input type="radio" name="contactoPrincipal" value="<%=c.getNombre()%>" <%=c.getNombre().equals(colaborador.getContactoPrincipal()) ? "checked" : ""%>>
                                            </td>
                                            <td class="columna-centrada">
                                                <a class="btn-outline" href="/colaboradores/editarContacto?id=<%=c.getId()%>"> Editar </a>
                                                <a class="btn-danger-outline" href="/colaboradores/eliminarContacto?id=<%=c.getId()%>"> Eliminar</a>
                                            </td>
                                        </tr>

                                    <%
                                            }%>
                                        <tr>
                                            <td class="anadir-contacto" colspan="3">
                                                <a href="/colaboradores/anadirContacto?id=<%=colaborador.getId()%>"> + Añadir contacto</a>
                                            </td>
                                        </tr>
                                    <%
                                        } else { %>
                                        <thead>
                                        <tr>
                                            <th colspan="3">
                                        </tr>
                                        </thead>
                                        <tbody>
                                           <tr>
                                               <td colspan="3">
                                                   <p class="text-mutex">Añadir primer contacto (será el principal)</p>
                                                   <div class="form-row">
                                                       <div class="form-group">
                                                           <input type="text" name="nuevoContactoNombre" placeholder="Nombre completo" required>
                                                       </div>
                                                       <div class="form-group">
                                                           <input type="email" name="nuevoContactoEmail" placeholder="Correo electrónico" required>
                                                       </div>
                                                       <div class="form-group">
                                                           <input type="tel" name="nuevoContactoTelefono" placeholder="Teléfono" required>
                                                       </div>
                                                   </div>
                                               </td>
                                           </tr>
                                    <%}%>
                                </tbody>
                            </table>

                        </div>

                        <div class="form-group">
                            <label for="observaciones">Observaciones</label>
                            <input type="text" id="observaciones" name="observaciones" value="<%= editando ? (colaborador.getObservaciones() != null ? colaborador.getObservaciones() : "" ) : ""%>">
                        </div>
                    </section>

                    <section class="form-actions">
                        <a href="/colaboradores" class="btn-danger-outline">Cancelar</a>
                        <button type="submit" class="btn-primary">Guardar</button>
                    </section>
                </form>
            </div>
        </section>
    </main>
</body>
<script>
    document.querySelector("#formColaborador").addEventListener("submit", function(event) {
        const editando = <%=editando%>;

        if(editando) {
            const contactos = document.querySelectorAll('input[name="contactoPrincipal"]');

            if (contactos.length === 0) {
                event.preventDefault();
                alert("Error: El colaborador debe tener al menos un contacto asignado.")
                window.scrollTo(0,0);
                return;
            }

            let seleccionado = false;
            for (let i = 0; i < contactos.length; i++) {
                if (contactos[i].checked) {
                    seleccionado = true;
                    break;
                }
            }

            if (!seleccionado) {
                event.preventDefault();
                alert("Error: Debes marcar un contacto como principal.")
                window.scrollTo(0,0);
                return;
            }
        }

        const temporal = document.querySelectorAll('input[name="temporal"]');
        if (temporal.length == 0) {
            event.preventDefault();
            alert("Error: Debe seleccionar si el colaborador es temporal.")
            window.scrollTo(0,0);
            return;
        }




    })
</script>
</html>
