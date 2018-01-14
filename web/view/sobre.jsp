<%-- 
    Document   : sobre
    Created on : Oct 9, 2016, 12:23:42 AM
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
<h1 class="page-header">COM222 - Trabalho 2</h1>
<h2>
    Equipe:
</h2>
<h3>
    <p>
        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        Daniel Massayuki Shinkai - 30469
    </p>
    <p>
        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        Igor Felipe dos Santos - 31101
    </p>
    <p>
        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        Patrick Perroni Pereira - 31975
    </p>
    <p>
        <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        Phelip Roberto Ribeiro de Souza - 30273
    </p>
</h3>

<%@ include file="../footer/footer.jsp"%>
