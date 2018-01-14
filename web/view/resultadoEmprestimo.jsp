<%-- 
    Document   : resultadoEmrprestimo
    Created on : Oct 19, 2016, 4:34:36 PM
    Author     : daniel
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<%
    /*Obtém o título do cadastro e informa ao usuário que o cadastro foi efetudo com sucesso.*/
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
    <strong>Operação finalizada!</strong> <%=msg%>
</div>
<div class="alert alert-success" role="alert">
    <strong>Prazo de Devolução: </strong> <%=dataDevol%>
</div>

<%@ include file="../footer/footer.jsp"%>
