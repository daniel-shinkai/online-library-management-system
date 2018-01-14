/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CookieUtilities;
import model.Emprestimo;
import model.SessionCookie;
import model.gerenciarBD;

/**
 *
 * @author daniel
 */
@WebServlet(name = "validarEmprestimo", urlPatterns = {"/validarEmprestimo"})
public class validarEmprestimo extends HttpServlet {

    private static final String ADDRESS_REGISTER_PUBS = "view/emprestimo.jsp";
    private static final String ADDRESS_REGISTER_RESULT = "view/resultadoEmprestimo.jsp";

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

            String sql = "INSERT INTO emprestimo( codigo_assoc , codigo_func, num_exemplar, ISBN_exemplar"
                    + ", data_emp, prazo_dev, finalizado) VALUES (?, ?, ?, ?, ?, ?, ?)";

            String mensagem = "";

            int numero = Integer.parseInt(request.getParameter("number"));
            int isbn = Integer.parseInt(request.getParameter("isbn"));
            int codigo_assoc = Integer.parseInt(request.getParameter("codigo"));
            int dia = Integer.parseInt(request.getParameter("dia"));
            int mes = Integer.parseInt(request.getParameter("mes"));
            int ano = Integer.parseInt(request.getParameter("ano"));
            int codigo_func = retornaCodigoFuncionario(request);

            // convertendo em data os 3 valores inteiros do formulario
            Date data_emp = converteIntEmDate(ano, mes, dia);

            Date dataDevolucao = null;

            if (gerenciarBD.inicializaJdbc()) {
                // pegando o tipo de associado para calcular a data de devolucao
                String statusAssociado = gerenciarBD.retornaTipoDoAssociado(codigo_assoc);

                // se o associado existe
                if (statusAssociado != null) {

                    // e' possi'vel emprestar o exemplar?
                    if (!gerenciarBD.verificaDisponibilidadeExemplar(isbn, numero)) {
                        mensagem = "Exemplar nao existe ou esta emprestado";
                    } else {

                        dataDevolucao = calculaERetornaDataDevolucao(statusAssociado, data_emp);

                        Emprestimo emprestimo = new Emprestimo(codigo_assoc, codigo_func, numero, isbn,
                                data_emp, dataDevolucao, 'n');

                        if (!gerenciarBD.insereEmprestimo(sql, emprestimo)) {
                            mensagem = "Falha ao registrar o emprestimo no banco de dados";
                        }
                    }
                } else {
                    mensagem = "Associado inexistente";
                }
                gerenciarBD.finalizaJdbc();
            } else {
                mensagem = "Nâo foi possível conectar-se ao banco de dados";
            }
            if (!mensagem.isEmpty()) {

                Cookie cNumExemp = new SessionCookie("numExemplar5", "" + numero);
                response.addCookie(cNumExemp);
                Cookie cIsbn = new SessionCookie("isbn5", "" + isbn);
                response.addCookie(cIsbn);
                Cookie cDia = new SessionCookie("dia5", "" + dia);
                response.addCookie(cDia);
                Cookie cMes = new SessionCookie("mes5", "" + mes);
                response.addCookie(cMes);
                Cookie cAno = new SessionCookie("ano5", "" + ano);
                response.addCookie(cAno);
                Cookie cEmail = new SessionCookie("codAssociado5", "" + codigo_assoc);
                response.addCookie(cEmail);
                //Se não foi possível cadastrar a publicação, o usuário é retornado à página de cadastro
                session.setAttribute("msgErro", mensagem);
                response.sendRedirect(ADDRESS_REGISTER_PUBS);
            } else {
                CookieUtilities.removeCookie(request, response, "numExemplar5");
                CookieUtilities.removeCookie(request, response, "isbn5");
                CookieUtilities.removeCookie(request, response, "dia5");
                CookieUtilities.removeCookie(request, response, "mes5");
                CookieUtilities.removeCookie(request, response, "ano5");
                CookieUtilities.removeCookie(request, response, "codAssociado5");

                // convertendo a data para string para mostrar ao usuario
                String dateString;
                Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                dateString = formatter.format(dataDevolucao);
                
                session.setAttribute("dataDevol", dateString);

                session.setAttribute("titulo", "Realização de empréstimo");
                session.setAttribute("msg", "O empréstimo foi realizado com sucesso!");
                response.sendRedirect(ADDRESS_REGISTER_RESULT);
            }
        }
    }

    private Date converteIntEmDate(int ano, int mes, int dia) {
        // data do java - mais fácil de manipular
        java.util.Date data = new GregorianCalendar(ano, mes - 1, dia).getTime();

        //data do sql, usado no bd - convertendo data do java pro sql
        Date sqlDate = new java.sql.Date(data.getTime());
        return sqlDate;
    }

    private int retornaCodigoFuncionario(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String codigo = session.getAttribute("codigo").toString();
        int codigoInt = Integer.parseInt(codigo);
        return codigoInt;
    }

    private Date calculaERetornaDataDevolucao(String status, Date dataEmp) {
        int diasDeEmprestimo = 0;

        if (status.equals("grad")) {
            diasDeEmprestimo = 7;
        } else if (status.equals("posgrad")) {
            diasDeEmprestimo = 10;
        } else if (status.equals("prof")) {
            diasDeEmprestimo = 14;
        }

        Date dataDev = retornaDataDevolucao(diasDeEmprestimo, dataEmp);

        return dataDev;
    }

    private Date retornaDataDevolucao(int diasDeEmprestimo, Date dataEmp) {
        // data atual em java Date
        java.util.Date dataJava = new Date(dataEmp.getTime());

        // usamos Calendar pq ele possui uma funcao de adicionar dias
        Calendar myCal = new GregorianCalendar();
        myCal.setTime(dataJava);

        // adicionando os dias de devolucao
        myCal.add(Calendar.DATE, diasDeEmprestimo);

        //voltando pro sql date
        java.util.Date dataDevJava = myCal.getTime();
        Date dataDev = new java.sql.Date(dataDevJava.getTime());

        return dataDev;
    }
}
