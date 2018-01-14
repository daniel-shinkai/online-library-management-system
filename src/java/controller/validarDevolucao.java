/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.javafx.geom.AreaOp;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Devolucao;
import model.Emprestimo;
import model.gerenciarBD;

/**
 *
 * @author daniel
 */
@WebServlet(name = "validarDevolucao", urlPatterns = {"/validarDevolucao"})
public class validarDevolucao extends HttpServlet {

    private static final String ADDRESS_REGISTER_PUBS = "view/devolucao.jsp";
    private static final String ADDRESS_REGISTER_RESULT = "view/resultadoDevolucao.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (!request.getParameter("isbn").isEmpty()) {

                int isbn = Integer.parseInt(request.getParameter("isbn"));

                realizaOperacoesDevolucao(request, response, isbn);

            } else {
                String titulo = request.getParameter("titulo");
                int isbn = gerenciarBD.retornaIsbnPublicacao(titulo);

                realizaOperacoesDevolucao(request, response, isbn);
            }

        }
    }

    private void realizaOperacoesDevolucao(HttpServletRequest request, HttpServletResponse response, int isbn) throws IOException {
        String mensagem = "";
        HttpSession session = request.getSession();

        String sql = "INSERT INTO devolucao (cod_emprestimo, codigo_func, prazo_dev, data_dev)"
                + " VALUES (?, ?, ?, ?)";

        int codigoFuncionario = retornaCodigoFuncionario(request);
        int numeroExemplar = Integer.parseInt(request.getParameter("number"));
        int codigoAssociado = Integer.parseInt(request.getParameter("codigo"));

        // tipo de data aceito no sql. a nomenclatura foi utilizada pra evitar ambiguidade com java.util.Date
        java.sql.Date dataDevolucao = retornaDataAtualEmSql();

        int diasDeAtraso = 0;

        if (gerenciarBD.inicializaJdbc()) {

            // se o usuário existe
            if (gerenciarBD.retornaTipoDoAssociado(codigoAssociado) != null) {

                // se as informacoes do emprestimo estao certas
                if (gerenciarBD.atualizaEmprestimoFinalizado(codigoAssociado, isbn, numeroExemplar)) {

                    Emprestimo emprestimo = gerenciarBD.retornaInformacaoTabelaEmprestimo(codigoAssociado,
                            isbn, numeroExemplar);

                    Devolucao devolucao = new Devolucao(emprestimo.getCod_emprestimo(), codigoFuncionario,
                            emprestimo.getPrazo_dev(), dataDevolucao);

                    // fazemos a devolucao
                    if (gerenciarBD.insereDevolucao(devolucao, sql)) {
                        // verifica se o associado devolveu dentro da data prevista
                        diasDeAtraso = calculaDiasDeAtraso(emprestimo.getPrazo_dev());

                        // se não devolveu na data prevista, calcular a multa
                        if (diasDeAtraso < 0) {
                            String sqlMulta = "INSERT INTO multa (cod_emprestimo, valor) VALUES (" + devolucao.getCod_emprestimo()
                                    + ", " + Math.abs(diasDeAtraso) + " )";

                            // inserir na tabela multa
                            if (!gerenciarBD.insereMulta(sqlMulta)) {
                                mensagem = "Erro ao inserir multa";
                            }
                        }
                    } else {
                        mensagem = "Erro na devolução do exemplar: dados de publicação inválidos";
                    }
                } else {
                    mensagem = "Erro ao atualizar a tabela emprestimo: dados de publicação inválidos";
                }
            }
            else {
                mensagem = "Associado inexistenete";
            }
            gerenciarBD.finalizaJdbc();
        } else {
            mensagem = "Nâo foi possível conectar-se ao banco de dados";
        }
        if (!mensagem.isEmpty()) {
            //Se não foi possível cadastrar a publicação, o usuário é retornado à página de cadastro
            session.setAttribute("msgErro", mensagem);
            response.sendRedirect(ADDRESS_REGISTER_PUBS);
        } else {
            session.setAttribute("titulo", "Devoluçao de Exemplar");
            String mensagemSucesso = "Devoluçao realizada com sucesso!";
            
            // avisar caso tenha multa
            if (diasDeAtraso < 0) {
                if(diasDeAtraso == -1)
                    session.setAttribute("multa", "O Associado deverá pagar uma multa de "  + Math.abs(diasDeAtraso) + " real.");
                else 
                    session.setAttribute("multa", "O Associado deverá pagar uma multa de "  + Math.abs(diasDeAtraso) + " reais.");
            }
            session.setAttribute("msg", mensagemSucesso);
            response.sendRedirect(ADDRESS_REGISTER_RESULT);
        }
    }

    java.sql.Date retornaDataAtualEmSql() {
        java.util.Date dataAtual = new java.util.Date();
        return new java.sql.Date(dataAtual.getTime());
    }

    private int retornaCodigoFuncionario(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String codigo = session.getAttribute("codigo").toString();
        int codigoInt = Integer.parseInt(codigo);
        return codigoInt;
    }

    private int calculaDiasDeAtraso(java.sql.Date prazoDev) {
        // convertendo pra java.utilDate pois e' mais fa'cil de manipular  
        java.util.Date prazoDevJava = new java.util.Date(prazoDev.getTime());

        // data atual
        java.util.Date dataAtual = new java.util.Date();

        int diasDeAtraso = (int) ((prazoDevJava.getTime() - dataAtual.getTime()) / (1000 * 60 * 60 * 24));

        return diasDeAtraso;
    }
}
