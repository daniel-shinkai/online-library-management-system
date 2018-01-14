<%-- 
    Document   : consultaPublicacao
    Created on : Oct 9, 2016, 3:51:19 AM
    Author     : Igor Santos
--%>

<%@ include file="../header/validarSessao.jsp"%>
<%  String endereco = "../header/h_principalAssoc.jsp";
    if (session.getAttribute("tipoUsuario") != null) {
        String tipoUsuario = session.getAttribute("tipoUsuario").toString();
        if (tipoUsuario.equals("admin")) {
            endereco = "../header/h_principalAdmin.jsp";
        }
    }
%>
<jsp:include page="<%=endereco%>" flush="true"/>
<h1 class="page-header">Consulta de publicação</h1>
<form method="post" action="../validarConsultaPublicacao">
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
    <center>
        <button type="submit" class="btn btn-primary">Consultar</button>
        <button class="btn btn-danger" type="reset">Reset</button>
    </center>
</form>
<!-- Verifica se houve algum erro durante a operação e informa ao usuário -->
<%@ include file="../footer/verificarErro.jsp"%>
<%@ include file="../footer/footer.jsp"%>