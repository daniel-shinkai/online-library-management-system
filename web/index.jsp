<%-- 
    Document   : index
    Created on : Sep 30, 2016, 11:10:24 PM
    Author     : Igor Santos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Página de Login</title>
        <!-- Bootstrap core CSS -->
        <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="Bootstrap/css/signin.css" rel="stylesheet">
    </head>
    <body>
        <!-- Fixed navbar -->
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-right">
                    <a class="navbar-brand" href="view/loginAdmin.jsp">Acessar como administrador</a>
                </div>
                <a style="color:black;" class="navbar-brand">[Associado]</a>
            </div>
        </nav>
        <div class="container">
            <form class="form-signin" method="post" action="./validarLogin">
                <h2 class="form-signin-heading">Acesse sua conta</h2>
                <label for="codigo" class="sr-only">Código</label>
                <input type="text" name="codigo" id="codigo" class="form-control" placeholder="Código" required autofocus>
                <label for="senha" class="sr-only">Senha</label>
                <input type="password" name="senha" id="senha" class="form-control" placeholder="Senha" required>
                <input type="hidden" name="tipoUsuario" id="tipoUsuario" value="associado">
                <button class="btn btn-lg btn-primary btn-block" type="submit">Acessar</button>
                <!-- Verifica se houve algum erro durante a operação e informa ao usuário -->
                <%@ include file="footer/verificarErro.jsp"%>
            </form>
        </div> <!-- /container -->
    </body>
</html>
