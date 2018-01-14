<%-- 
    Document   : exibirHistorico
    Created on : Oct 17, 2016, 3:10:59 PM
    Author     : daniel
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAssoc.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1 class="page-header">Hist�rico</h1>
<h4><b>C�digo do associado:  <%=session.getAttribute("codigo")%> </b> </h4>
<h3>Publica��es Devolvidas</h3>

<table class="table table-bordered">
    <thead>
        <tr>
            <th>Codigo Empr�stimo</th>
            <th>ISBN</th>
            <th>T�tulo</th>
            <th>N�mero do exemplar</th>
            <th>Editora</th>
            <th>Ano</th>
            <th>Data do empr�stimo</th>
            <th>Data de devolu��o</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${listaDevolvida}" var="pub">
            <tr>
                <td>${pub.cod_emprestimo}</td>
                <td>${pub.ISBN_exemplar}</td>
                <td>${pub.titulo}</td>
                <td>${pub.num_exemplar}</td>
                <td>${pub.editora}</td>
                <td>${pub.ano}</td>
                <td>${pub.data_emp}</td>
                <td>${pub.data_dev}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<br>
<h3>Publica��es Nao Devolvidas</h3>

<table class="table table-bordered">
    <thead>
        <tr>
            <th>Codigo Empr�stimo</th>
            <th>ISBN</th>
            <th>T�tulo</th>
            <th>N�mero do exemplar</th>
            <th>Editora</th>
            <th>Ano</th>
            <th>Data do empr�stimo</th>
            <th>Prazo de devolu��o</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${listaNaoDevolvida}" var="pub">
            <tr>
                <td>${pub.cod_emprestimo}</td>
                <td>${pub.ISBN_exemplar}</td>
                <td>${pub.titulo}</td>
                <td>${pub.num_exemplar}</td>
                <td>${pub.editora}</td>
                <td>${pub.ano}</td>
                <td>${pub.data_emp}</td>
                <td>${pub.data_dev}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<br>

<% //Removendo os atributos da consulta que foram atribu�dos � sess�o
    if (session.getAttribute("listaDevolvida") != null) {
        session.removeAttribute("listaDevolvida");
    }
    if (session.getAttribute("listaNaoDevolvida") != null) {
        session.removeAttribute("listaNaoDevolvida");
    }
%>
<%@ include file="../footer/footer.jsp"%>
