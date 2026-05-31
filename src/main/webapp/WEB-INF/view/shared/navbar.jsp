<%@ page import="com.leftjoiners.bancosol.proyectobackend.dto.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
<link rel="stylesheet" href="/css/navbar.css">

<%
    String section = (String) request.getAttribute("currentSection");
    Usuario usuario = (Usuario) request.getAttribute("user");

    if (section == null) {
        section = "";
    }
%>

<header class="main-header">
    <div class="top-navbar">
        <div class="logo-container">
            <img src="/images/LOGO_BANCOSOL.png" alt="Logo Bancosol" class="logo-img">
        </div>
        <p><%=usuario.getNombre()%></p>

        <div class="user-section">
            <button class="icon-btn">
                <i class="ri-notification-3-line"></i>
            </button>
            <button class="icon-btn">
                <i class="ri-settings-3-line"></i>
            </button>
            <button class="icon-btn">
                <i class="ri-question-line"></i>
            </button>
            <div class="user-avatar"><%=usuario.getNombre().substring(0,1)%></div>
        </div>
    </div>

    <nav class="bottom-navbar">
        <ul class="nav-menu">
            <li class="nav-item <%=section.equals("campanyas") ? "active" : ""%>">
                <a href="/campanyas" class="nav-link">
                    <i class="ri-megaphone-line"></i>
                    <span>Gestión de Campañas</span>
                </a>
            </li>
            <li class="nav-item <%=section.equals("tiendas") ? "active" : ""%>">
                <a href="/tiendas" class="nav-link">
                    <i class="ri-store-2-line"></i>
                    <span>Tiendas</span>
                </a>
            </li>
            <li class="nav-item <%=section.equals("colaboradores") ? "active" : ""%>"> <!-- Esta es la pestaña 'active' por defecto en este ejemplo -->
                <a href="/colaboradores" class="nav-link">
                    <i class="ri-user-heart-line"></i>
                    <span>Colaboradores</span>
                </a>
            </li>
            <li class="nav-item <%=section.equals("coordinadores") ? "active" : ""%>">
                <a href="/coordinadores" class="nav-link">
                    <i class="ri-team-line"></i>
                    <span>Coordinadores</span>
                </a>
            </li>
            <li class="nav-item <%=section.equals("turnos") ? "active" : ""%>">
                <a href="/turnos" class="nav-link">
                    <i class="ri-clipboard-line"></i>
                    <span>Asignación de turno</span>
                </a>
            </li>
        </ul>

        <div class="logout-section">
            <a href="/logout" class="logout-link">
                <i class="ri-logout-box-r-line"></i>
                <span>Cerrar Sesión</span>
            </a>
        </div>
    </nav>
</header>