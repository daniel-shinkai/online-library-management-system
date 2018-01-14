<%-- 
    Document   : cadastroExemplar
    Created on : Oct 9, 2016, 3:32:07 AM
    Author     : Phelip Roberto
--%>
<%@page import="model.CookieUtilities"%>
<%    //Obtendo por meio de cookies os valores que foram inseridos nos campos do cadastro que falhou
    String numero = CookieUtilities.getCookieValue(request, "numero3", "");
    String isbn = CookieUtilities.getCookieValue(request, "isbn3", "");
    String preco = CookieUtilities.getCookieValue(request, "preco3", "");
%>
<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<h1 class="page-header">Cadastro de exemplar</h1>
<form method="post" action="../validarCadastroExemplar">
    <div class="form-group row">
        <label for="numero" class="col-xs-2 col-form-label">Número de sequência</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="numero" id="numero" min="1" max="9999999999999" value="<%=numero%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="isbn" class="col-xs-2 col-form-label">ISBN</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="isbn" id="isbn" min="1" max="9999999999999" value="<%=isbn%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="preco" class="col-xs-2 col-form-label">Preço</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" min="0" step="0.01" name="preco" id="preco" value="<%=preco%>" required>
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
