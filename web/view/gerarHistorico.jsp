<%-- 
    Document   : gerarHistorico
    Created on : Oct 17, 2016, 6:20:47 PM
    Author     : daniel
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAssoc.jsp"%>

<h1 class="page-header">Visualizar histórico</h1>
<form method="post" action="../validarHistorico">
    <h3> Nessa página você poderá gerar o histórico de empréstimos </h3>
    <br>
    <center>
        <button type="submit" class="btn btn-primary">Gerar Histórico</button>
    </center>
</form>
<!-- Verifica se houve algum erro durante a operação e informa ao usuário -->
<%@ include file="../footer/verificarErro.jsp"%>
<%@ include file="../footer/footer.jsp"%>