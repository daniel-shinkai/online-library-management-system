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
import model.AssociadoAtraso;
import model.gerenciarBD;

/**
 *
 * @author Igor Santos
 */
@WebServlet(name = "validarRelatorio", urlPatterns = {"/validarRelatorio"})
public class validarRelatorio extends HttpServlet {

    private static final String ADDRESS_REPORT_GEN = "view/geraRelatorio.jsp";
    private static final String ADDRESS_REPORT_VIEW = "view/exibeRelatorio.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            String sql = "SELECT emprestimo.ISBN_exemplar, emprestimo.num_exemplar, emprestimo.data_emp, emprestimo.prazo_dev, emprestimo.codigo_assoc, associado.nome, associado.sobrenome FROM emprestimo JOIN associado on emprestimo.codigo_assoc=associado.codigo WHERE emprestimo.finalizado='n' AND emprestimo.prazo_dev < CURRENT_DATE";
            String msgErro = "";

            if (gerenciarBD.inicializaJdbc()) {
                String sCodigo = request.getParameter("codigo");
                List<AssociadoAtraso> aAtraso;
                if (!sCodigo.isEmpty()) {
                    int codigo = Integer.parseInt(sCodigo);
                    sql += " AND emprestimo.codigo_assoc=? ORDER BY emprestimo.codigo_assoc";
                    aAtraso = gerenciarBD.buscaAssociadoAtraso(sql, codigo);
                    //Verificando se nenhum associado em atraso com o codigo informado foi encontrado
                    if (aAtraso.size() < 1) {   
                        msgErro = "Não foi encontrado associado em atraso com o código informado";
                    } else {
                        session.setAttribute("aAtraso", aAtraso);
                    }
                } else {
                    sql += " ORDER BY emprestimo.codigo_assoc";
                    aAtraso = gerenciarBD.buscaAssociadoAtrasado(sql);
                    //Verificando se nenhum associado em atraso foi encontrado
                    if (aAtraso.size() < 1) {
                        msgErro = "Não há associados com empréstimos em atraso";
                    } else {
                        session.setAttribute("aAtraso", aAtraso);
                    }
                }
            } else {
                msgErro = "Não foi possível fazer a conexão com o banco de dados";
            }

            if (!msgErro.isEmpty()) {
                //Se não nenhum associado em atraso foi encontrado, o usuário é retornado à página de gerar relatório
                session.setAttribute("msgErro", msgErro);
                response.sendRedirect(ADDRESS_REPORT_GEN);
            } else {
                response.sendRedirect(ADDRESS_REPORT_VIEW);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
