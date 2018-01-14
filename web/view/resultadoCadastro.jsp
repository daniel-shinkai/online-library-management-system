<%-- 
    Document   : resultadoCadastro
    Created on : Oct 12, 2016, 6:50:09 PM
    Author     : Igor Santos
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<%
    /*Obtém o título do cadastro e informa ao usuário que o cadastro foi efetudo com sucesso.*/
    String titulo = "";
    String msg = "";
    if (session.getAttribute("titulo") != null && session.getAttribute("msg") != null) {
        titulo = session.getAttribute("titulo").toString();
        msg = session.getAttribute("msg").toString();
        session.removeAttribute("titulo");
        session.removeAttribute("msg");
    }
%>
<h1 class="page-header"><%=titulo%></h1>
<div class="alert alert-success" role="alert">
    <strong>Operação finalizada!</strong> <%=msg%>
</div>

<%@ include file="../footer/footer.jsp"%>
