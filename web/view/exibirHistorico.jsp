<%-- 
    Document   : exibirHistorico
    Created on : Oct 17, 2016, 3:10:59 PM
    Author     : daniel
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAssoc.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1 class="page-header">Histórico</h1>
<h4><b>Código do associado:  <%=session.getAttribute("codigo")%> </b> </h4>
<h3>Publicações Devolvidas</h3>

<table class="table table-bordered">
    <thead>
        <tr>
            <th>Codigo Empréstimo</th>
            <th>ISBN</th>
            <th>Título</th>
            <th>Número do exemplar</th>
            <th>Editora</th>
            <th>Ano</th>
            <th>Data do empréstimo</th>
            <th>Data de devolução</th>
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
<h3>Publicações Nao Devolvidas</h3>

<table class="table table-bordered">
    <thead>
        <tr>
            <th>Codigo Empréstimo</th>
            <th>ISBN</th>
            <th>Título</th>
            <th>Número do exemplar</th>
            <th>Editora</th>
            <th>Ano</th>
            <th>Data do empréstimo</th>
            <th>Prazo de devolução</th>
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

<% //Removendo os atributos da consulta que foram atribuídos à sessão
    if (session.getAttribute("listaDevolvida") != null) {
        session.removeAttribute("listaDevolvida");
    }
    if (session.getAttribute("listaNaoDevolvida") != null) {
        session.removeAttribute("listaNaoDevolvida");
    }
%>
<%@ include file="../footer/footer.jsp"%>
