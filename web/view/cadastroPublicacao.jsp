<%-- 
    Document   : cadastroPublicacao
    Created on : Oct 8, 2016, 11:38:40 PM
    Author     : Igor Santos
--%>

<%@page import="model.CookieUtilities"%>
<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<%    //Obtendo por meio de cookies os valores que foram inseridos nos campos do cadastro que falhou
    String isbn = CookieUtilities.getCookieValue(request, "isbn2", "");
    String titulo = CookieUtilities.getCookieValue(request, "titulo2", "");
    String autor = CookieUtilities.getCookieValue(request, "autor2", "");
    String editora = CookieUtilities.getCookieValue(request, "editora2", "");
    String ano = CookieUtilities.getCookieValue(request, "ano2", "");
%>
<h1 class="page-header">Cadastro de publicação</h1>
<form method="post" action="../validarCadastroPublicacao">
    <div class="form-group row">
        <label for="isbn" class="col-xs-2 col-form-label">ISBN</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="isbn" id="isbn" min="1" max="9999999999999"  value="<%=isbn%>"required>
        </div>
    </div>
    <div class="form-group row">
        <label for="titulo" class="col-xs-2 col-form-label">Título</label>
        <div class="col-xs-10">
            <input class="form-control" type="text" name="titulo" id="titulo" value="<%=titulo%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="autor" class="col-xs-2 col-form-label">Autor</label>
        <div class="col-xs-10">
            <input class="form-control" type="text" name="autor" id="autor" value="<%=autor%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="editora" class="col-xs-2 col-form-label">Editora</label>
        <div class="col-xs-10">
            <input class="form-control" type="text" name="editora" id="editora" value="<%=editora%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="ano" class="col-xs-2 col-form-label">Ano de publicação</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" min="1" max="2016" name="ano" id="ano" value="<%=ano%>" required>
        </div>
    </div>
    <center>
        <button type="submit" class="btn btn-primary">Cadastrar</button>
        <button class="btn btn-danger" type="reset">Reset</button>
    </center>
</form>
<!-- Verifica se houve algum erro durante a operação e informa ao usuário -->
<%@ include file="../footer/verificarErro.jsp"%>
<%@ include file="../footer/footer.jsp"%>
