<%-- 
    Document   : avaliaSessao
    Created on : Oct 12, 2016, 1:27:20 AM
    Author     : Igor Santos
--%>

<%
    /*
        Verifica se o usuário já efetuou o login para poder estar acessando a página
    */
    if (session.getAttribute("codigo") == null) {
        session.setAttribute("msgErro", "Necessário efetuar o login");
        response.sendRedirect("../index.jsp");
    }
%>
