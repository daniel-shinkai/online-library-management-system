<%-- 
    Document   : verificarErro
    Created on : Oct 12, 2016, 6:31:21 PM
    Author     : Igor Santos
--%>

<%
    /*Caso houve falha durante o login, o usuário é retornado para esta página
    com uma mensagem que informa o erro*/
    if (session.getAttribute("msgErro") != null) {
        String msgErro = (String) session.getAttribute("msgErro");
        session.removeAttribute("msgErro");
        out.println("<br><div class='alert alert-danger' role='alert'>");
        out.println("<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span>");
        out.println("<span class='sr-only'>Error:</span>");
        out.println(msgErro);
        out.println("</div>");
    }
%>
