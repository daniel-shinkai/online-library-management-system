<%-- 
    Document   : exibeConsultaPublicacao
    Created on : Oct 15, 2016, 7:05:53 PM
    Author     : Igor Santos
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%  String endereco = "../header/h_principalAssoc.jsp";
    if (session.getAttribute("tipoUsuario") != null) {
        String tipoUsuario = session.getAttribute("tipoUsuario").toString();
        if (tipoUsuario.equals("admin")) {
            endereco = "../header/h_principalAdmin.jsp";
        }
    }
%>
<jsp:include page="<%=endereco%>" flush="true"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1 class="page-header">Consulta de publicação</h1>
<h4><b>Título:</b> ${publicacao.titulo}</h4>
<h4><b>ISBN:</b> ${publicacao.isbn}</h4>
<h4><b>Autor:</b> ${publicacao.autor}</h4>
<h4><b>Editora:</b> ${publicacao.editora}</h4>
<h4><b>Ano:</b> ${publicacao.ano}</h4>
<h2>Status dos exemplares</h2>
<table class="table table-bordered">
    <thead>
        <tr>
            <th>Número</th>
            <th>Preco</th>
            <th>Status</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${statusEx}" var="ex">
            <tr>
                <td><c:out value="${ex.numero}"/></td>
                <td><c:out value="${ex.preco}"/></td>
                <td><c:out value="${ex.status}"/></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<% //Removendo os atributos da consulta que foram atribuídos à sessão
    if (session.getAttribute("publicacao") != null) {
        session.removeAttribute("publicacao");
    }
    if (session.getAttribute("statusEx") != null) {
        session.removeAttribute("statusEx");
    }
%>
<%@ include file="../footer/footer.jsp"%>
