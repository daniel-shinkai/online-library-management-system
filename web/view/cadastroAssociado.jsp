<%-- 
    Document   : cadastroAssociado
    Created on : Oct 8, 2016, 10:14:50 PM
    Author     : Patrick Perroni
--%>

<%@page import="model.CookieUtilities"%>
<%@ include file="../header/validarSessao.jsp"%>
<%@ include file="../header/h_principalAdmin.jsp"%>
<%    //Obtendo por meio de cookies os valores que foram inseridos nos campos do cadastro que falhou
    String codigo = CookieUtilities.getCookieValue(request, "codigo1", "");
    String nome = CookieUtilities.getCookieValue(request, "nome1", "");
    String sobrenome = CookieUtilities.getCookieValue(request, "sobrenome1", "");
    String endereco = CookieUtilities.getCookieValue(request, "endereco1", "");
    String email = CookieUtilities.getCookieValue(request, "email1", "");
%>
<h1 class="page-header">Cadastro de associado</h1>
<form method="post" action="../validarCadastroAssociado">
    <div class="form-group row">
        <label for="codigo" class="col-xs-2 col-form-label">Código</label>
        <div class="col-xs-10">
            <input class="form-control" type="number" name="codigo" id="codigo" min="1" max="99999999999999" value="<%=codigo%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="nome_sobrenome" class="col-xs-2 col-form-label">Nome e Sobrenome</label>
        <div class="col-xs-5">
            <input class="form-control" type="text" name="nome" id="nome" value="<%=nome%>" required>
        </div>
        <div class="col-xs-5">
            <input class="form-control" type="text" name="sobrenome" id="sobrenome" value="<%=sobrenome%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="endereco" class="col-xs-2 col-form-label">Endereço</label>
        <div class="col-xs-10">
            <input class="form-control" type="text" name="endereco" id="endereco" value="<%=endereco%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="email" class="col-xs-2 col-form-label">Email</label>
        <div class="col-xs-10">
            <input class="form-control" type="email" name="email" id="email" value="<%=email%>" required>
        </div>
    </div>
    <div class="form-group row">
        <label for="senha" class="col-xs-2 col-form-label">Senha</label>
        <div class="col-xs-10">
            <input class="form-control" type="password" name="senha" id="senha" required>
        </div>
    </div>
    <fieldset class="form-group row">
        <legend class="col-form-legend col-sm-2">Status</legend>
        <div class="col-sm-10">
            <div class="form-check">
                <label class="form-check-label">
                    <input class="form-check-input" type="radio" name="status" id="status1" value="grad" checked>
                    Aluno de Graduação
                </label>
            </div>
            <div class="form-check">
                <label class="form-check-label">
                    <input class="form-check-input" type="radio" name="status" id="status2" value="posgrad">
                    Aluno de Pós-graduação
                </label>
            </div>
            <div class="form-check disabled">
                <label class="form-check-label">
                    <input class="form-check-input" type="radio" name="status" id="status3" value="prof">
                    Professor
                </label>
            </div>
        </div>
    </fieldset>
    <center>
        <button type="submit" class="btn btn-primary">Cadastrar</button>
        <button class="btn btn-danger" type="reset">Reset</button>
    </center>
</form>
<!-- Verifica se houve algum erro durante a operação e informa ao usuário -->
<%@ include file="../footer/verificarErro.jsp"%>
<%@ include file="../footer/footer.jsp"%>
