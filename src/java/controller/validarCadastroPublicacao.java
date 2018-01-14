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
import model.Publicacao;
import model.SessionCookie;
import model.gerenciarBD;

/**
 *
 * @author Igor Santos
 */
@WebServlet(name = "validarCadastroPublicacao", urlPatterns = {"/validarCadastroPublicacao"})
public class validarCadastroPublicacao extends HttpServlet {

    private static final String ADDRESS_REGISTER_PUBS = "view/cadastroPublicacao.jsp";
    private static final String ADDRESS_REGISTER_RESULT = "view/resultadoCadastro.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            String sql = "INSERT INTO publicacao(ISBN, titulo, autor, editora, ano) VALUES (?, ?, ?, ?, ?)";
            String msgErro = "";

            int isbn = Integer.parseInt(request.getParameter("isbn"));
            String titulo = request.getParameter("titulo");
            String autor = request.getParameter("autor");
            String editora = request.getParameter("editora");
            int ano = Integer.parseInt(request.getParameter("ano"));

            Publicacao publicacao = new Publicacao(isbn, titulo, autor, editora, ano);
            if (gerenciarBD.inicializaJdbc()) {
                if (!gerenciarBD.inserePublicacao(sql, publicacao)) {
                    /*
                        Não foi possível inserir a nova publicação porque provavelmente o ISBN é inválido.
                     */
                    msgErro = "Falha ao cadastrar a nova publicação. O ISBN inserido é inválido";

                    Cookie cIsbn = new SessionCookie("isbn2", "" + isbn);
                    response.addCookie(cIsbn);
                    Cookie cTitulo = new SessionCookie("titulo2", titulo);
                    response.addCookie(cTitulo);
                    Cookie cAutor = new SessionCookie("autor2", autor);
                    response.addCookie(cAutor);
                    Cookie cEditora = new SessionCookie("editora2", editora);
                    response.addCookie(cEditora);
                    Cookie cAno = new SessionCookie("ano2", "" + ano);
                    response.addCookie(cAno);
                } else {
                    /*A inserção foi efetuada. Agora é necessário remover os cookies que foram definidos na sessão.*/
                    CookieUtilities.removeCookie(request, response, "isbn2");
                    CookieUtilities.removeCookie(request, response, "titulo2");
                    CookieUtilities.removeCookie(request, response, "autor2");
                    CookieUtilities.removeCookie(request, response, "editora2");
                    CookieUtilities.removeCookie(request, response, "ano2");
                }
                gerenciarBD.finalizaJdbc();
            } else {
                msgErro = "Não foi possível fazer a conexão com o banco de dados";
            }

            if (!msgErro.isEmpty()) {
                //Se não foi possível cadastrar a publicação, o usuário é retornado à página de cadastro
                session.setAttribute("msgErro", msgErro);
                response.sendRedirect(ADDRESS_REGISTER_PUBS);
            } else {
                session.setAttribute("titulo", "Cadastro de publicação");
                session.setAttribute("msg", "A publicação foi registrada com sucesso!");
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
