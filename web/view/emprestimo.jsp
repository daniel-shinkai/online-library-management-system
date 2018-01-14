<%-- 
    Document   : emprestimo
    Created on : Oct 9, 2016, 3:59:24 AM
    Author     : Igor Santos
--%>

<%@page import="model.CookieUtilities"%>
<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<%    //Obtendo por meio de cookies os valores que foram inseridos nos campos do cadastro que falhou
    String numExemplar = CookieUtilities.getCookieValue(request, "numExemplar5", "");
    String isbn = CookieUtilities.getCookieValue(request, "isbn5", "");
    String dia = CookieUtilities.getCookieValue(request, "dia5", "");
    String mes = CookieUtilities.getCookieValue(request, "mes5", "");
    String ano = CookieUtilities.getCookieValue(request, "ano5", "");
    String codAssociado = CookieUtilities.getCookieValue(request, "codAssociado5", "");
%>
<h1 class="page-header">Empréstimo</h1>
<form method="post" action="../validarEmprestimo">
    <div class="form-group row">
        <label for="numero" class="col-xs-2 col-form-label">Número do exemplar</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="number" id="number" min="1" max="9999999999999" value="<%=numExemplar%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="isbn" class="col-xs-2 col-form-label">ISBN</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="isbn" id="isbn" min="1" max="9999999999999" value="<%=isbn%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="data" class="col-xs-2 col-form-label">Data do empréstimo</label>
        <div class="col-xs-2">
            <input class="form-control" type="number" min="1" max="31" name="dia" id="dia" placeholder="dia" value="<%=dia%>" required>
        </div>
        <div class="col-xs-2">
            <input class="form-control" type="number" min="1" max="12" name="mes" id="mes" placeholder="mês" value="<%=mes%>" required>
        </div>
        <div class="col-xs-2">
            <input class="form-control" type="number" min="1900" max="2017" name="ano" id="ano" placeholder="ano" value="<%=ano%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="codigo" class="col-xs-2 col-form-label">Código do associado</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="codigo" id="codigo" min="1" max="99999999999999" value="<%=codAssociado%>" required>
        </div>
    </div>
    <center>
        <button type="submit" class="btn btn-primary">Emprestar</button>
        <button class="btn btn-danger" type="reset">Reset</button>
    </center>
</form>
<%@ include file="../footer/verificarErro.jsp"%>
<%@ include file="../footer/footer.jsp"%>