<%-- 
    Document   : exibeRelatorio
    Created on : Oct 16, 2016, 2:43:04 AM
    Author     : Igor Santos
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1 class="page-header">Relat�rio</h1>
<c:forEach items="${aAtraso}" var="assoc">
    <h4><b>C�digo do associado:</b> ${assoc.codigo}</h4>
    <h4><b>Nome: </b> ${assoc.nome} ${assoc.sobrenome}</h4>
    <h3>Publica��es em atraso</h3>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ISBN</th>
                <th>T�tulo</th>
                <th>Autor</th>
                <th>N�mero do exemplar</th>
                <th>Editora</th>
                <th>Ano</th>
                <th>Data do empr�stimo</th>
                <th>Prazo de devolu��o</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${assoc.pAtrasada}" var="pub">
                <tr>
                    <td>${pub.isbn}</td>
                    <td>${pub.titulo}</td>
                    <td>${pub.autor}</td>
                    <td>${pub.numExemplar}</td>
                    <td>${pub.editora}</td>
                    <td>${pub.ano}</td>
                    <td>${pub.dataEmp}</td>
                    <td>${pub.prazoDev}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
</c:forEach>
<% //Removendo os atributos da consulta que foram atribu�dos � sess�o
    if (session.getAttribute("aAtraso") != null) {
        session.removeAttribute("aAtraso");
    }
%>
<%@ include file="../footer/footer.jsp"%>