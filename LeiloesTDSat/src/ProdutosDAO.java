/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        conn = new conectaDAO().connectDB();

    try {
        // Preparando a query SQL para inserção de um novo produto
        String query = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        prep = conn.prepareStatement(query);

        // Configurando os parâmetros da query com os dados do produto
        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor());
        prep.setString(3, produto.getStatus());

        // Executando a query para inserir o produto no banco de dados
        prep.executeUpdate();

        JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso!");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto.");
    } finally {
        // Fechando recursos (é importante liberar recursos para evitar vazamentos)
        try {
            if (prep != null) {
                prep.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        conn = new conectaDAO().connectDB();
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();

        try {
            String query = "SELECT * FROM produtos";
            prep = conn.prepareStatement(query);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listagem;
    }
      public void venderProduto(int idProduto) {
        conn = new conectaDAO().connectDB();

        try {
            // Preparando a query SQL para atualizar o status do produto para "Vendido"
            String query = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            prep = conn.prepareStatement(query);

            // Configurando o parâmetro da query com o ID do produto
            prep.setInt(1, idProduto);

            // Executando a query para atualizar o status do produto
            int rowsAffected = prep.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID fornecido.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao vender produto.");
        } finally {
            // Fechando recursos
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    conn = new conectaDAO().connectDB();
    ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();

    try {
        String query = "SELECT * FROM produtos WHERE status = 'Vendido'";
        prep = conn.prepareStatement(query);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            produtosVendidos.add(produto);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultset != null) {
                resultset.close();
            }
            if (prep != null) {
                prep.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return produtosVendidos;
}
 
    
    
        
}

