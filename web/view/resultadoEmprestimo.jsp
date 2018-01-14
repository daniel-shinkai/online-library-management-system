<%-- 
    Document   : resultadoEmrprestimo
    Created on : Oct 19, 2016, 4:34:36 PM
    Author     : daniel
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<%
    /*Obt�m o t�tulo do cadastro e informa ao usu�rio que o cadastro foi efetudo com sucesso.*/
    String titulo = "";
    String msg = "";
    String dataDevol = "";
    if (session.getAttribute("titulo") != null && session.getAttribute("msg") != null && session.getAttribute("dataDevol") != null) {
        titulo = session.getAttribute("titulo").toString();
        msg = session.getAttribute("msg").toString();
        dataDevol = session.getAttribute("dataDevol").toString();
        session.removeAttribute("titulo");
        session.removeAttribute("msg");
        session.removeAttribute("dataDevol");
    }
%>
<h1 class="page-header"><%=titulo%></h1>
<div class="alert alert-success" role="alert">
    <strong>Opera��o finalizada!</strong> <%=msg%>
</div>
<div class="alert alert-success" role="alert">
    <strong>Prazo de Devolu��o: </strong> <%=dataDevol%>
</div>

<%@ include file="../footer/footer.jsp"%>
