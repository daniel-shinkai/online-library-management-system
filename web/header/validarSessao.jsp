<%-- 
    Document   : avaliaSessao
    Created on : Oct 12, 2016, 1:27:20 AM
    Author     : Igor Santos
--%>

<%
    /*
        Verifica se o usu�rio j� efetuou o login para poder estar acessando a p�gina
    */
    if (session.getAttribute("codigo") == null) {
        session.setAttribute("msgErro", "Necess�rio efetuar o login");
        response.sendRedirect("../index.jsp");
    }
%>
