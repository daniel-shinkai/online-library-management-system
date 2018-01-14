<%-- 
    Document   : geraRelatorio
    Created on : Oct 16, 2016, 2:13:32 AM
    Author     : Igor Santos
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<h1 class="page-header">Relatório</h1>
<form method="post" action="../validarRelatorio">
    <div class="form-group row">
        <label for="codigo" class="col-xs-2 col-form-label">Código do associado</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="codigo" id="codigo" min="1" max="99999999999999">
        </div>
    </div>
    <center>
        <button type="submit" class="btn btn-primary">Gerar</button>
        <button class="btn btn-danger" type="reset">Reset</button>
    </center>
</form>
<!-- Verifica se houve algum erro durante a operação e informa ao usuário -->
<%@ include file="../footer/verificarErro.jsp"%>
<%@ include file="../footer/footer.jsp"%>
