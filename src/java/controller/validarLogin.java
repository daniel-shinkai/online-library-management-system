/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.gerenciarBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Igor Santos
 */
@WebServlet(name = "validarLogin", urlPatterns = {"/validarLogin"})
public class validarLogin extends HttpServlet {

    private static final String ADDRESS_LOGIN_ADMIN = "view/loginAdmin.jsp";
    private static final String ADDRESS_HOME = "view/home.jsp";
    private static final String ADDRESS_LOGIN_ASSOC = "index.jsp";

    public static boolean verificaInt(String s) {
        return verificaInt(s, 10);
    }

    public static boolean verificaInt(String s, int base) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (Character.digit(s.charAt(i), base) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String paginaInicial;
            String paginaPrincipal;
            String msgErro = "";
            String sCodigo = request.getParameter("codigo");
            String senha = request.getParameter("senha");
            String tipoUsuario = request.getParameter("tipoUsuario");

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30 * 60); // A sessao expira em 30 minutos

            if (tipoUsuario.equals("associado")) {
                paginaInicial = ADDRESS_LOGIN_ASSOC;
                paginaPrincipal = ADDRESS_HOME;
            } else {
                paginaInicial = ADDRESS_LOGIN_ADMIN;
                paginaPrincipal = ADDRESS_HOME;
            }

            if (verificaInt(sCodigo)) {
                int codigo = Integer.parseInt(sCodigo);
                String sql = "SELECT * FROM ";
                if (tipoUsuario.equals("associado")) {
                    sql += "associado WHERE codigo=? AND senha=?";
                } else {
                    sql += "funcionario WHERE codigo=? AND senha=?";
                }
                if (gerenciarBD.inicializaJdbc()) {
                    ResultSet rs;
                    try {
                        rs = gerenciarBD.buscaUsuario(codigo, senha, sql);
                        if (rs != null) {
                            String nome = rs.getString("nome");
                            session.setAttribute("nome", nome);
                            session.setAttribute("codigo", codigo);
                            session.setAttribute("tipoUsuario", tipoUsuario);
                        } else {
                            msgErro = "Código e/ou senha inválidos.";
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(validarLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    msgErro = "Erro ao fazer a conexão com o banco de dados.";
                }
            } else {
                msgErro = "Código inválido. Entre somente com números.";
            }

            if (!msgErro.isEmpty()) {
                //Se ocorreu o login não foi validado, o usuário é retornado à página de login
                session.setAttribute("msgErro", msgErro);
                response.sendRedirect(paginaInicial);
            } else {
                //Caso contrário, ele é mandado à página de funções de usuário
                response.sendRedirect(paginaPrincipal);
            }

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
