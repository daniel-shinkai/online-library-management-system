<%-- 
    Document   : gerarHistorico
    Created on : Oct 17, 2016, 6:20:47 PM
    Author     : daniel
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAssoc.jsp"%>

<h1 class="page-header">Visualizar hist�rico</h1>
<form method="post" action="../validarHistorico">
    <h3> Nessa p�gina voc� poder� gerar o hist�rico de empr�stimos </h3>
    <br>
    <center>
        <button type="submit" class="btn btn-primary">Gerar Hist�rico</button>
    </center>
</form>
<!-- Verifica se houve algum erro durante a opera��o e informa ao usu�rio -->
<%@ include file="../footer/verificarErro.jsp"%>
<%@ include file="../footer/footer.jsp"%>