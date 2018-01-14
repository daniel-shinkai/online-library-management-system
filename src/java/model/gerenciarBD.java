/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Igor Santos
 */
public class gerenciarBD {
    
    private static final String DRIVER_BANCO_DADOS = "com.mysql.jdbc.Driver";
    private static final String CONEXAO_BANCO_DADOS = "jdbc:mysql://localhost/biblioteca";
    private static final String USUARIO_BANCO_DADOS = "root";
    private static final String SENHA_BANCO_DADOS = "";
    
    private static Connection c;
    private static PreparedStatement pstmt;
    
    public static boolean inicializaJdbc() {
        try {
            Class.forName(DRIVER_BANCO_DADOS);
            c = DriverManager.getConnection(CONEXAO_BANCO_DADOS, USUARIO_BANCO_DADOS, SENHA_BANCO_DADOS);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            return false;
        }
        
        return true;
    }
    
    public static ResultSet buscaUsuario(int codigo, String senha, String sql) {
        ResultSet rs;
        try {
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, codigo);
            pstmt.setString(2, senha);
            System.out.println("" + pstmt);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return rs;
    }
    
    public static boolean insereAssociado(String sql, Associado associado) {
        try {
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, associado.getCodigo());
            pstmt.setString(2, associado.getNome());
            pstmt.setString(3, associado.getSobrenome());
            pstmt.setString(4, associado.getEndereco());
            pstmt.setString(5, associado.getEmail());
            pstmt.setString(6, associado.getSenha());
            pstmt.setString(7, associado.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean existePublicacao(String sql, int isbn) {
        try {
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                /*
                  Verificando se existe algum registro na tabela com o ISBN informado
                 */
                int i = rs.getInt(1);
                if (i > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean inserePublicacao(String sql, Publicacao publicacao) {
        try {
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, publicacao.getIsbn());
            pstmt.setString(2, publicacao.getTitulo());
            pstmt.setString(3, publicacao.getAutor());
            pstmt.setString(4, publicacao.getEditora());
            pstmt.setInt(5, publicacao.getAno());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean insereExemplar(String sql, Exemplar exemplar) {
        try {
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, exemplar.getNumero());
            pstmt.setInt(2, exemplar.getIsbn());
            pstmt.setFloat(3, exemplar.getPreco());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static void finalizaJdbc() {
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Publicacao buscaPublicacao(String sql, int isbn) {
        try {
            ResultSet rs;
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, isbn);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editora = rs.getString("editora");
                int ano = rs.getInt("ano");
                Publicacao publicacao = new Publicacao(isbn, titulo, autor, editora, ano);
                return publicacao;
            }
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Publicacao buscaPublicacao(String sql, String titulo) {
        try {
            ResultSet rs;
            pstmt = c.prepareStatement(sql);
            pstmt.setString(1, titulo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int isbn = rs.getInt("ISBN");
                String autor = rs.getString("autor");
                String editora = rs.getString("editora");
                int ano = rs.getInt("ano");
                Publicacao publicacao = new Publicacao(isbn, titulo, autor, editora, ano);
                return publicacao;
            }
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static List<StatusExemplar> buscaStatusExemplar(String sql, int isbn) {
        List<StatusExemplar> statusEx = new ArrayList<>();
        try {
            ResultSet rs;
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, isbn);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int numero = rs.getInt("numero");
                float preco = rs.getFloat("preco");
                String status; //O status é definido depois de verificar se o livro está emprestado
                if (rs.getString("finalizado") == null || rs.getString("finalizado").equals("s")) {
                    /*Estas condições indicam que o livro está disponível*/
                    status = "Disponível";
                } else {
                    //O livro está indisponível
                    status = "Indisponível";
                }
                statusEx.add(new StatusExemplar(numero, preco, status));
            }
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statusEx;
    }

    /*
        Nesta função irá ocorrer o filtro dos empréstimos pelo código do associado.
     */
    public static List<AssociadoAtraso> buscaAssociadoAtraso(String sql, int codigoAssoc) {
        List<AssociadoAtraso> aAtraso = new ArrayList<>();
        int codigoTemp = -1;
        try {
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, codigoAssoc);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int isbn = rs.getInt("ISBN_exemplar");
                int numeroEx = rs.getInt("num_exemplar");
                Date dataEmp = rs.getDate("data_emp");
                Date prazoDev = rs.getDate("prazo_dev");
                //Verificando se é o mesmo associado com mais de uma publicação em atraso
                if (codigoTemp != codigoAssoc) {
                    codigoTemp = codigoAssoc;
                    String nome = rs.getString("nome");
                    String sobrenome = rs.getString("sobrenome");
                    aAtraso.add(new AssociadoAtraso(codigoTemp, nome, sobrenome));
                }
                //Buscando as informações da publicação
                String sqlPub = "SELECT *FROM publicacao WHERE ISBN=?";
                pstmt = c.prepareStatement(sqlPub);
                pstmt.setInt(1, isbn);
                ResultSet pub = pstmt.executeQuery();
                if (pub.next()) {
                    String titulo = pub.getString("titulo");
                    String autor = pub.getString("autor");
                    String editora = pub.getString("editora");
                    int ano = pub.getInt("ano");
                    aAtraso.get(aAtraso.size() - 1).setpAtrasada(new PublicacaoAtrasada(numeroEx, dataEmp, prazoDev, isbn, titulo, autor, editora, ano));
                }
            }
            return aAtraso;
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Esta função irá retornar todos os associados em atraso
    public static List<AssociadoAtraso> buscaAssociadoAtrasado(String sql) {
        List<AssociadoAtraso> aAtraso = new ArrayList<>();
        int codigoTemp = -1;
        try {
            pstmt = c.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int isbn = rs.getInt("ISBN_exemplar");
                int numeroEx = rs.getInt("num_exemplar");
                Date dataEmp = rs.getDate("data_emp");
                Date prazoDev = rs.getDate("prazo_dev");
                int codigoAssoc = rs.getInt("codigo_assoc");
                //Verificando se é o mesmo associado com mais de uma publicação em atraso
                if (codigoTemp != codigoAssoc) {
                    codigoTemp = codigoAssoc;
                    String nome = rs.getString("nome");
                    String sobrenome = rs.getString("sobrenome");
                    aAtraso.add(new AssociadoAtraso(codigoTemp, nome, sobrenome));
                }
                //Buscando as informações da publicação
                String sqlPub = "SELECT *FROM publicacao WHERE ISBN=?";
                pstmt = c.prepareStatement(sqlPub);
                pstmt.setInt(1, isbn);
                ResultSet pub = pstmt.executeQuery();
                if (pub.next()) {
                    String titulo = pub.getString("titulo");
                    String autor = pub.getString("autor");
                    String editora = pub.getString("editora");
                    int ano = pub.getInt("ano");
                    aAtraso.get(aAtraso.size() - 1).setpAtrasada(new PublicacaoAtrasada(numeroEx, dataEmp, prazoDev, isbn, titulo, autor, editora, ano));
                }
            }
            return aAtraso;
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean verificaDisponibilidadeExemplar(int isbn, int numeroExemplar) {
        
        if (!existeExemplar(isbn, numeroExemplar)) {
            return false;
        }
        
        String sql = "SELECT * FROM emprestimo WHERE ISBN_exemplar = " + isbn + " AND num_exemplar = " + numeroExemplar
                + " ORDER BY cod_emprestimo DESC LIMIT 1";
        
        try {
            pstmt = c.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            if (resultSetEstaVazio(rs)) {//exemplar ainda não emprestado
                return true;
            }
            
            if (rs.next()) {
                if (rs.getString("finalizado").equals("s")) //exemplar já foi devolvido
                {
                    return true;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static boolean existeExemplar(int isbn, int numeroExemplar) {
        
        String sql = "SELECT * FROM exemplar WHERE  ISBN = " + isbn + " AND numero = " + numeroExemplar;
        
        try {
            pstmt = c.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            if (resultSetEstaVazio(rs)) {
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public static String retornaTipoDoAssociado(int codigo) {
        String sql = "SELECT status FROM associado WHERE codigo  = " + codigo;
        String status = "";
        try {
            pstmt = c.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            if (resultSetEstaVazio(rs)) {
                return null;
            }
            
            if (rs.next()) {
                status = rs.getString("status");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return status;
    }
    
    public static boolean resultSetEstaVazio(ResultSet rs) throws SQLException {
        return !rs.isBeforeFirst();
    }
    
    public static boolean insereEmprestimo(String sql, Emprestimo emprestimo) {
        try {
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, emprestimo.getCodigo_assoc());
            pstmt.setInt(2, emprestimo.getCodigo_func());
            pstmt.setInt(3, emprestimo.getNum_exmeplar());
            pstmt.setInt(4, emprestimo.getISBN_exemplar());
            pstmt.setDate(5, emprestimo.getData_emp());
            pstmt.setDate(6, emprestimo.getPrazo_dev());
            pstmt.setString(7, emprestimo.getFinalizado() + "");
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static Emprestimo retornaInformacaoTabelaEmprestimo(int codigoAssociado, int isbn, int numeroExemplar) {
        Emprestimo emprestimo = new Emprestimo();
        
        try {
            String sql = "SELECT cod_emprestimo, prazo_dev FROM emprestimo WHERE codigo_assoc = " + codigoAssociado
                    + " AND ISBN_exemplar = " + isbn + " AND num_exemplar = " + numeroExemplar + " ORDER BY cod_emprestimo DESC";
            
            pstmt = c.prepareStatement(sql);
            
            ResultSet rs = pstmt.executeQuery();
            if (!gerenciarBD.resultSetEstaVazio(rs)) {
                if (rs.next()) {
                    emprestimo.setCod_emprestimo(rs.getInt("cod_emprestimo"));
                    emprestimo.setPrazo_dev(rs.getDate("prazo_dev"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return emprestimo;
    }
    
    public static boolean atualizaEmprestimoFinalizado(int codigoAssociado, int isbn, int numeroExemplar) {
        
        String sql = "UPDATE emprestimo SET finalizado = 's' WHERE codigo_assoc = " + codigoAssociado
                + " AND ISBN_exemplar = " + isbn + " AND num_exemplar = " + numeroExemplar;
        
        try {
            pstmt = c.prepareStatement(sql);
            
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean insereDevolucao(Devolucao devolucao, String sql) {
        try {
            pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, devolucao.getCod_emprestimo());
            pstmt.setInt(2, devolucao.getCodigo_func());
            pstmt.setDate(3, devolucao.getPrazo_dev());
            pstmt.setDate(4, devolucao.getData_dev());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean insereMulta(String sql) {
        try {
            pstmt = c.prepareStatement(sql);
            
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static int retornaIsbnPublicacao(String titulo) {
        String sql = "SELECT ISBN FROM publicacao WHERE titulo like '" + titulo + "'";
        int isbnProcurado = -1;
        try {
            pstmt = c.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                isbnProcurado = rs.getInt("ISBN");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return isbnProcurado;
    }
    
    public static List<Historico> retornaHistoricoEmListaEmprestimoDevolvido(int codigoAssociado) {
        
        List<Historico> listaHistorico = new ArrayList<>();

        // sql abaixo retorna todos os dados do historico quando o associado já devolveu o livro
        String sql = "select emprestimo.cod_emprestimo, emprestimo.ISBN_exemplar, emprestimo.num_exemplar, publicacao.titulo, publicacao.ano, publicacao.editora, emprestimo.data_emp, devolucao.data_dev from emprestimo inner join devolucao on emprestimo.cod_emprestimo = devolucao.cod_emprestimo inner join publicacao on emprestimo.ISBN_exemplar = publicacao.ISBN where emprestimo.codigo_assoc = " + codigoAssociado + " and emprestimo.finalizado like 's'";
        System.out.println(sql);
        try {
            pstmt = c.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int cod_emprestimo = rs.getInt("cod_emprestimo");
                int isbn_exemplar = rs.getInt("ISBN_exemplar");
                int num_exemplar = rs.getInt("num_exemplar");
                String titulo = rs.getString("titulo");
                int ano = rs.getInt("ano");
                String editora = rs.getString("editora");
                Date data_emp = rs.getDate("data_emp");
                Date data_dev = rs.getDate("data_dev");
                
                String dateString;
                Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                dateString = formatter.format(data_dev);
                                
                Historico historico = new Historico(cod_emprestimo, isbn_exemplar, num_exemplar, ano, titulo, editora,
                        data_emp, dateString);
                listaHistorico.add(historico);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return listaHistorico;
    }
    
    public static List<Historico> retornaHistoricoEmListaEmprestimoNaoDevolvido(int codigoAssociado) {
        
        List<Historico> listaHistorico = new ArrayList<>();

        // sql abaixo retorna todos os dados do historico quando o associado não devolveu o livro mas não está atrasado
        String sql = "select emprestimo.cod_emprestimo, emprestimo.ISBN_exemplar, emprestimo.num_exemplar, publicacao.titulo, publicacao.ano, publicacao.editora, emprestimo.data_emp, emprestimo.prazo_dev from emprestimo inner join publicacao on emprestimo.ISBN_exemplar = publicacao.ISBN where emprestimo.codigo_assoc = " + codigoAssociado + " and emprestimo.finalizado like 'n' and emprestimo.prazo_dev > CURRENT_DATE ";
        
        try {
            pstmt = c.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int cod_emprestimo = rs.getInt("cod_emprestimo");
                int isbn_exemplar = rs.getInt("ISBN_exemplar");
                int num_exemplar = rs.getInt("num_exemplar");
                String titulo = rs.getString("titulo");
                int ano = rs.getInt("ano");
                String editora = rs.getString("editora");
                Date data_emp = rs.getDate("data_emp");
                Date prazo_dev = rs.getDate("prazo_dev");
                
                String dateString;
                Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                dateString = formatter.format(prazo_dev);
                
                Historico historico = new Historico(cod_emprestimo, isbn_exemplar, num_exemplar, ano, titulo, editora,
                        data_emp, dateString);
                
                listaHistorico.add(historico);
            }
            
            sql = "select emprestimo.cod_emprestimo, emprestimo.ISBN_exemplar, emprestimo.num_exemplar, publicacao.titulo, publicacao.ano, publicacao.editora, emprestimo.data_emp, abs(DATEDIFF(emprestimo.prazo_dev, CURRENT_DATE)) as dataDiff from emprestimo inner join publicacao on emprestimo.ISBN_exemplar = publicacao.ISBN where emprestimo.codigo_assoc = " + codigoAssociado + " and emprestimo.finalizado like 'n' and emprestimo.prazo_dev < CURRENT_DATE ";
            
            pstmt = c.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int cod_emprestimo = rs.getInt("cod_emprestimo");
                int isbn_exemplar = rs.getInt("ISBN_exemplar");
                int num_exemplar = rs.getInt("num_exemplar");
                String titulo = rs.getString("titulo");
                int ano = rs.getInt("ano");
                String editora = rs.getString("editora");
                Date data_emp = rs.getDate("data_emp");
                
                String prazo_dev = "Dias de atraso: ";
                prazo_dev += rs.getString("dataDiff");
                
                Historico historico = new Historico(cod_emprestimo, isbn_exemplar, num_exemplar, ano, titulo, editora,
                        data_emp, prazo_dev);
                listaHistorico.add(historico);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(gerenciarBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return listaHistorico;
    }
    
}
