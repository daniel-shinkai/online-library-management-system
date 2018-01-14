/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Publicacao;
import model.StatusExemplar;
import model.gerenciarBD;

/**
 *
 * @author Igor Santos
 */
@WebServlet(name = "validarConsultaPublicacao", urlPatterns = {"/validarConsultaPublicacao"})
public class validarConsultaPublicacao extends HttpServlet {

    private static final String ADDRESS_QUERY_PUBS = "view/consultaPublicacao.jsp";
    private static final String ADDRESS_LIST_PUBS = "view/exibeConsultaPublicacao.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            String msgErro = "";

            String sIsbn = request.getParameter("isbn");
            String titulo = request.getParameter("titulo");

            if (sIsbn.isEmpty() && titulo.isEmpty()) { //O usuáiro não inseriu nenhuma das informações necessárias
                msgErro = "Insira o titulo e/ou o ISBN da publicação";
            } else {
                int isbn;
                String sqlPub = "SELECT *FROM publicacao WHERE ";
                if (gerenciarBD.inicializaJdbc()) {
                    Publicacao pub;
                    if (!sIsbn.isEmpty()) { //O ISBN foi inserido
                        isbn = Integer.parseInt(sIsbn);
                        sqlPub += "ISBN=?";
                        pub = gerenciarBD.buscaPublicacao(sqlPub, isbn);
                    } else { //Somente o título foi inserido
                        sqlPub += "titulo=?";
                        pub = gerenciarBD.buscaPublicacao(sqlPub, titulo);
                    }
                    //Verificando se a publicação existe
                    if (pub != null) {
                        session.setAttribute("publicacao", pub);
                        /*
                            Esta query une os registros da tabela exemplar com os registros da tabela empréstimo.
                            Os registros da tabela empréstimo que ficarem como null no left join com a tabela exemplar
                            estão disponíveis, são exemplares disponíveis, pois não foram emprestados ainda.
                        */
                        String sql = "SELECT numero, preco, t1.finalizado FROM exemplar LEFT JOIN (SELECT ISBN_exemplar, num_exemplar, data_emp, finalizado FROM emprestimo as emp1 WHERE data_emp IN (SELECT MAX(data_emp) FROM emprestimo as emp2 WHERE emp2.ISBN_exemplar=emp1.ISBN_exemplar AND emp1.num_exemplar=emp2.num_exemplar GROUP BY emp2.ISBN_exemplar, emp2.num_exemplar)) t1 ON exemplar.ISBN=t1.ISBN_exemplar AND exemplar.numero=t1.num_exemplar WHERE exemplar.ISBN=? ORDER BY exemplar.numero";
                        List<StatusExemplar> statusEx = gerenciarBD.buscaStatusExemplar(sql, pub.getIsbn());
                        session.setAttribute("statusEx", statusEx);
                    } else {
                        //A publicação não foi encontrada
                        msgErro = "Não existe nenhuma publicação com os dados informados";
                    }
                    gerenciarBD.finalizaJdbc();
                } else {
                    msgErro = "Não foi possível fazer a conexão com o banco de dados";
                }
            }

            if (!msgErro.isEmpty()) {
                //Se não foi possível cadastrar a publicação, o usuário é retornado à página de cadastro
                session.setAttribute("msgErro", msgErro);
                response.sendRedirect(ADDRESS_QUERY_PUBS);
            } else {
                response.sendRedirect(ADDRESS_LIST_PUBS);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
