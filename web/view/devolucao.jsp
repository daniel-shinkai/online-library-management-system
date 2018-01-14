<%-- 
    Document   : devolucao
    Created on : Oct 9, 2016, 11:02:30 PM
    Author     : Igor Santos
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<h1 class="page-header">Devolução</h1>
<form method="post" action="../validarDevolucao">
    <div class="form-group row">
        <label for="numero" class="col-xs-2 col-form-label">Número do exemplar</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="number" id="number" min="1" max="9999999999999" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="isbn" class="col-xs-2 col-form-label">ISBN</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="isbn" id="isbn" min="1" max="9999999999999">
            <small class="form-text text-muted">Você pode optar por inserir somente o ISBN do livro, ignorando o título.</small>
        </div>
    </div>
    <div class="form-group row">
        <label for="titulo" class="col-xs-2 col-form-label">Título</label>
        <div class="col-xs-10">
            <input class="form-control" type="text" name="titulo" id="titulo">
            <small class="form-text text-muted">Você pode optar por inserir somente o título do livro, ignorando o ISBN.</small>
        </div>
    </div>
    <div class="form-group row">
        <label for="codigo" class="col-xs-2 col-form-label">Código do associado</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="codigo" id="codigo" min="1" max="99999999999999" required>
        </div>
    </div>
    <center>
        <button type="submit" class="btn btn-primary">Devolver</button>
        <button class="btn btn-danger" type="reset">Reset</button>
    </center>
</form>
<%@ include file="../footer/verificarErro.jsp"%>
<%@ include file="../footer/footer.jsp"%>
