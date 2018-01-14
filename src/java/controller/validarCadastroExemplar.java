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
import model.CookieUtilities;
import model.Exemplar;
import model.SessionCookie;
import model.gerenciarBD;

/**
 *
 * @author Phelip Roberto
 */
@WebServlet(name = "validarCadastroExemplar", urlPatterns = {"/validarCadastroExemplar"})
public class validarCadastroExemplar extends HttpServlet {
    
    private static final String ADDRESS_REGISTER_BOOK = "view/cadastroExemplar.jsp";
    private static final String ADDRESS_REGISTER_RESULT = "view/resultadoCadastro.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            
            String verifPublicacao = "SELECT COUNT(1) FROM publicacao WHERE isbn=?";
            String sql = "INSERT INTO exemplar(numero, ISBN, preco) VALUES (?, ?, ?)";
            String msgErro = "";
            
            int numero = Integer.parseInt(request.getParameter("numero"));
            int isbn = Integer.parseInt(request.getParameter("isbn"));
            float preco = Float.parseFloat(request.getParameter("preco"));
            
            if (gerenciarBD.inicializaJdbc()) {
                if (gerenciarBD.existePublicacao(verifPublicacao, isbn)) {
                    Exemplar exemplar = new Exemplar(numero, isbn, preco);
                    if (gerenciarBD.insereExemplar(sql, exemplar)) {
                        /*A inserção foi efetuada. Agora é necessário remover os cookies que foram definidos na sessão.*/
                        CookieUtilities.removeCookie(request, response, "numero3");
                        CookieUtilities.removeCookie(request, response, "isbn3");
                        CookieUtilities.removeCookie(request, response, "preco3");
                    } else {
                        msgErro = "O número do exemplar inserido é inválido";
                    }
                } else {
                    msgErro = "Não há nenhuma publicação cadastrada com o ISBN informado";
                }
                gerenciarBD.finalizaJdbc();
            } else {
                msgErro = "Não foi possível fazer a conexão com o banco de dados";
            }
            
            if (!msgErro.isEmpty()) {
                //Se não foi possível cadastrar o exemplar, o usuário é retornado à página de cadastro
                session.setAttribute("msgErro", msgErro);
                Cookie cNumero = new SessionCookie("numero3", "" + numero);
                response.addCookie(cNumero);
                Cookie cIsbn = new SessionCookie("isbn3", "" + isbn);
                response.addCookie(cIsbn);
                Cookie cPreco = new SessionCookie("preco3", "" + preco);
                response.addCookie(cPreco);
                response.sendRedirect(ADDRESS_REGISTER_BOOK);
            } else {
                session.setAttribute("titulo", "Cadastro de exemplar");
                session.setAttribute("msg", "O novo exemplar foi registrado com sucesso!");
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
