<%-- 
    Document   : resultadoDevolucao
    Created on : Oct 19, 2016, 4:17:18 PM
    Author     : daniel
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

<%
    /*Obtém o título do cadastro e informa ao usuário que o cadastro foi efetudo com sucesso.*/
    String multa = "";
    String mensagemMulta = "";
    
    if (session.getAttribute("multa") != null) {
        mensagemMulta = session.getAttribute("multa").toString();
%>
<div class="alert alert-warning" role="alert">
    <strong>Atraso na Devoluçao: </strong> <%=mensagemMulta%>
</div>

<%
        /*Obtém o título do cadastro e informa ao usuário que o cadastro foi efetudo com sucesso.*/
    }
        
    session.removeAttribute("multa");
        
%>

<%@ include file="../footer/footer.jsp"%>