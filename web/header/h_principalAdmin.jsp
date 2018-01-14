<%-- 
    Document   : h_principalAdmin
    Created on : Oct 8, 2016, 1:43:36 PM
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
        <link rel="icon" href="favicon.ico">

        <title>Página de Administrador</title>

        <!-- Bootstrap core CSS -->
        <link href="../Bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <link href="../Bootstrap/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="../Bootstrap/css/dashboard.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a style="color:white;" class="navbar-brand">Olá, <%=session.getAttribute("nome")%>!</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                            <a class="navbar-brand" href="${pageContext.request.contextPath}/logout">
                            Sair <span style="color:white;" class="glyphicon glyphicon-log-out"></span>
                        </a>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/view/home.jsp">
                                <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                                Home <span class="sr-only">(current)</span>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/view/sobre.jsp">
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                                Sobre
                            </a>
                        </li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li><a href="${pageContext.request.contextPath}/view/cadastroAssociado.jsp">Cadastrar associado</a></li>
                        <li><a href="${pageContext.request.contextPath}/view/cadastroPublicacao.jsp">Cadastrar publicação</a></li>
                        <li><a href="${pageContext.request.contextPath}/view/cadastroExemplar.jsp">Cadastrar exemplar</a></li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li><a href="${pageContext.request.contextPath}/view/consultaPublicacao.jsp">Consultar publicação</a></li>
                        <li><a href="${pageContext.request.contextPath}/view/emprestimo.jsp">Emprestar</a></li>
                        <li><a href="${pageContext.request.contextPath}/view/devolucao.jsp">Devolver</a></li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li><a href="${pageContext.request.contextPath}/view/geraRelatorio.jsp">Gerar relatório</a></li>
                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

