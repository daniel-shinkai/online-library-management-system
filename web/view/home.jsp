<%-- 
    Document   : principalAdmin
    Created on : Oct 2, 2016, 10:22:47 PM
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
<h1 class="page-header">
    <span class="glyphicon glyphicon-book" aria-hidden="true"></span>
    Sistema de Controle de Biblioteca
</h1>
<h3>
    <p>
        Para utilizar esse sistema: 
    </p>
    <p>
        <b>1.</b> Selecione a funcionalidade que desejar no menu disponível no canto 
        esquerdo desta página.
    </p>
    <p>
        <b>2.</b> Preencha os campos que forem necessários.
    </p>
</h3>
<center>
    <h1>
        <span class="glyphicon glyphicon-hand-left" aria-hidden="true"></span>
    </h1>
</center>
<%@ include file="../footer/footer.jsp"%>
