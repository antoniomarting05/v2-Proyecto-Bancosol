<%--
Javier Urbaneja Benítez: 100%
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inicie sesión</title>
    <link rel="stylesheet" href="/css/auth.css">
</head>
<%
    String error = (String) request.getAttribute("error");
%>
<body class="auth-page">
    <div class="login-container">
        <div class="login-card">
            <h2 class="login-title">Iniciar sesión</h2>

            <% if(error != null) { %>
                <div class="error-message">
                    <%= error %>
                </div>
            <% } %>

            <form action="/login" method="post" class="login-form">
                <div class="form-group">
                    <label for="username">Usuario</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="btn-login">
                    Entrar
                </button>
            </form>
        </div>
    </div>
</body>
</html>