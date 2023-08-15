package conexao;

import javax.swing.JOptionPane;
import java.sql.*; // execução dos comandos sql no ambiente java

public class Conexao {
    final private String driver = "com.mysql.cj.jdbc.Driver"; //definição do driver mysql
    final private String url = "jdbc:mysql://localhost/clientes"; // acesso ao bd clientes no servidor myadm
    final private String usuario = "root"; //usuario do mysql
    final private String senha = ""; //senha do mysql
    private Connection conexao; //variável que vai armazenar a conexão aberta do mysql
    public Statement statement;
    public ResultSet resultset;

    public boolean conecta() { 
        boolean result = true;
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,usuario,senha);
            JOptionPane.showMessageDialog(null, "Conexão feita com sucesso!!!!!!!");
        } catch(ClassNotFoundException Driver)
        {
            JOptionPane.showMessageDialog(null, "Driver não localizado"+Driver, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
            result = false;
        } catch(SQLException Fonte){
             JOptionPane.showMessageDialog(null, "Fonte de dados não encontrada!"+Fonte, "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
        
    } //fim do public boolean conecta
    
    public void desconecta() 
    {
        try {
            conexao.close();
              JOptionPane.showMessageDialog(null, "Conexão fechada!!","Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(SQLException fecha)
        {
             JOptionPane.showMessageDialog(null, "Erro ao fechar!!","Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
        }
    
    }
    
    public void executaSQL(String sql)
    {
        try{
            statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultset = statement.executeQuery(sql);
        }catch(SQLException excecao)
        {
               JOptionPane.showMessageDialog(null, "Erro no comando sql! \n Erro: " + excecao, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
        }
    
    }

} //end of the class   
