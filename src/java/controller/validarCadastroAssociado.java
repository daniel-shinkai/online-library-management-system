/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Associado;
import model.CookieUtilities;
import model.SessionCookie;
import model.gerenciarBD;

/**
 *
 * @author Patrick Perroni
 */
@WebServlet(name = "validarCadastroAssociado", urlPatterns = {"/validarCadastroAssociado"})
public class validarCadastroAssociado extends HttpServlet {

    private static final String ADDRESS_REGISTER_ASSOC = "view/cadastroAssociado.jsp";
    private static final String ADDRESS_REGISTER_RESULT = "view/resultadoCadastro.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            String sql = "INSERT INTO associado(codigo, nome, sobrenome, endereco, email, senha, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String msgErro = "";

            int codigo = Integer.parseInt(request.getParameter("codigo"));
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String endereco = request.getParameter("endereco");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String status = request.getParameter("status");

            Associado associado = new Associado(codigo, nome, sobrenome, endereco, email, senha, status);

            if (gerenciarBD.inicializaJdbc()) {
                if (!gerenciarBD.insereAssociado(sql, associado)) {
                    /*Não foi possível inserir o novo associado no BD porque provavelmente o código é inválido*/
                    msgErro = "Falha ao cadastrar o novo associado. O código inserido é inválido";

                    Cookie cCodigo = new SessionCookie("codigo1", "" + codigo);
                    response.addCookie(cCodigo);
                    Cookie cNome = new SessionCookie("nome1", nome);
                    response.addCookie(cNome);
                    Cookie cSobrenome = new SessionCookie("sobrenome1", sobrenome);
                    response.addCookie(cSobrenome);
                    Cookie cEndereco = new SessionCookie("endereco1", endereco);
                    response.addCookie(cEndereco);
                    Cookie cEmail = new SessionCookie("email1", email);
                    response.addCookie(cEmail);
                } else {
                    /*A inserção foi efetuada. Agora é necessário remover os cookies que foram definidos na sessão.*/
                    CookieUtilities.removeCookie(request, response, "codigo1");
                    CookieUtilities.removeCookie(request, response, "nome1");
                    CookieUtilities.removeCookie(request, response, "sobrenome1");
                    CookieUtilities.removeCookie(request, response, "endereco1");
                    CookieUtilities.removeCookie(request, response, "email1");
                    CookieUtilities.removeCookie(request, response, "status1");
                }
                gerenciarBD.finalizaJdbc();
            } else {
                msgErro = "Não foi possível fazer a conexão com o banco de dados";
            }

            if (!msgErro.isEmpty()) {
                //Se não foi possível cadastrar o associado, o usuário é retornado à página de cadastro
                session.setAttribute("msgErro", msgErro);
                response.sendRedirect(ADDRESS_REGISTER_ASSOC);
            } else {
                session.setAttribute("titulo", "Cadastro de associado");
                session.setAttribute("msg", "O novo associado foi registrado com sucesso!");
                response.sendRedirect(ADDRESS_REGISTER_RESULT);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
