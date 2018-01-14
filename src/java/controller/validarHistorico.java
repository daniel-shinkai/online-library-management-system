/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Historico;
import model.gerenciarBD;

/**
 *
 * @author daniel
 */
@WebServlet(name = "validarHistorico", urlPatterns = {"/validarHistorico"})
public class validarHistorico extends HttpServlet {

    private static final String ADDRESS_REPORT_GEN = "view/gerarHistorico.jsp";
    private static final String ADDRESS_REPORT_VIEW = "view/exibirHistorico.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            String mensagem = "";

            int codigoAssociado = retornaCodigoAssociado(request);

            if (gerenciarBD.inicializaJdbc()) {

                List<Historico> listaHistoricoEmprestimoDevolvido = new ArrayList<>();
                List<Historico> listaHistoricoEmprestimoNaoDevolvido = new ArrayList<>();

                listaHistoricoEmprestimoDevolvido = gerenciarBD.retornaHistoricoEmListaEmprestimoDevolvido(codigoAssociado);
                listaHistoricoEmprestimoNaoDevolvido = gerenciarBD.retornaHistoricoEmListaEmprestimoNaoDevolvido(codigoAssociado);

                if (listaHistoricoEmprestimoDevolvido.isEmpty() && listaHistoricoEmprestimoNaoDevolvido.isEmpty()) {
                    mensagem = "Não foi possível gerar o histórico: não há empréstimos realizados";
                } else {
                    session.setAttribute("listaDevolvida", listaHistoricoEmprestimoDevolvido);
                    session.setAttribute("listaNaoDevolvida", listaHistoricoEmprestimoNaoDevolvido);
                }

                gerenciarBD.finalizaJdbc();
            } else {
                mensagem = "Nao foi possivel realizar a conexão com o banco de dados";
            }
            if (!mensagem.isEmpty()) {
                //Se não nenhum associado em atraso foi encontrado, o usuário é retornado à página de gerar relatório
                session.setAttribute("msgErro", mensagem);
                response.sendRedirect(ADDRESS_REPORT_GEN);
            } else {
                response.sendRedirect(ADDRESS_REPORT_VIEW);
            }
        }
    }

    private int retornaCodigoAssociado(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String codigo = session.getAttribute("codigo").toString();
        int codigoInt = Integer.parseInt(codigo);
        return codigoInt;
    }

}
